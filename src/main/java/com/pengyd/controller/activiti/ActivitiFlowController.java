package com.pengyd.controller.activiti;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengyd.bean.AttdApproveInfo;
import com.pengyd.bean.Employee;
import com.pengyd.bean.JobsManage;
import com.pengyd.service.AttdApproveInfoService;
import com.pengyd.service.EmployeeService;
import com.pengyd.service.JobsManageService;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;



@Controller
@RequestMapping(value = "/activiti_flow")
public class ActivitiFlowController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private ActivitiConsoleUtils activitiConsoleUtils;

    @Resource
    private AttdApproveInfoService attdApproveInfoService;

    @Resource
    private JobsManageService jobsManageService;

    @Resource
    private EmployeeService employeeService;

    /**
     * 数据展示页面
     * @return
     */
    @RequiresPermissions(value = "activiti_flow_show")
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(Model model, HttpServletRequest request) {
        return "activiti_flow/show";
    }

//    @RequiresPermissions(value = "activiti_flow_showTask")
    @RequestMapping(value = "/showTask", method = RequestMethod.GET)
    public String showTask(Model model, HttpServletRequest request) {
        return "activiti_flow/showTask";
    }

    /**
     *  任务执行，经理级审批员工请假、调休申请  前台任务执行点击办理
     */
//    @RequiresPermissions(value = "activiti_flow_handleTask")
    @RequestMapping(value = "/handleTask", method = RequestMethod.GET)
    public String handleTask(Model model, HttpServletRequest request) {
        String taskId = request.getParameter("taskId");

        model.addAttribute("taskId", taskId);
        //根据taskId查找businessKey   businessKey关联到请假表的id 通过ID可以找到对应的申请记录
        String businessKey = activitiConsoleUtils.getBusinessKeyByTaskId(taskId);
        AttdApproveInfo attdApproveInfo = new AttdApproveInfo();
        attdApproveInfo.setId(Integer.valueOf(businessKey));
        ReturnData rdAttdApproveInfo = attdApproveInfoService.selectByParam(null, attdApproveInfo);
        List<AttdApproveInfo> dataAttdApproveInfo = (List<AttdApproveInfo>) rdAttdApproveInfo.getData().get("data");

        if (dataAttdApproveInfo.size() > 0) {
            model.addAttribute("attdApproveInfo", JSONObject.toJSONString(dataAttdApproveInfo.get(0)));//请假单的回显
        }
        //根据taskId得到当前任务所在的流程实例正在执行的节点的所有的sequenceFlow的名称
        List<PvmTransition> pvmTransitions = activitiConsoleUtils.getPvmTransitions(taskId);
        model.addAttribute("outcomeListSize", pvmTransitions.size());//该任务的sequenceFlow

        return "activiti_flow/handleTask";
    }

//    @RequiresPermissions(value = "activiti_flow_finishTask")
    @RequestMapping(value = "/finishTask", method = RequestMethod.GET)
    public String finishTask(Model model, HttpServletRequest request) {
        String taskId = request.getParameter("taskId");

        model.addAttribute("taskId", taskId);

        //根据taskId查找businessKey
        String businessKey = activitiConsoleUtils.getBusinessKeyByTaskId(taskId);

        //更新相关的状态值为 进行中
        /*JobsManage jobsManage = new JobsManage();
        jobsManage.setId(Integer.valueOf(businessKey));*/

        model.addAttribute("jobsManageId", businessKey);//该任务的sequenceFlow

        List<PvmTransition> pvmTransitions = activitiConsoleUtils.getPvmTransitions(taskId);
        model.addAttribute("outcomeListSize", pvmTransitions.size());//该任务的sequenceFlow

        return "activiti_flow/finishTask";
    }

    /**
     * 数据新增页面
     */
