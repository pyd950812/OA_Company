package com.pengyd.controller;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengyd.bean.Employee;
import com.pengyd.bean.JobsManage;
import com.pengyd.controller.activiti.ActivitiConsoleUtils;
import com.pengyd.service.JobsManageService;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
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
 * @function: 任务
 */
@Controller
@RequestMapping(value = "/jobs_manage")
public class JobsManageController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private JobsManageService jobsManageService;

    @Resource
    private ActivitiConsoleUtils activitiConsoleUtils;

    /**
     * 数据展示页面
     */
    @RequiresPermissions(value = "jobs_manage_show")
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(Model model, HttpServletRequest request) {
        return "jobs_manage/show";
    }

    /**
     * 数据新增页面
     */
    //@RequiresPermissions(value = "jobs_manage_add")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request) {
        return "jobs_manage/add";
    }

    /**
     * 数据修改页面
     */
    //@RequiresPermissions(value = "jobs_manage_edit")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");

        JobsManage jobsManage = new JobsManage();
        jobsManage.setId(Integer.valueOf(Integer.parseInt(id)));

        ReturnData rd = jobsManageService.selectByParam(null, jobsManage);
        if (rd.getCode().equals("OK")) {
            List<JobsManage> data = (List<JobsManage>) rd.getData().get("data");

            model.addAttribute("olddata", JSON.toJSONString(data.get(0)));
        }
        return "jobs_manage/edit";
    }

    /**
     * 对 jobs_manage 的数据插入操作
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData insert(@RequestBody JobsManage jobsManage, Model model, HttpServletRequest request) {
        Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));

        jobsManage.setCreateUserId(currentEmp.getId());

        jobsManage.setJobState(0);//0-新建 1-进行中 2-已解决 3-已关闭 4-已驳回
        jobsManage.setJobWorkInfo("");//重置为""

        //开启相关的工作流程

        return jobsManageService.insert(jobsManage);//执行插入 JobsManage 操作
    }

    /**
     * 对 attd_approve_list 的数据插入操作
     */
    @RequestMapping(value = "/finishTask", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData finishTask(Model model, HttpServletRequest request) {
        //Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));

        String jobsManageId = request.getParameter("jobsManageId");
        String jobWorkInfo = request.getParameter("jobWorkInfo");

        JobsManage jobsManage = new JobsManage();
        jobsManage.setId(Integer.valueOf(jobsManageId));
        jobsManage.setJobWorkInfo(jobWorkInfo);

        //任务执行到下一级
        String taskId = request.getParameter("taskId");

        //得到当前正在执行的流程实例的节点的id的值
        ActivityImpl activityImpl = activitiConsoleUtils.getActivityImplByTaskId(taskId);
        String name = activityImpl.getProperty("name").toString();

        ProcessInstance pi = null;

        pi = activitiConsoleUtils.finishTask(taskId);

        jobsManage.setJobState(2);//0-新建 1-进行中 2-已解决

        return jobsManageService.update(jobsManage);
    }

    /**
     * 查看工作小结
     */
    @RequestMapping(value = "/viewJobWorkInfo", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData viewJobWorkInfo(Model model, HttpServletRequest request) {
        String jobsManageId = request.getParameter("jobsManageId");

        JobsManage jobsManage = new JobsManage();
        jobsManage.setId(Integer.valueOf(jobsManageId));
        return jobsManageService.selectByParam(null, jobsManage);
    }

    /**
     * 对 jobs_manage 的数据删除操作
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData delete(@RequestBody JobsManage jobsManage, Model model, HttpServletRequest request) {
        return jobsManageService.delete(jobsManage);//执行删除 JobsManage  操作
    }

    /**
     * 对 jobs_manage 的数据批量删除操作
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
            rd = jobsManageService.deleteBatch(ids.split(","));
        }
        return rd;
    }

    /**
     * 对 jobs_manage 的数据修改操作
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData update(@RequestBody JobsManage jobsManage, Model model, HttpServletRequest request) {
        return jobsManageService.update(jobsManage);//执行 JobsManage  操作
    }

    /**
     * 对 jobs_manage 的数据分页查询操作
     */
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean select(String GridParam, Model model, HttpServletRequest request) {
        JobsManage jobsManage = new Gson().fromJson(GridParam, JobsManage.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        //分页查询
        return jobsManageService.select(page, rows, order_by, jobsManage);
    }

    /**
     * 对 jobs_manage 的数据分页查询操作 - 关联查询
     */
    @RequestMapping(value = "/selectRelationData", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean selectRelationData(String GridParam, Model model, HttpServletRequest request) {
        JobsManage jobsManage = new Gson().fromJson(GridParam, JobsManage.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        //分页查询
        return jobsManageService.selectRelationData(page, rows, order_by, jobsManage);
    }

    /**
     * 对 contract 的数据分页查询操作 - 关联查询
     */
    @RequestMapping(value = "/selectRelationDataByEmpRealname", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean selectRelationDataByEmpRealname(String GridParam, Model model, HttpServletRequest request) {
        JobsManage jobsManage = new Gson().fromJson(GridParam, JobsManage.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        String empRealname = request.getParameter("empRealname");//

        //分页查询
        return jobsManageService.selectRelationDataByEmpRealname(page, rows, order_by, jobsManage, empRealname);
    }

    /**
     *  删除已分配的任务
     */
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

            JobsManage jobsManage = new JobsManage();
            int idI = Integer.valueOf(id);//BusinessKey
            jobsManage.setId(idI);

            rd = jobsManageService.delete(jobsManage);//
        }
        return rd;
    }

    /**
     * 对 jobs_manage 的数据查询操作不分页
     */
    @RequestMapping(value = "/selectByParam", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData selectByParam(@RequestBody JobsManage jobsManage, Model model, HttpServletRequest request) {
        String order_by = request.getParameter("order_by");//排序

        return jobsManageService.selectByParam(order_by, jobsManage);
    }

    /**
     * 对 jobs_manage 的数据导出操作
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response) {
        //1、使用JSONObject
        String json = request.getParameter("json");
        JobsManage jobsManage = new Gson().fromJson(json, JobsManage.class);

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序
        //分页查询
        JqGridJsonBean rd = jobsManageService.select(page, rows, order_by, jobsManage);

        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("jobsManage");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);

        //创建单元格并设置单元格内容
        row1.createCell(1 - 1).setCellValue("主键");
        row1.createCell(2 - 1).setCellValue("创建领导");
        row1.createCell(3 - 1).setCellValue("指定分配员工");
        row1.createCell(4 - 1).setCellValue("工作描述");
        row1.createCell(5 - 1).setCellValue("工作状态 0-新建 1-进行中 2-已解决 3-已关闭 4-已驳回");
        row1.createCell(6 - 1).setCellValue("创建时间");
        row1.createCell(7 - 1).setCellValue("修改时间");
        row1.createCell(8 - 1).setCellValue("工作具体小结");
        //在sheet里创建第三行
        @SuppressWarnings("unchecked")
        List<JobsManage> maps = (List<JobsManage>) rd.getRoot();
        for (int i = 0; i < maps.size(); i++) {
            JobsManage map = maps.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(1 - 1).setCellValue(map.getId() + "");
            row.createCell(2 - 1).setCellValue(map.getCreateUserId() + "");
            row.createCell(3 - 1).setCellValue(map.getWorkUserId() + "");
            row.createCell(4 - 1).setCellValue(map.getJobInfo() + "");
            row.createCell(5 - 1).setCellValue(map.getJobState() + "");
            row.createCell(6 - 1).setCellValue(map.getCreateTime() + "");
            row.createCell(7 - 1).setCellValue(map.getUpdateTime() + "");
            row.createCell(8 - 1).setCellValue(map.getJobWorkInfo() + "");
        }

        //输出Excel文件
        try {
            ServletOutputStream output = response.getOutputStream();
            String fileName = new String(("导出jobsManage").getBytes(), "ISO8859_1");
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
     * 对 jobs_manage 的数据导入操作
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
                    JobsManage jobsManage = new JobsManage();
                    //System.out.println(row.getCell(0));
                    //此处自己添字段例如 myTable.set...(row.getCell(0))

                    //jobsManageService.insert(jobsManage);
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