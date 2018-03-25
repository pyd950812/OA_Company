package com.pengyd.controller;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengyd.bean.Attendance;
import com.pengyd.bean.Employee;
import com.pengyd.service.AttendanceService;
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
@RequestMapping(value = "/attendance")
public class AttendanceController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private AttendanceService attendanceService;

    /**
     * 数据展示页面
     * @return
     */
    @RequiresPermissions(value = "attendance_show")
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(Model model, HttpServletRequest request) {
        return "attendance/show";
    }

    /**
     * 数据新增页面
     * @return
     */
    @RequiresPermissions(value = "attendance_add")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request) {
        return "attendance/add";
    }

    /**
     * 数据修改页面
     * @return
     */
    @RequiresPermissions(value = "attendance_edit")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");

        Attendance attendance = new Attendance();
        attendance.setId(Integer.valueOf(Integer.parseInt(id)));

        ReturnData rd = attendanceService.selectByParam(null, attendance);
        if (rd.getCode().equals("OK")) {
            List<Attendance> data = (List<Attendance>) rd.getData().get("data");

            model.addAttribute("olddata", JSON.toJSONString(data.get(0)));
        }
        return "attendance/edit";
    }

    /**
     * 对 attendance 的数据插入操作
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData insert(HttpServletRequest request) {
        Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));

        Attendance attendance = new Attendance();

        attendance.setEmpId(currentEmp.getId());
        attendance.setAttdState(1);//0-缺勤(旷工) - 1-正常 2-迟到 3-请假 4-调休

        return attendanceService.insert(attendance);//执行插入 Attendance 操作
    }

    /**
     * 对 attendance 的数据删除操作
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData delete(@RequestBody Attendance attendance, Model model, HttpServletRequest request) {
        return attendanceService.delete(attendance);//执行删除 Attendance  操作
    }

    /**
     * 对 attendance 的数据批量删除操作
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
            rd = attendanceService.deleteBatch(ids.split(","));
        }
        return rd;
    }

    /**
     * 对 attendance 的数据修改操作
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData update(@RequestBody Attendance attendance, Model model, HttpServletRequest request) {
        return attendanceService.update(attendance);//执行 Attendance  操作
    }

    /**
     * 对 attendance 的数据分页查询操作
     */
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean select(String GridParam, Model model, HttpServletRequest request) {
        Attendance attendance = new Gson().fromJson(GridParam, Attendance.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        //分页查询
        return attendanceService.select(page, rows, order_by, attendance);
    }

    /**
     * 对 attendance 的数据分页查询操作 - 关联查询
     */
    @RequestMapping(value = "/selectRelationData", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean selectRelationData(String GridParam, Model model, HttpServletRequest request) {
        Attendance attendance = new Gson().fromJson(GridParam, Attendance.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        //分页查询
        return attendanceService.selectRelationData(page, rows, order_by, attendance);
    }

    /**
     * 对 attendance 的数据分页查询操作 - 关联查询
     */
    @RequestMapping(value = "/selectRelationDataByEmpRealname", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean selectRelationDataByEmpRealname(String GridParam, Model model, HttpServletRequest request) {
        Attendance attendance = new Gson().fromJson(GridParam, Attendance.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        String empRealname = request.getParameter("empRealname");//

        //分页查询
        return attendanceService.selectRelationDataByEmpRealname(page, rows, order_by, attendance, empRealname);
    }

    /**
     * 对 attendance 的数据查询操作不分页
     */
    @RequestMapping(value = "/selectByParam", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData selectByParam(@RequestBody Attendance attendance, Model model, HttpServletRequest request) {
        String order_by = request.getParameter("order_by");//排序

        return attendanceService.selectByParam(order_by, attendance);
    }

    /**
     * 对 attendance 的数据导出操作
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response) {
        //1、使用JSONObject
        String json = request.getParameter("json");
        Attendance attendance = new Gson().fromJson(json, Attendance.class);

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序
        //分页查询
        JqGridJsonBean rd = attendanceService.select(page, rows, order_by, attendance);

        //创建HSSFWorkbook对象(excel的文档对象)  
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）  
        HSSFSheet sheet = wb.createSheet("attendance");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个  
        HSSFRow row1 = sheet.createRow(0);

        //创建单元格并设置单元格内容  
        row1.createCell(1 - 1).setCellValue("主键");
        row1.createCell(2 - 1).setCellValue("所属员工");
        row1.createCell(3 - 1).setCellValue("考勤状态 - 0-缺勤(旷工) - 1-正常 2-迟到 3-请假 4-调休");
        row1.createCell(4 - 1).setCellValue("考核日期");
        //在sheet里创建第三行  
        @SuppressWarnings("unchecked")
        List<Attendance> maps = (List<Attendance>) rd.getRoot();
        for (int i = 0; i < maps.size(); i++) {
            Attendance map = maps.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(1 - 1).setCellValue(map.getId() + "");
            row.createCell(2 - 1).setCellValue(map.getEmpId() + "");
            row.createCell(3 - 1).setCellValue(map.getAttdState() + "");
            row.createCell(4 - 1).setCellValue(map.getCreateTime() + "");
        }

        //输出Excel文件  
        try {
            ServletOutputStream output = response.getOutputStream();
            String fileName = new String(("导出attendance").getBytes(), "ISO8859_1");
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
     * 对 attendance 的数据导入操作
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
                    Attendance attendance = new Attendance();
                    //System.out.println(row.getCell(0));
                    //此处自己添字段例如 myTable.set...(row.getCell(0))

                    //attendanceService.insert(attendance);  
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