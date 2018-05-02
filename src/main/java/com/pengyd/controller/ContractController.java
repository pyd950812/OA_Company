package com.pengyd.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengyd.bean.Contract;
import com.pengyd.bean.Employee;
import com.pengyd.service.ContractService;
import com.pengyd.service.EmployeeService;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

/**
 * @Author pengyd
 * @Date 2018/3/22 17:08
 * @function:
 */
@Controller
@RequestMapping(value = "/contract")
public class ContractController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private ContractService contractService;

    @Resource
    private EmployeeService employeeService;

    /**
     * 数据展示页面
     */
    @RequiresPermissions(value = "contract_show")
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(Model model, HttpServletRequest request) {
        return "contract/show";
    }

    /**
     * 数据新增页面
     */
    @RequiresPermissions(value = "contract_add")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request) {
        return "contract/add";
    }

    /**
     * 数据修改页面
     */
    @RequiresPermissions(value = "contract_edit")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");

        Contract contract = new Contract();
        contract.setId(Integer.valueOf(Integer.parseInt(id)));

        ReturnData rd = contractService.selectByParam(null, contract);
        if (rd.getCode().equals("OK")) {
            List<Contract> data = (List<Contract>) rd.getData().get("data");

            model.addAttribute("olddata", JSON.toJSONString(data.get(0)));
        }
        return "contract/edit";
    }

    /**
     * 对 contract 的数据插入操作
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData insert(HttpServletRequest request) {
        Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));

        Contract contract = new Contract();

        contract.setCreateUserId(currentEmp.getId());
        contract.setModifyUserId(currentEmp.getId());

        //采用spring提供的上传文件的方法

        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            /*Enumeration<String> params2 = multiRequest.getParameterNames();
            while (params2.hasMoreElements()) {
                String value = (String) params2.nextElement();//调用nextElement方法获得元素
                System.out.println(value);
                System.out.println(multiRequest.getParameter(value));
            }*/

            String empId = multiRequest.getParameter("empIdName");

            contract.setEmpId(Integer.valueOf(empId));

            //获取用户的真实姓名
            String empRealname = employeeService.selectRealnameById(Integer.valueOf(empId));

            //获取multiRequest 中所有的文件名
            /*Iterator iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
            }*/

            MultipartFile file = multiRequest.getFile("contractFileName");

            if (file != null) {
                String pathName = "F:/graduationdoc/empContract/";
                File dirFile = new File(pathName);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }

                /*System.out.println(file.getContentType());//application/vnd.openxmlformats-officedocument.wordprocessingml.document
                System.out.println(file.getName());//file
                System.out.println(file.getOriginalFilename());//xxx.docx
                System.out.println(file.getSize());*///11078

                String fileName = file.getOriginalFilename();
                String fileNameSuffix = fileName.substring(fileName.lastIndexOf("."));

                String docName = empRealname + fileNameSuffix;
                String docPathName = pathName + empRealname + fileNameSuffix;

                contract.setContractName(docName);
                contract.setContractUrl(docPathName);

                //上传
                try {
                    file.transferTo(new File(docPathName));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return contractService.insert(contract);//执行插入 Contract 操作
    }

    @RequiresPermissions(value = "contract_downFileById")
    @RequestMapping(value = "/downFileById", method = RequestMethod.GET)
    public void downFileById(Model model, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        Contract contract = new Contract();
        contract.setId(Integer.valueOf(Integer.parseInt(id)));

        ReturnData rd = contractService.selectByParam(null, contract);
        List<Contract> data = (List<Contract>) rd.getData().get("data");
        if (data.size() >= 1) {
            Contract contract2 = data.get(0);
            //获取输入流
            try {
                InputStream bis = new BufferedInputStream(new FileInputStream(new File(contract2.getContractUrl())));

                String fileName = new String((contract2.getContractName()).getBytes(), "ISO8859_1");
                //设置文件下载头
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
                //response.setContentType("multipart/form-data");
                response.setContentType("application/binary;charset=utf-8");
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                int len = 0;
                while ((len = bis.read()) != -1) {
                    out.write(len);
                    out.flush();
                }
                out.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 对 contract 的数据删除操作
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData delete(@RequestBody Contract contract, Model model, HttpServletRequest request) {
        return contractService.delete(contract);//执行删除 Contract  操作
    }

    /**
     * 对 contract 的数据批量删除操作
     */
    @RequestMapping({ "/deleteBatch" })
    @ResponseBody
    public ReturnData deleteBatch(HttpServletRequest request) {
        ReturnData rd = new ReturnData();
        String ids = request.getParameter("ids");
        if ((ids == null) || ("".equals(ids))) {
            rd.setCode("ERROR");
            rd.setMsg("ids为空");
        }
        else {
            rd = contractService.deleteBatch(ids.split(","));
        }
        return rd;
    }

    /**
     * 对 contract 的数据修改操作
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData update(HttpServletRequest request) {
        Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));

        Contract contract = new Contract();

        contract.setModifyUserId(currentEmp.getId());

        //采用spring提供的上传文件的方法

        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            String id = multiRequest.getParameter("idName");

            contract.setId(Integer.valueOf(id));

            String empId = multiRequest.getParameter("empIdName");

            contract.setEmpId(Integer.valueOf(empId));

            //获取用户的真实姓名
            String empRealname = employeeService.selectRealnameById(Integer.valueOf(empId));

            MultipartFile file = multiRequest.getFile("contractFileName");

            if (file != null) {
                String pathName = "F:/graduationdoc/empContract/";
                File dirFile = new File(pathName);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }

                String fileName = file.getOriginalFilename();
                String fileNameSuffix = fileName.substring(fileName.lastIndexOf("."));

                String docName = empRealname + fileNameSuffix;
                String docPathName = pathName + empRealname + fileNameSuffix;

                contract.setContractName(docName);
                contract.setContractUrl(docPathName);

                //上传
                try {
                    file.transferTo(new File(docPathName));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return contractService.update(contract);//执行插入 Contract 操作
    }

    /**
     * 对 contract 的数据分页查询操作
     */
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean select(String GridParam, Model model, HttpServletRequest request) {
        Contract contract = new Gson().fromJson(GridParam, Contract.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        //分页查询
        return contractService.select(page, rows, order_by, contract);
    }

    /**
     * 对 contract 的数据分页查询操作 - 关联查询
     */
    @RequestMapping(value = "/selectRelationData", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean selectRelationData(String GridParam, Model model, HttpServletRequest request) {
        Contract contract = new Gson().fromJson(GridParam, Contract.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        //分页查询
        return contractService.selectRelationData(page, rows, order_by, contract);
    }

    /**
     * 对 contract 的数据分页查询操作 - 关联查询
     */
    @RequestMapping(value = "/selectRelationDataByEmpRealname", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean selectRelationDataByEmpRealname(String GridParam, Model model, HttpServletRequest request) {
        Contract contract = new Gson().fromJson(GridParam, Contract.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        String empRealname = request.getParameter("empRealname");//

        //分页查询
        return contractService.selectRelationDataByEmpRealname(page, rows, order_by, contract, empRealname);
    }

    /**
     * 对 contract 的数据查询操作不分页
     */
    @RequestMapping(value = "/selectByParam", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData selectByParam(@RequestBody Contract contract, Model model, HttpServletRequest request) {
        String order_by = request.getParameter("order_by");//排序

        return contractService.selectByParam(order_by, contract);
    }

    /**
     * 对 contract 的数据导出操作
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response) {
        //1、使用JSONObject
        String json = request.getParameter("json");
        Contract contract = new Gson().fromJson(json, Contract.class);

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序
        //分页查询
        JqGridJsonBean rd = contractService.select(page, rows, order_by, contract);

        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("contract");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);

        //创建单元格并设置单元格内容
        row1.createCell(1 - 1).setCellValue("主键");
        row1.createCell(2 - 1).setCellValue("所属员工");
        row1.createCell(3 - 1).setCellValue("合同存储的路径");
        row1.createCell(4 - 1).setCellValue("合同文件名称");
        row1.createCell(5 - 1).setCellValue("创建用户");
        row1.createCell(6 - 1).setCellValue("创建时间");
        row1.createCell(7 - 1).setCellValue("修改用户的ID");
        row1.createCell(8 - 1).setCellValue("修改日期");
        //在sheet里创建第三行
        @SuppressWarnings("unchecked")
        List<Contract> maps = (List<Contract>) rd.getRoot();
        for (int i = 0; i < maps.size(); i++) {
            Contract map = maps.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(1 - 1).setCellValue(map.getId() + "");
            row.createCell(2 - 1).setCellValue(map.getEmpId() + "");
            row.createCell(3 - 1).setCellValue(map.getContractUrl() + "");
            row.createCell(4 - 1).setCellValue(map.getContractName() + "");
            row.createCell(5 - 1).setCellValue(map.getCreateUserId() + "");
            row.createCell(6 - 1).setCellValue(map.getCreateTime() + "");
            row.createCell(7 - 1).setCellValue(map.getModifyUserId() + "");
            row.createCell(8 - 1).setCellValue(map.getModifyTime() + "");
        }

        //输出Excel文件
        try {
            ServletOutputStream output = response.getOutputStream();
            String fileName = new String(("导出contract").getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/binary;charset=utf-8");
            wb.write(output);
            output.flush();
            output.close();

        }
        catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    /**
     * 对 contract 的数据导入操作
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData _import(@RequestParam(value = "file", required = false) MultipartFile file,
                              HttpServletResponse response) {
        ReturnData rd = new ReturnData();
        String filename = file.getOriginalFilename();
        if (filename == null || "".equals(filename)) {
            return null;
        }
        try {
            InputStream XSSFinput = file.getInputStream();
            InputStream HSSFinput = file.getInputStream();
            Workbook workBook = null;
            try {
                workBook = new XSSFWorkbook(XSSFinput);
            }
            catch (Exception ex) {
                workBook = new HSSFWorkbook(HSSFinput);
            }

            Sheet sheet = workBook.getSheetAt(0);
            if (sheet != null) {
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    Row row = sheet.getRow(i);
                    Contract contract = new Contract();
                    //System.out.println(row.getCell(0));
                    //此处自己添字段例如 myTable.set...(row.getCell(0))

                    //contractService.insert(contract);
                }

            }
        }
        catch (Exception e) {
            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
            //e.printStackTrace();
        }

        rd.setCode("OK");
        rd.setMsg("数据导入成功");
        return rd;
    }
}