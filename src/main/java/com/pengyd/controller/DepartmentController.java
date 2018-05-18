package com.pengyd.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengyd.bean.Department;
import com.pengyd.bean.DeptPerm;
import com.pengyd.service.DepartmentService;
import com.pengyd.service.DeptPermService;
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

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;


@Controller
@RequestMapping(value = "/department")
public class DepartmentController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private DepartmentService departmentService;

    @Resource
    private DeptPermService deptPermService;

    /**
     * 数据展示页面
     * @return
     */
    @RequiresPermissions(value = "department_show")
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(Model model, HttpServletRequest request) {
        return "department/show";
    }

    /**
     * 数据新增页面
     */
    //@RequiresPermissions(value = "department_add")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request) {
        return "department/add";
    }

    /**
     * 数据修改页面
     */
    //@RequiresPermissions(value = "department_edit")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        int deptId = Integer.valueOf(id);

        Department department = new Department();
        department.setId(deptId);

        ReturnData rd = departmentService.selectByParam(null, department);
        if (rd.getCode().equals("OK")) {
            List<Department> data = (List<Department>) rd.getData().get("data");

            model.addAttribute("olddata", JSON.toJSONString(data.get(0)));
        }

        //获取权限信息
        /*DeptPerm deptPerm = new DeptPerm();
        deptPerm.setDeptId(deptId);
        
        List<Integer> permValue = new ArrayList<Integer>();
        ReturnData rdDeptPerm = deptPermService.selectByParam(null, deptPerm);
        List<DeptPerm> dataDeptPerm = (List<DeptPerm>) rdDeptPerm.getData().get("data");
        if (dataDeptPerm.size() > 0) {
            for (int i = 0; i < dataDeptPerm.size(); i++) {
                permValue.add(dataDeptPerm.get(i).getPermId());
            }
        }
        
        String permValueStr = permValue.toString();
        
        if (permValue.size() > 0) {
            permValueStr = permValueStr.substring(1, permValueStr.length() - 1);
        }
        
        model.addAttribute("permValue", permValueStr);*/

        return "department/edit";
    }

    /**
     * 对 department 的数据插入操作
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData insert(String GridParam, Model model, HttpServletRequest request) {
        Department department = new Gson().fromJson(GridParam, Department.class);//json 转对象

        ReturnData rd = departmentService.insert(department);

        int deptId = (int) rd.getData().get("data");
        //拿到前台传过来复选框中的值（选中了哪些权限）
        String permValue = request.getParameter("permValue");//2,3,4

        //首先 - 新增部门的时候肯定是新增
        String[] permValueArray = permValue.split(",");
        for (int i = 0; i < permValueArray.length; i++) {
            DeptPerm deptPerm = new DeptPerm();
            deptPerm.setDeptId(deptId);
            deptPerm.setPermId(Integer.valueOf(permValueArray[i]));
            deptPermService.insert(deptPerm);
        }

        return rd;//执行插入 Department 操作
    }

    /**
     * 对 department 的数据删除操作
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData delete(@RequestBody Department department, Model model, HttpServletRequest request) {
        return departmentService.delete(department);//执行删除 Department  操作
    }

    /**
     * 对 department 的数据批量删除操作
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
            rd = departmentService.deleteBatch(ids.split(","));
        }
        return rd;
    }

    /**
     * 对 department 的数据修改操作
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData update(String GridParam, Model model, HttpServletRequest request) {
        Department department = new Gson().fromJson(GridParam, Department.class);//json 转对象

        int deptId = department.getId();

        //原先存在的权限项
        DeptPerm deptPerm = new DeptPerm();
        deptPerm.setDeptId(deptId);

        List<Integer> permValueOld = new ArrayList<Integer>();
        List<Integer> permValueIdOld = new ArrayList<Integer>();
        ReturnData rdDeptPerm = deptPermService.selectByParam(null, deptPerm);
        List<DeptPerm> dataDeptPerm = (List<DeptPerm>) rdDeptPerm.getData().get("data");
        if (dataDeptPerm.size() > 0) {
            for (int i = 0; i < dataDeptPerm.size(); i++) {
                DeptPerm deptPermTemp = dataDeptPerm.get(i);
                permValueOld.add(deptPermTemp.getPermId());
                permValueIdOld.add(deptPermTemp.getId());
            }
        }

        //新的权限项
        List<Integer> permValueNew = new ArrayList<Integer>();

        String permValue = request.getParameter("permValue");//2,3,4

        //首先 - 新增设备的时候肯定是新增
        String[] permValueArray = permValue.split(",");
        for (int i = 0; i < permValueArray.length; i++) {
            permValueNew.add(Integer.valueOf(permValueArray[i]));
        }

        //删除没有的
        for (int i = 0; i < permValueOld.size(); i++) {
            if (!permValueNew.contains(permValueOld.get(i))) {
                DeptPerm deptPermTemp = new DeptPerm();
                deptPermTemp.setId(permValueIdOld.get(i));
                deptPermService.delete(deptPermTemp);
            }
        }

        //新增新增的
        for (int i = 0; i < permValueNew.size(); i++) {
            if (!permValueOld.contains(permValueNew.get(i))) {
                DeptPerm deptPermTemp = new DeptPerm();
                deptPermTemp.setDeptId(deptId);
                deptPermTemp.setPermId(permValueNew.get(i));
                deptPermService.insert(deptPermTemp);
            }
        }

        return departmentService.update(department);//执行 Department  操作
    }

    /**
     * 对 department 的数据分页查询操作
     */
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean select(String GridParam, Model model, HttpServletRequest request) {
        Department department = new Gson().fromJson(GridParam, Department.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        //分页查询
        return departmentService.select(page, rows, order_by, department);
    }

    @RequestMapping({ "/ajaxSelectDept" })
    @ResponseBody
    public ReturnData ajaxSelectDept(HttpServletRequest request) {
        return departmentService.ajaxSelectDept();
    }

    /**
     * 对 department 的数据查询操作不分页
     */
    @RequestMapping(value = "/selectByParam", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData selectByParam(@RequestBody Department department, Model model, HttpServletRequest request) {
        String order_by = request.getParameter("order_by");//排序

        return departmentService.selectByParam(order_by, department);
    }

    /**
     * 对 department 的数据导出操作
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response) {
        //1、使用JSONObject
        String json = request.getParameter("json");
        Department department = new Gson().fromJson(json, Department.class);

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序
        //分页查询
        JqGridJsonBean rd = departmentService.select(page, rows, order_by, department);

        //创建HSSFWorkbook对象(excel的文档对象)  
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）  
        HSSFSheet sheet = wb.createSheet("department");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个  
        HSSFRow row1 = sheet.createRow(0);

        //创建单元格并设置单元格内容  
        row1.createCell(1 - 1).setCellValue("主键");
        row1.createCell(2 - 1).setCellValue("部门编码");
        row1.createCell(3 - 1).setCellValue("部门名称");
        row1.createCell(4 - 1).setCellValue("部门信息");
        row1.createCell(5 - 1).setCellValue("创建时间");
        row1.createCell(6 - 1).setCellValue("是否能直接分配所属工作-1-分配，2-不分配");
        //在sheet里创建第三行  
        @SuppressWarnings("unchecked")
        List<Department> maps = (List<Department>) rd.getRoot();
        for (int i = 0; i < maps.size(); i++) {
            Department map = maps.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(1 - 1).setCellValue(map.getId() + "");
            row.createCell(2 - 1).setCellValue(map.getDeptCode() + "");
            row.createCell(3 - 1).setCellValue(map.getDeptname() + "");
            row.createCell(4 - 1).setCellValue(map.getDeptinfo() + "");
            row.createCell(5 - 1).setCellValue(map.getCreateTime() + "");
            row.createCell(6 - 1).setCellValue(map.getIsDis() + "");
        }

        //输出Excel文件  
        try {
            ServletOutputStream output = response.getOutputStream();
            String fileName = new String(("导出department").getBytes(), "ISO8859_1");
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
     * 对 department 的数据导入操作
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
                    Department department = new Department();
                    //System.out.println(row.getCell(0));
                    //此处自己添字段例如 myTable.set...(row.getCell(0))

                    //departmentService.insert(department);  
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