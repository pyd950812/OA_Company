
package com.pengyd.controller;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengyd.bean.Attendance;
import com.pengyd.bean.Employee;
import com.pengyd.service.AttendanceService;
import com.pengyd.util.CommonUtils;
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
    //@RequiresPermissions(value = "attendance_add")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request) {
        return "attendance/add";
    }

    /**
     * 数据修改页面
     * @return
     */
    //@RequiresPermissions(value = "attendance_edit")
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
     * 对 attendance 的数据插入操作 - 设置的是取出的相关的时间
     */
    @RequestMapping(value = "/insertByTime", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData insertByTime(@RequestBody Attendance attendance, Model model, HttpServletRequest request) {
        Long attdTimeL = attendance.getCreateTime().getTime();//2018-03-14 08:00:00

        int attdState = attendance.getAttdState();
        //1 缺勤(全天旷工) - 2 半天旷工 - 3 正常上班 - 4 正常下班 - 5 迟到 - 6 早退 - 7 请假 - 8 调休 - 9 出差
        if (attdState == 4) {//attdState == 1 ||
            attdTimeL += (long) 10 * 60 * 60 * 1000;
        }
        else if (attdState == 2) {
            attdTimeL += (long) 6 * 60 * 60 * 1000;
        }
        else if (attdState == 3) {
            attdTimeL += (long) 60 * 60 * 1000;
        }
        else if (attdState == 5) {
            attdTimeL += (long) 90 * 60 * 1000;
        }
        else if (attdState == 6) {
            attdTimeL += (long) (9 * 60 * 60 * 1000 + 30 * 60 * 1000);
        }

        Timestamp attdTime = new Timestamp(attdTimeL);
        attendance.setCreateTime(attdTime);

        return attendanceService.insertByTime(attendance);//执行插入 Attendance 操作
    }

    /**
     * 对 attendance 的数据插入操作 - 上班的操作记录
     */
    @RequestMapping(value = "/insertByOnDuty", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData insertByOnDuty(HttpServletRequest request) {
        ReturnData rd = new ReturnData();

        Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));

        Attendance attendance = new Attendance();

        attendance.setEmpId(currentEmp.getId());

        String nowDateStr = CommonUtils.toStrByNowDate();

        ReturnData rdTemp = attendanceService.selectByNowDateStr(attendance, nowDateStr);

        List<Attendance> data = (List<Attendance>) rdTemp.getData().get("data");

        if (data.size() >= 1) {//存在签到记录
            rd.setCode("ERROR");
            rd.setMsg("已打过，无需再次打卡");
        }
        else {
            //拿当前时间和9点做判断 - 判断是否是迟到 - 若未打卡的员工，在下班之后记为旷工 - 定时任务 - 生成未打卡员工的打卡记录 - 记为旷工
            Date dateNow = CommonUtils.getNowDate();

            String datetimeOnDuty9 = "09:00";
            Date dateOnDuty9 = CommonUtils.getDutyDate(datetimeOnDuty9);

            String datetimeOnDuty93 = "09:30";
            Date dateOnDuty93 = CommonUtils.getDutyDate(datetimeOnDuty93);

            String datetimeOnDuty12 = "12:00";
            Date dateOnDuty12 = CommonUtils.getDutyDate(datetimeOnDuty12);

            String datetimeOnDuty14 = "14:00";
            Date dateOnDuty14 = CommonUtils.getDutyDate(datetimeOnDuty14);

            String datetimeOffDuty = "18:00";
            Date dateOffDuty = CommonUtils.getDutyDate(datetimeOffDuty);

            //09:00-09:30 迟到 - 09:30-12:00 旷工半天 - 14:00-18:00 旷工一天

            Integer attdState = 1;//1 缺勤(全天旷工) - 2 半天旷工 - 3 正常上班 - 4 正常下班 - 5 迟到 - 6 早退 - 7 请假 - 8 调休 - 9 出差

            if (dateNow.getTime() < dateOnDuty9.getTime()) {//正常
                attdState = 3;
            }
            else if (dateNow.getTime() > dateOnDuty9.getTime() && dateNow.getTime() < dateOnDuty93.getTime()) {//迟到
                attdState = 5;
            }
            else if (dateNow.getTime() > dateOnDuty93.getTime() && dateNow.getTime() < dateOnDuty14.getTime()) {//
                attdState = 2;
            }
            /*else if (dateNow.getTime() > dateOffDuty14.getTime()) {//旷工 - 1

            }*/

            attendance.setAttdState(attdState);//

            rd = attendanceService.insert(attendance);//执行插入 Attendance 操作
        }
        return rd;
    }

    /**
     * 对 attendance 的数据插入操作 - 下班的操作记录
     */
    @RequestMapping(value = "/insertByOffDuty", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData insertByOffDuty(HttpServletRequest request) {
        ReturnData rd = new ReturnData();

        Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));

        //如果未点击签到，不允许下班，只能第二天申请补卡

        //先判断是否有当天的签到记录

        Attendance attendance = new Attendance();

        attendance.setEmpId(currentEmp.getId());

        String nowDateStr = CommonUtils.toStrByNowDate();

        ReturnData rdTemp = attendanceService.selectByNowDateStr(attendance, nowDateStr);

        List<Attendance> data = (List<Attendance>) rdTemp.getData().get("data");

        if (data.size() >= 1) {//存在签到记录
            if (data.size() >= 2) {//已经存在两条签到记录 - 上班和下班
                rd.setCode("ERROR");
                rd.setMsg("已打过，无需再次打卡");
            }
            else {
                //拿当前时间和9点做判断 - 判断是否是迟到 - 若未打卡的员工，在下班之后记为旷工 - 定时任务 - 生成未打卡员工的打卡记录 - 记为旷工
                Date dateNow = CommonUtils.getNowDate();

                String datetimeOnDuty9 = "09:00";
                Date dateOnDuty9 = CommonUtils.getDutyDate(datetimeOnDuty9);

                String datetimeOnDuty93 = "09:30";
                Date dateOnDuty93 = CommonUtils.getDutyDate(datetimeOnDuty93);

                String datetimeOnDuty12 = "12:00";
                Date dateOnDuty12 = CommonUtils.getDutyDate(datetimeOnDuty12);

                String datetimeOnDuty14 = "14:00";
                Date dateOnDuty14 = CommonUtils.getDutyDate(datetimeOnDuty14);

                String datetimeOffDuty73 = "17:30";
                Date dateOffDuty73 = CommonUtils.getDutyDate(datetimeOffDuty73);

                String datetimeOffDuty = "18:00";
                Date dateOffDuty = CommonUtils.getDutyDate(datetimeOffDuty);

                //09:00-09:30 迟到 - 09:30-12:00 旷工半天 - 14:00-18:00 旷工一天

                Integer attdState = 1;//1 缺勤(全天旷工) - 2 半天旷工 - 3 正常上班 - 4 正常下班 - 5 迟到 - 6 早退 - 7 请假 - 8 调休 - 9 出差

                /*if (dateNow.getTime() < dateOnDuty12.getTime()) {//旷工
                    attdState = 1;//已经打过卡，不做任何处理 - 但是此时点击的是 - 下班打卡
                }
                else */
                if (dateNow.getTime() > dateOnDuty12.getTime() && dateNow.getTime() < dateOffDuty73.getTime()) {//
                    attdState = 2;
                }
                else if (dateNow.getTime() > dateOffDuty73.getTime() && dateNow.getTime() < dateOffDuty.getTime()) {//
                    attdState = 6;
                }
                else if (dateNow.getTime() > dateOffDuty.getTime()) {//
                    attdState = 4;
                }
                /*else if (dateNow.getTime() > dateOffDuty14.getTime()) {//旷工 - 1

                }*/

                attendance.setAttdState(attdState);//

                rd = attendanceService.insert(attendance);//执行插入 Attendance 操作
            }
        }
        else {
            rd.setCode("ERROR");
            rd.setMsg("未签到不允许打卡，请联系管理员补卡");
        }

        return rd;
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
        Long attdTimeL = attendance.getCreateTime().getTime();//2018-03-14 08:00:00

        int attdState = attendance.getAttdState();
        //1 缺勤(全天旷工) - 2 半天旷工 - 3 正常上班 - 4 正常下班 - 5 迟到 - 6 早退 - 7 请假 - 8 调休 - 9 出差
        if (attdState == 1 || attdState == 4) {//
            attdTimeL += (long) 10 * 60 * 60 * 1000;
        }
        else if (attdState == 2) {
            attdTimeL += (long) 6 * 60 * 60 * 1000;
        }
        else if (attdState == 3) {
            attdTimeL += (long) 60 * 60 * 1000;
        }
        else if (attdState == 5) {
            attdTimeL += (long) 90 * 60 * 1000;
        }
        else if (attdState == 6) {
            attdTimeL += (long) (9 * 60 * 60 * 1000 + 30 * 60 * 1000);
        }
        else if (attdState == 7 || attdState == 8 || attdState == 9) {
            attdTimeL -= (long) (8 * 60 * 60 * 1000);
        }

        Timestamp attdTime = new Timestamp(attdTimeL);
        attendance.setCreateTime(attdTime);

        return attendanceService.update(attendance);//执行插入 Attendance 操作
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

        String createTimeStr = "";

        Timestamp createTime = attendance.getCreateTime();
        if (createTime != null) {
            createTimeStr = CommonUtils.timestampToDateStr(createTime);
            attendance.setCreateTime(null);
        }

        //分页查询
        return attendanceService.selectRelationDataByEmpRealname(page, rows, order_by, attendance, empRealname,
                createTimeStr);
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