package com.pengyd.controller;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengyd.bean.AttdApproveInfo;
import com.pengyd.bean.Employee;
import com.pengyd.controller.activiti.ActivitiConsoleUtils;
import com.pengyd.service.AttdApproveInfoService;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;
import org.activiti.engine.task.Task;
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

/**
 * @Author pengyd
 * @Date 2018/3/22 17:08
 * @function:  流程申请（请假、调休）
 */
@Controller
@RequestMapping(value = "/attd_approve_info")
public class AttdApproveInfoController {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private AttdApproveInfoService attdApproveInfoService;

    @Resource
    private ActivitiConsoleUtils activitiConsoleUtils;

    /**
     * 数据展示页面
     */
    @RequiresPermissions(value = "attd_approve_info_show")
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(Model model, HttpServletRequest request) {
        return "attd_approve_info/show";
    }

    /**
     * 数据新增页面
     */
    //@RequiresPermissions(value = "attd_approve_info_add")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request) {
        String approve_type = request.getParameter("approve_type");
        model.addAttribute("approve_type", approve_type);
        return "attd_approve_info/add";
    }

    /**
     * 数据修改页面
     */
    //@RequiresPermissions(value = "attd_approve_info_edit")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");

        AttdApproveInfo attdApproveInfo = new AttdApproveInfo();
        attdApproveInfo.setId(Integer.valueOf(Integer.parseInt(id)));

        ReturnData rd = attdApproveInfoService.selectByParam(null, attdApproveInfo);
        if (rd.getCode().equals("OK")) {
            List<AttdApproveInfo> data = (List<AttdApproveInfo>) rd.getData().get("data");

            model.addAttribute("olddata", JSON.toJSONString(data.get(0)));
        }
        return "attd_approve_info/edit";
    }

    /**
     * 对 attd_approve_info 的数据插入操作
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData insert(String jsonParam, Model model, HttpServletRequest request) {
        AttdApproveInfo attdApproveInfo = new Gson().fromJson(jsonParam, AttdApproveInfo.class);//, consumes = "application/json" - json 转对象

        Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));

        attdApproveInfo.setEmpId(currentEmp.getId());

        attdApproveInfo.setApproveState(0);//审批状态  - 0-等待提交 1-开始审批 2-审批中 3-审批通过 4-审批驳回

        return attdApproveInfoService.insert(attdApproveInfo);//执行插入 AttdApproveInfo 操作
    }

    /**
     * 对 attd_approve_info 的数据删除操作
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData delete(@RequestBody AttdApproveInfo attdApproveInfo, Model model, HttpServletRequest request) {
        return attdApproveInfoService.delete(attdApproveInfo);//执行删除 AttdApproveInfo  操作
    }

    /**
     * 对 attd_approve_info 的数据批量删除操作
     * @param request 请求数据
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
            rd = attdApproveInfoService.deleteBatch(ids.split(","));
        }
        return rd;
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData deleteById(Model model, HttpServletRequest request) {
        ReturnData rd = new ReturnData();
        String id = request.getParameter("id");
        if ((id == null) || ("".equals(id))) {
            rd.setCode("ERROR");
            rd.setMsg("id为空");
        }
        else {
            //判断相关的任务是否存在，存在的话，做删除操作
            List<Task> taskList = activitiConsoleUtils.getTaskListByBusinessKey(id);

            //删除任务并返回流程实例
            for (Task task : taskList) {
                activitiConsoleUtils.deleteProcessInstance(task);
            }

            AttdApproveInfo attdApproveInfo = new AttdApproveInfo();
            int idI = Integer.valueOf(id);//BusinessKey
            attdApproveInfo.setId(idI);

            rd = attdApproveInfoService.delete(attdApproveInfo);//执行删除 AttdApproveInfo  操作
        }
        return rd;
    }

    /**
     * 对 attd_approve_info 的数据修改操作
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST) //, consumes = "application/json"
    @ResponseBody
    public ReturnData update(String GridParam, Model model, HttpServletRequest request) {
        AttdApproveInfo attdApproveInfo = new Gson().fromJson(GridParam, AttdApproveInfo.class);//json 转对象

        return attdApproveInfoService.update(attdApproveInfo);//执行 AttdApproveInfo  操作
    }

    /**
     * 对 attd_approve_info 的数据分页查询操作
     */
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean select(String GridParam, Model model, HttpServletRequest request) {
        AttdApproveInfo attdApproveInfo = new Gson().fromJson(GridParam, AttdApproveInfo.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        //分页查询
        return attdApproveInfoService.select(page, rows, order_by, attdApproveInfo);
    }

    /**
     * 对 attd_approve_info 的数据分页查询操作 - 关联查询
     */
    @RequestMapping(value = "/selectRelationData", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean selectRelationData(String GridParam, Model model, HttpServletRequest request) {
        Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));

        AttdApproveInfo attdApproveInfo = new Gson().fromJson(GridParam, AttdApproveInfo.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        attdApproveInfo.setEmpId(currentEmp.getId());

        //分页查询
        return attdApproveInfoService.selectRelationData(page, rows, order_by, attdApproveInfo);
    }

    /**
     * 对 contract 的数据分页查询操作 - 关联查询
     */
    @RequestMapping(value = "/selectRelationDataByEmpRealname", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean selectRelationDataByEmpRealname(String GridParam, Model model, HttpServletRequest request) {
        AttdApproveInfo attdApproveInfo = new Gson().fromJson(GridParam, AttdApproveInfo.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        String empRealname = request.getParameter("empRealname");//

        //分页查询
        return attdApproveInfoService.selectRelationDataByEmpRealname(page, rows, order_by, attdApproveInfo,
                empRealname);
    }

    /**
     * 对 attd_approve_info 的数据查询操作不分页
     */
    @RequestMapping(value = "/selectByParam", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData selectByParam(@RequestBody AttdApproveInfo attdApproveInfo, Model model,
                                    HttpServletRequest request) {
        String order_by = request.getParameter("order_by");//排序

        return attdApproveInfoService.selectByParam(order_by, attdApproveInfo);
    }

    /**
     * 对 attd_approve_info 的数据导出操作
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response) {
        //1、使用JSONObject
        String json = request.getParameter("json");
        AttdApproveInfo attdApproveInfo = new Gson().fromJson(json, AttdApproveInfo.class);

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序
        //分页查询
        JqGridJsonBean rd = attdApproveInfoService.select(page, rows, order_by, attdApproveInfo);

        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("attdApproveInfo");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);

        //创建单元格并设置单元格内容
        row1.createCell(1 - 1).setCellValue("主键");
        row1.createCell(2 - 1).setCellValue("所属员工-提交审批人");
        row1.createCell(3 - 1).setCellValue("请假、调休或出差时长");
        row1.createCell(4 - 1).setCellValue("请假事由");
        row1.createCell(5 - 1).setCellValue("审批备注");
        row1.createCell(6 - 1).setCellValue("审批事件类型 1-请假申请 2-调休申请 3-出差申请");
        row1.createCell(7 - 1).setCellValue("审批内的开始日期");
        row1.createCell(8 - 1).setCellValue("审批内的结束日期");
        row1.createCell(9 - 1).setCellValue("审批提交时间");
        row1.createCell(10 - 1).setCellValue("审批状态 0-开始审批 1-审批中 2-审批通过 3-审批驳回");
        row1.createCell(11 - 1).setCellValue("修改时间");
        //在sheet里创建第三行
        @SuppressWarnings("unchecked")
        List<AttdApproveInfo> maps = (List<AttdApproveInfo>) rd.getRoot();
        for (int i = 0; i < maps.size(); i++) {
            AttdApproveInfo map = maps.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(1 - 1).setCellValue(map.getId() + "");
            row.createCell(2 - 1).setCellValue(map.getEmpId() + "");
            row.createCell(3 - 1).setCellValue(map.getApproveDays() + "");
            row.createCell(4 - 1).setCellValue(map.getApproveContent() + "");
            row.createCell(5 - 1).setCellValue(map.getApproveRemark() + "");
            row.createCell(6 - 1).setCellValue(map.getApproveType() + "");
            row.createCell(7 - 1).setCellValue(map.getApproveTimeStart() + "");
            row.createCell(8 - 1).setCellValue(map.getApproveTimeEnd() + "");
            row.createCell(9 - 1).setCellValue(map.getCreateTime() + "");
            row.createCell(10 - 1).setCellValue(map.getApproveState() + "");
            row.createCell(11 - 1).setCellValue(map.getUpdateTime() + "");
        }

        //输出Excel文件
        try {
            ServletOutputStream output = response.getOutputStream();
            String fileName = new String(("导出attdApproveInfo").getBytes(), "ISO8859_1");
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
     * 对 attd_approve_info 的数据导入操作
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
                    AttdApproveInfo attdApproveInfo = new AttdApproveInfo();
                    //System.out.println(row.getCell(0));
                    //此处自己添字段例如 myTable.set...(row.getCell(0))

                    //attdApproveInfoService.insert(attdApproveInfo);
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