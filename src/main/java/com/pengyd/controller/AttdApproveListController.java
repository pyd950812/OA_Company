package com.pengyd.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengyd.bean.AttdApproveInfo;
import com.pengyd.bean.AttdApproveList;
import com.pengyd.bean.Employee;
import com.pengyd.controller.activiti.ActivitiConsoleUtils;
import com.pengyd.service.AttdApproveInfoService;
import com.pengyd.service.AttdApproveListService;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
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
@RequestMapping(value = "/attd_approve_list")
public class AttdApproveListController {


    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private AttdApproveListService attdApproveListService;

    @Resource
    private ActivitiConsoleUtils activitiConsoleUtils;

    @Resource
    private AttdApproveInfoService attdApproveInfoService;

    /**
     * 数据展示页面
     */
    @RequiresPermissions(value = "attd_approve_list_show")
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(Model model, HttpServletRequest request) {
        return "attd_approve_list/show";
    }

    /**
     * 数据新增页面
     */
    @RequiresPermissions(value = "attd_approve_list_add")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request) {
        return "attd_approve_list/add";
    }

    /**
     * 数据修改页面
     */
    @RequiresPermissions(value = "attd_approve_list_edit")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");

        AttdApproveList attdApproveList = new AttdApproveList();
        attdApproveList.setId(Integer.valueOf(Integer.parseInt(id)));

        ReturnData rd = attdApproveListService.selectByParam(null, attdApproveList);
        if (rd.getCode().equals("OK")) {
            List<AttdApproveList> data = (List<AttdApproveList>) rd.getData().get("data");

            model.addAttribute("olddata", JSON.toJSONString(data.get(0)));
        }
        return "attd_approve_list/edit";
    }

    /**
     * 对 attd_approve_list 的数据插入操作
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData insert(Model model, HttpServletRequest request) {
        Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));

        //添加批注列表信息
        String attdApproveInfoId = request.getParameter("attdApproveInfoId");

        String annotation = request.getParameter("annotation");

        AttdApproveList attdApproveList = new AttdApproveList();

        int attdApproveInfoIdI = Integer.valueOf(attdApproveInfoId);

        attdApproveList.setaAInfoId(attdApproveInfoIdI);
        attdApproveList.setAnnotation(annotation);
        attdApproveList.setEmpId(currentEmp.getId());

        //任务执行到下一级
        String taskId = request.getParameter("taskId");

        //得到当前正在执行的流程实例的节点的id的值
        ActivityImpl activityImpl = activitiConsoleUtils.getActivityImplByTaskId(taskId);
        String name = activityImpl.getProperty("name").toString();

        AttdApproveInfo attdApproveInfo = new AttdApproveInfo();
        attdApproveInfo.setId(attdApproveInfoIdI);

        ProcessInstance pi = null;
        //if ("提交申请".equals(name)) {
        if ("审批【部门经理】".equals(name)) {
            attdApproveInfo.setApproveState(2);//如果当前的任务是提交申请，则设置状态值为1 - 0-等待提交 1-开始审批 2-审批中 3-审批通过 4-审批驳回
            attdApproveInfoService.update(attdApproveInfo);

            //完成当前的任务,并且返回一个流程实例
            pi = activitiConsoleUtils.finishTask(taskId);
        }
        /*else if (name.contains("审批")) {
            attdApproveInfo.setApproveState(2);//如果当前的任务是提交申请，则设置状态值为1 - 0-等待提交 1-开始审批 2-审批中 3-审批通过 4-审批驳回
            attdApproveInfoService.update(attdApproveInfo);
        }*/
        else if ("审批【技术总监/总经理】".equals(name)) {
            //获取到请假的天数
            ReturnData rdData = attdApproveInfoService.selectByParam(null, attdApproveInfo);
            List<AttdApproveInfo> dataList = (List<AttdApproveInfo>) rdData.getData().get("data");
            AttdApproveInfo attdApproveInfoTemp = dataList.get(0);

            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("days", attdApproveInfoTemp.getApproveDays());

            pi = activitiConsoleUtils.finishTask(taskId, variables);
        }
        else if ("审批【董事长】".equals(name)) {
            pi = activitiConsoleUtils.finishTask(taskId);
        }

        if (pi == null) {//该流程已经完成了
            attdApproveInfo.setApproveState(3);//如果当前的任务是提交申请，则设置状态值为1 - 0-等待提交 1-开始审批 2-审批中 3-审批通过 4-审批驳回
            attdApproveInfoService.update(attdApproveInfo);
        }

        return attdApproveListService.insert(attdApproveList);//执行插入 AttdApproveList 操作
    }

    /**
     * 对 attd_approve_list 的数据删除操作
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData delete(@RequestBody AttdApproveList attdApproveList, Model model, HttpServletRequest request) {
        return attdApproveListService.delete(attdApproveList);//执行删除 AttdApproveList  操作
    }

    /**
     * 对 attd_approve_list 的数据批量删除操作
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
            rd = attdApproveListService.deleteBatch(ids.split(","));
        }
        return rd;
    }

    /**
     * 对 attd_approve_list 的数据修改操作
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData update(@RequestBody AttdApproveList attdApproveList, Model model, HttpServletRequest request) {
        Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));

        return attdApproveListService.update(attdApproveList);//执行 AttdApproveList  操作
    }

    /**
     * 对 attd_approve_list 的数据分页查询操作
     */
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean select(String GridParam, Model model, HttpServletRequest request) {
        AttdApproveList attdApproveList = new Gson().fromJson(GridParam, AttdApproveList.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        //分页查询
        return attdApproveListService.select(page, rows, order_by, attdApproveList);
    }

    /**
     * 对 attd_approve_list 的数据分页查询操作 - 关联查询
     */
    @RequestMapping(value = "/selectRelationData", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean selectRelationData(String GridParam, Model model, HttpServletRequest request) {
        AttdApproveList attdApproveList = new Gson().fromJson(GridParam, AttdApproveList.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        //分页查询
        return attdApproveListService.selectRelationData(page, rows, order_by, attdApproveList);
    }

    @RequestMapping(value = "/selectHisRelationData", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean selectHisRelationData(Model model, HttpServletRequest request) {
        String taskId = request.getParameter("taskId");

        String businessKey = activitiConsoleUtils.getBusinessKeyByTaskId(taskId);//就是关联的请假单的 - attdApproveInfoId - aAInfoId

        AttdApproveList attdApproveList = new AttdApproveList();

        attdApproveList.setaAInfoId(Integer.valueOf(businessKey));

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        //分页查询
        return attdApproveListService.selectRelationData(page, rows, order_by, attdApproveList);
    }

    /**
     * 对 attd_approve_list 的数据查询操作不分页
     */
    @RequestMapping(value = "/selectByParam", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData selectByParam(@RequestBody AttdApproveList attdApproveList, Model model,
                                    HttpServletRequest request) {
        String order_by = request.getParameter("order_by");//排序

        return attdApproveListService.selectByParam(order_by, attdApproveList);
    }

    /**
     * 对 attd_approve_list 的数据导出操作
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response) {
        //1、使用JSONObject
        String json = request.getParameter("json");
        AttdApproveList attdApproveList = new Gson().fromJson(json, AttdApproveList.class);

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序
        //分页查询
        JqGridJsonBean rd = attdApproveListService.select(page, rows, order_by, attdApproveList);

        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("attdApproveList");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);

        //创建单元格并设置单元格内容
        row1.createCell(1 - 1).setCellValue("主键");
        row1.createCell(2 - 1).setCellValue("批注用户");
        row1.createCell(3 - 1).setCellValue("批注内容");
        row1.createCell(4 - 1).setCellValue("批注日期");
        row1.createCell(5 - 1).setCellValue("关联的审批申请表的ID");
        //在sheet里创建第三行
        @SuppressWarnings("unchecked")
        List<AttdApproveList> maps = (List<AttdApproveList>) rd.getRoot();
        for (int i = 0; i < maps.size(); i++) {
            AttdApproveList map = maps.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(1 - 1).setCellValue(map.getId() + "");
            row.createCell(2 - 1).setCellValue(map.getEmpId() + "");
            row.createCell(3 - 1).setCellValue(map.getAnnotation() + "");
            row.createCell(4 - 1).setCellValue(map.getCreateTime() + "");
            row.createCell(5 - 1).setCellValue(map.getaAInfoId() + "");
        }

        //输出Excel文件
        try {
            ServletOutputStream output = response.getOutputStream();
            String fileName = new String(("导出attdApproveList").getBytes(), "ISO8859_1");
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
     * 对 attd_approve_list 的数据导入操作
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
                    AttdApproveList attdApproveList = new AttdApproveList();
                    //System.out.println(row.getCell(0));
                    //此处自己添字段例如 myTable.set...(row.getCell(0))

                    //attdApproveListService.insert(attdApproveList);
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