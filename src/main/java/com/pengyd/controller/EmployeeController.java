package com.pengyd.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.pengyd.bean.Employee;
import com.pengyd.bean.Jobpos;
import com.pengyd.service.EmployeeService;
import com.pengyd.service.JobposService;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import com.google.gson.Gson;

/**
 * @Author pengyd
 * @Date 2018/3/22 17:08
 * @function: 员工
 */
@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private EmployeeService employeeService;

    @Resource
    private JobposService jobposService;



    /**
     * 数据展示页面
     */
    @RequiresPermissions(value = "employee_show")
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(Model model, HttpServletRequest request) {
        return "employee/show";
    }

    /**
     * 数据新增页面
     */
    //@RequiresPermissions(value = "employee_add")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request) {
        return "employee/add";
    }

    /**
     * 数据修改页面
     */
    //@RequiresPermissions(value = "employee_edit")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");

        Employee employee = new Employee();
        employee.setId(Integer.valueOf(Integer.parseInt(id)));

        ReturnData rd = employeeService.selectByParam(null, employee);
        if (rd.getCode().equals("OK")) {
            List<Employee> data = (List<Employee>) rd.getData().get("data");

            model.addAttribute("olddata", JSON.toJSONString(data.get(0)));
        }
        return "employee/edit";
    }

    /**
     * 对 employee 的数据插入操作
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData insert(@RequestBody Employee employee, Model model, HttpServletRequest request) {
        return employeeService.insert(employee);//执行插入 Employee 操作
    }

    /**
     * 对 employee 的数据删除操作
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData delete(@RequestBody Employee employee, Model model, HttpServletRequest request) {
        return employeeService.delete(employee);//执行删除 Employee  操作
    }

    /**
     * 对 employee 的数据批量删除操作
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
            rd = employeeService.deleteBatch(ids.split(","));
        }
        return rd;
    }

    /**
     * 对 employee 的数据修改操作
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData update(@RequestBody Employee employee, Model model, HttpServletRequest request) {
        return employeeService.update(employee);//执行 Employee  操作
    }

    /**
     * 对 employee 的数据分页查询操作
     */
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean select(String GridParam, Model model, HttpServletRequest request) {
        Employee employee = new Gson().fromJson(GridParam, Employee.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        //分页查询
        return employeeService.select(page, rows, order_by, employee);
    }

    /**
     * 对 employee 的数据分页查询操作
     */
    @RequestMapping(value = "/selectRelationData", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean selectRelationData(String GridParam, Model model, HttpServletRequest request) {
        Employee employee = new Gson().fromJson(GridParam, Employee.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        //分页查询
        return employeeService.selectRelationData(page, rows, order_by, employee);
    }

    @RequestMapping({ "/ajaxSelectMaxEmpCode" })
    @ResponseBody
    public ReturnData ajaxSelectMaxEmpCode(HttpServletRequest request) {
        return employeeService.ajaxSelectMaxEmpCode();
    }

    @RequestMapping({ "/ajaxSelectSubEmpBySup" })
    @ResponseBody
    public ReturnData ajaxSelectSubEmpBySup(HttpServletRequest request) {
        ReturnData rd = new ReturnData();

        //首先获取到当前用户的jobId
        Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));
        int jobId = currentEmp.getJobposId();

        //通过 jobId 查询出工作编码
        Jobpos jobpos = new Jobpos();
        jobpos.setId(jobId);
        ReturnData rdJobpos = jobposService.selectByParam(null, jobpos);
        List<Jobpos> dataJobpos = (List<Jobpos>) rdJobpos.getData().get("data");
        String jobIdStr = dataJobpos.get(0).getJobposCode();

        if (jobIdStr.length() != 6) {
            rd.setCode("ERROR");
            rd.setMsg("非部门经理");
        }
        else {
            //获取到子工作职位的用户的jobpos编码的前缀
            jobIdStr = jobIdStr.substring(0, 5);
            ReturnData rdTemp = jobposService.selectIdListBySubId(jobIdStr);
            List<Integer> data = (List<Integer>) rdTemp.getData().get("data");

            //需要把部门经理自身给屏蔽掉
            Iterator<Integer> iter = data.iterator();
            while (iter.hasNext()) {
                Integer item = iter.next();
                if (jobId == item) {
                    iter.remove();
                }
            }
            //System.out.println(strList);

            if (data.size() < 0) {
                rd.setCode("ERROR");
                rd.setMsg("没有所属员工");
            }
            else {
                String strListStr = data.toString();
                String jobIdListStr = strListStr.substring(1, (strListStr.length() - 1));
                rd = employeeService.selectSubEmpListByJobId(jobIdListStr);
            }
        }
        return rd;
    }

    /**
     * 对 employee 的数据查询操作不分页
     */
    @RequestMapping(value = "/selectByParam", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData selectByParam(@RequestBody Employee employee, Model model, HttpServletRequest request) {
        String order_by = request.getParameter("order_by");//排序

        return employeeService.selectByParam(order_by, employee);
    }

    /**
     * 对 employee 的数据导出操作
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response) {
        //1、使用JSONObject
        String json = request.getParameter("json");
        Employee employee = new Gson().fromJson(json, Employee.class);

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序
        //分页查询
        JqGridJsonBean rd = employeeService.select(page, rows, order_by, employee);

        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("employee");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);

        //创建单元格并设置单元格内容
        row1.createCell(1 - 1).setCellValue("主键");
        row1.createCell(2 - 1).setCellValue("员工编码");
        row1.createCell(3 - 1).setCellValue("用户名");
        row1.createCell(4 - 1).setCellValue("用户密码");
        row1.createCell(5 - 1).setCellValue("真实姓名");
        row1.createCell(6 - 1).setCellValue("入职时间");
        row1.createCell(7 - 1).setCellValue("所属职位");
        row1.createCell(8 - 1).setCellValue("注册时间");
        //在sheet里创建第三行
        @SuppressWarnings("unchecked")
        List<Employee> maps = (List<Employee>) rd.getRoot();
        for (int i = 0; i < maps.size(); i++) {
            Employee map = maps.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(1 - 1).setCellValue(map.getId() + "");
            row.createCell(2 - 1).setCellValue(map.getEmpCode() + "");
            row.createCell(3 - 1).setCellValue(map.getLoginname() + "");
            row.createCell(4 - 1).setCellValue(map.getPassword() + "");
            row.createCell(5 - 1).setCellValue(map.getRealname() + "");
            row.createCell(6 - 1).setCellValue(map.getEntryTime() + "");
            row.createCell(7 - 1).setCellValue(map.getJobposId() + "");
            row.createCell(8 - 1).setCellValue(map.getRegisterTime() + "");
        }

        //输出Excel文件
        try {
            ServletOutputStream output = response.getOutputStream();
            String fileName = new String(("导出employee").getBytes(), "ISO8859_1");
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
     * 对 employee 的数据导入操作
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
                    Employee employee = new Employee();
                    //System.out.println(row.getCell(0));
                    //此处自己添字段例如 myTable.set...(row.getCell(0))

                    //employeeService.insert(employee);
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

    /**
     * 对 employee 的数据插入操作
     */
    @RequestMapping(value = "/ajaxLoginname", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData ajaxLoginname(@RequestBody Employee employee, Model model, HttpServletRequest request) {
        ReturnData rd = employeeService.selectByParam(null, employee);
        List<Employee> data = (List<Employee>) rd.getData().get("data");
        if (data.size() >= 1) {
            rd.setCode("ERROR");
            rd.setMsg("该用户名已存在，请修改");
        }
        return rd;//执行插入 Employee 操作
    }

    @RequestMapping({ "/ajaxSelectEmpByJobposId" })
    @ResponseBody
    public ReturnData ajaxSelectEmpByJobposId(HttpServletRequest request) {
        String jobposId = request.getParameter("jobposId");//
        return employeeService.ajaxSelectEmpByJobposId(jobposId);
    }

    @RequestMapping({ "/ajaxSelectEmpById" })
    @ResponseBody
    public ReturnData ajaxSelectEmpById(HttpServletRequest request) {
        String empId = request.getParameter("empId");//
        String empRealname = employeeService.selectRealnameById(Integer.valueOf(empId));

        ReturnData rd = new ReturnData();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("data", empRealname);
        rd.setCode("OK");
        rd.setData(dataMap);
        return rd;
    }



    //跳转到 员工薪资信息
    @RequestMapping("salary")
    public String salary(){
        return "employee/salary";
    }




}