//    @RequiresPermissions(value = "activiti_flow_add")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request) {
        return "activiti_flow/add";
    }

    /**
     * 部署信息管理列表 - getAllDeployment
     * 
     * 流程定义信息列表 - getAllProcessDefinition
     * 
     */
    @RequestMapping(value = "/selectAllDeployment", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean selectAllDeployment(Model model, HttpServletRequest request) {
        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        JqGridJsonBean jgjb = new JqGridJsonBean();
        try {
            int _page = Integer.parseInt(page);
            int _rows = Integer.parseInt(rows);
            //没有order_by 默认主键排序
            if (order_by != null && !"".equals(order_by)) {
                //不变
            }
            else {
                order_by = "id";
            }

            //找到所有的流程部署信息
            List<Deployment> deployments = activitiConsoleUtils.getAllDeployment();//org.activiti.engine.impl.persistence.entity.DeploymentEntity

            //将对象转换为JSON

            //查询Attendance总数据量
            //int count = attendanceMapper.selectRelationCount(attendance);

            int count = deployments.size();

            //根据查询条件查询总页数
            int pages = (count % Integer.parseInt(rows)) == 0 ? (count / _rows) : ((count / _rows) + 1);

            /*List<Map<String, Object>> data = attendanceMapper.selectRelationData(attendance, _rows, (_page - 1) * _rows,
                    order_by);*/

            jgjb.setPage(_page);// 第几页
            jgjb.setRecords(count);// 总数据量
            jgjb.setTotal(pages);// 总页数

            //jgjb.setRoot(deployments);// 查询数据信息

            //无法取出相关的数据
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

            for (int i = 0; i < count; i++) {
                Map<String, Object> dataMap = new HashMap<String, Object>();
                DeploymentEntity deployment = (DeploymentEntity) deployments.get(i);

                String deploymentID = deployment.getId();

                dataMap.put("id", deploymentID);

                //通过部署ID得到流程定义的ID
                ProcessDefinition processDefinition = activitiConsoleUtils
                        .getProcessDefinitionByDeploymentID(deploymentID);

                dataMap.put("pdid", processDefinition.getId());

                dataMap.put("name", deployment.getName());
                dataMap.put("deploymentTime", deployment.getDeploymentTime());

                data.add(dataMap);
            }
            jgjb.setRoot(data);// 查询数据信息
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //操作异常,返回错误信息
        }
        return jgjb;
    }

    @RequestMapping(value = "/insertActivitiFlow", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData insert(HttpServletRequest request) {
        ReturnData rd = new ReturnData();

        //Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));

        //采用spring提供的上传文件的方法

        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            String activitiFlowName = multiRequest.getParameter("activitiFlowName");

            MultipartFile file = multiRequest.getFile("activitiFlowFileName");

            if (file != null) {
                String pathName = "F:/graduationdoc/activitiFlow/";
                File dirFile = new File(pathName);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }

                /*System.out.println(file.getContentType());//application/vnd.openxmlformats-officedocument.wordprocessingml.document
                System.out.println(file.getName());//file
                System.out.println(file.getOriginalFilename());//xxx.docx
                System.out.println(file.getSize());*///11078

                String fileName = file.getOriginalFilename();
                /*String fileNameSuffix = fileName.substring(fileName.lastIndexOf("."));
                
                String docName = empRealname + fileNameSuffix;
                String docPathName = pathName + empRealname + fileNameSuffix;*/

                String docPathName = pathName + fileName;

                //上传
                try {
                    file.transferTo(new File(docPathName));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                File deployFile = new File(docPathName);

                //zip文件 流程部署
                if (deployFile.exists()) {
                    try {
                        activitiConsoleUtils.deploy(deployFile, activitiFlowName);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                    //deployFile.deleteOnExit();
                    boolean result = deployFile.delete();
                    int tryCount = 0;
                    while (!result && tryCount++ < 10) {
                        System.gc(); //回收资源
                        result = deployFile.delete();
                    }
                }
            }
        }

        /*rd.setCode("ERROR");
        rd.setMsg(e.getMessage());*/
        rd.setCode("OK");
        rd.setMsg("数据导入成功");
        return rd;
    }

    /**
     * 查看流程部署的流程图  并且写到输出流中
     */
    @RequestMapping(value = "/viewImage", method = RequestMethod.GET)
    public void viewImage(Model model, HttpServletRequest request, HttpServletResponse response) {
        String pdid = request.getParameter("pdid");
        InputStream in = activitiConsoleUtils.showImages(pdid);

        try {
            //3：得到response对象，获取response对象中的输出流
            OutputStream out = response.getOutputStream();
            //4:将Inputstream流对象中的数据读取出来，写到输出流中
            for (int b = -1; (b = in.read()) != -1;) {
                out.write(b);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //查看流程图，不需要指定页面的时候，返回null即可
        //return null;
    }

    /**
     * 删除流程部署 - 被使用中的流程部署项，禁止删除
     */
//    @RequiresPermissions(value = "activiti_flow_delDeployment")
    @RequestMapping(value = "/delDeployment")
    @ResponseBody
    public ReturnData delDeployment(Model model, HttpServletRequest request) {
        String depid = request.getParameter("depid");
        activitiConsoleUtils.deleteDeployment(depid);

        ReturnData rd = new ReturnData();
        rd.setCode("OK");
        rd.setMsg("数据删除成功");
        return rd;
    }

    /**
     * 提交审批任务
     */
    @RequestMapping(value = "/commitAttdApprove")
    @ResponseBody
    public ReturnData commitAttdApprove(Model model, HttpServletRequest request) {
        Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));
        String empName = currentEmp.getRealname();

        String attdApproveId = request.getParameter("attdApproveId");
        String attdApproveType = request.getParameter("attdApproveType");

        //从数据库的请假类型匹配到相关的部署项，通过部署ID取出流程ID，截取取出前缀

        //1-请假申请 - AskForLeave 2-调休申请 - ExchangeHoliday 3-出差申请 - OnBusiness
        if ("1".equals(attdApproveType)) {//请假
            activitiConsoleUtils.startPI(attdApproveId, "AskForLeave", empName);
        }
        else if ("2".equals(attdApproveType)) {
            activitiConsoleUtils.startPI(attdApproveId, "ExchangeHoliday", empName);
        }
        else if ("3".equals(attdApproveType)) {
            activitiConsoleUtils.startPI(attdApproveId, "OnBusiness", empName);
        }

        //更新相关的状态值为 开始审批
        AttdApproveInfo attdApproveInfo = new AttdApproveInfo();
        attdApproveInfo.setId(Integer.valueOf(attdApproveId));
        attdApproveInfo.setApproveState(1);//开始审批

        attdApproveInfoService.update(attdApproveInfo);

        ReturnData rd = new ReturnData();
        rd.setCode("OK");
        rd.setMsg("审批提交成功");
        return rd;
    }

    /**
     * 任务分配  点击分配任务
     */
    @RequestMapping(value = "/commitJobsManage")
    @ResponseBody
    public ReturnData commitJobsManage(Model model, HttpServletRequest request) {
        //任务ID
        String jobsManageId = request.getParameter("jobsManageId");
        //被指定的员工ID
        String workUserId = request.getParameter("workUserId");
        //根据被指定的员工的id 开启一个工作流程
        Employee employee = new Employee();
        employee.setId(Integer.valueOf(workUserId));
        ReturnData rData = employeeService.selectByParam(null, employee);
        List<Employee> employeeList = (List<Employee>) rData.getData().get("data");

        activitiConsoleUtils.startPI(jobsManageId, "PublishTask", employeeList.get(0).getRealname());

        //点击分配任务后，将该任务的状态值设为 ----进行中
        JobsManage jobsManage = new JobsManage();
        jobsManage.setId(Integer.valueOf(jobsManageId));
        jobsManage.setJobState(1);

        jobsManageService.update(jobsManage);

        ReturnData rd = new ReturnData();
        rd.setCode("OK");
        rd.setMsg("审批提交成功");
        return rd;
    }

    /**
     * 根据员工姓名找到与之相关的所有的任务执行列表
     */
    @RequestMapping(value = "/selectEmpTastList", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean selectEmpTastList(Model model, HttpServletRequest request) {
        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        JqGridJsonBean jgjb = new JqGridJsonBean();
        try {
            int _page = Integer.parseInt(page);
            int _rows = Integer.parseInt(rows);
            //没有order_by 默认主键排序
            if (order_by != null && !"".equals(order_by)) {
                //不变
            }
            else {
                order_by = "id";
            }

            Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));
            String empName = currentEmp.getRealname();
            //根据员工的真实姓名找到属于员工的任务
            List<Task> tasks = activitiConsoleUtils.getTasksByAssignee(empName);

            int count = tasks.size();

            //根据查询条件查询总页数
            int pages = (count % Integer.parseInt(rows)) == 0 ? (count / _rows) : ((count / _rows) + 1);

            /*List<Map<String, Object>> data = attendanceMapper.selectRelationData(attendance, _rows, (_page - 1) * _rows,
                    order_by);*/

            jgjb.setPage(_page);// 第几页
            jgjb.setRecords(count);// 总数据量
            jgjb.setTotal(pages);// 总页数

            //jgjb.setRoot(deployments);// 查询数据信息

            //无法取出相关的数据
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

            for (int i = 0; i < count; i++) {
                Map<String, Object> dataMap = new HashMap<String, Object>();
                Task task = (Task) tasks.get(i);

                dataMap.put("id", task.getId());//任务ID
                dataMap.put("name", task.getName());//任务名称
                dataMap.put("createTime", task.getCreateTime());//创建时间
                dataMap.put("assignee", task.getAssignee());//办理人

                data.add(dataMap);
            }
            jgjb.setRoot(data);// 查询数据信息
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //操作异常,返回错误信息
        }
        return jgjb;
    }

    /**
     * 任务执行界面，点击查看流程图   查看流程部署的流程图
     */
    @RequestMapping(value = "/viewCurrentImage", method = RequestMethod.GET)
    public String viewCurrentImage(Model model, HttpServletRequest request, HttpServletResponse response) {
        String taskId = request.getParameter("taskId");
        //根据taskID来查看当前流程执行到哪一个节点ActivityImpl
        ActivityImpl activityImpl = activitiConsoleUtils.getActivityImplByTaskId(taskId);

        ProcessDefinitionEntity processDefinitionEntity = activitiConsoleUtils
                .getProcessDefinitionEntityByTaskId(taskId);

        model.addAttribute("deploymentId", processDefinitionEntity.getId());
        model.addAttribute("imageName", processDefinitionEntity.getName());

        JSONObject jsonObj = new JSONObject();

        jsonObj.put("x", activityImpl.getX());
        jsonObj.put("y", activityImpl.getY());
        jsonObj.put("width", activityImpl.getWidth());
        jsonObj.put("height", activityImpl.getHeight());

        model.addAttribute("acs", jsonObj);

        return "activiti_flow/workflowImg";
    }

}