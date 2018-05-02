package com.pengyd.controller.activiti.flow_diagram.listener;

import java.util.List;

import com.pengyd.bean.Employee;
import com.pengyd.bean.Jobpos;
import com.pengyd.service.EmployeeService;
import com.pengyd.service.JobposService;
import com.pengyd.util.ReturnData;
import com.pengyd.util.SpringContextUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



public class ManagerTaskHandler implements TaskListener {

    /**
     * 给MyTaskListener所在的userTask赋值任务的执行人
     */
    /**
     * 任务的执行人可以动态的赋值
     *   1、流程变量
     *        可以通过提取流程变量的方式给任务赋值执行人
     *   2、可以操作数据库
     *       WebApplicationContext ac = WebApplicationContextUtils
     *          .getWebApplicationContext(ServletActionContext.getServletContext());
            IEmployeeService employeeService = (IEmployeeService) ac.getBean("employeeService");  
            
    String value = (String) delegateTask.getVariable("aaa");
    delegateTask.setAssignee(value);
     * 
     */

    @Override
    public void notify(DelegateTask delegateTask) {
        ServletRequestAttributes srAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        Employee currentEmp = ((Employee) srAttributes.getRequest().getSession().getAttribute("current_emp"));

        JobposService jobposService = (JobposService) SpringContextUtil.getBean(JobposService.class);
        EmployeeService employeeService = (EmployeeService) SpringContextUtil.getBean(EmployeeService.class);

        Employee empP = null;

        //获取上级的名称
        int jobposId = currentEmp.getJobposId();
        Jobpos jobpos = new Jobpos();
        jobpos.setId(jobposId);
        ReturnData rd = jobposService.selectByParam(null, jobpos);
        List<Jobpos> data = (List<Jobpos>) rd.getData().get("data");
        if (data.size() > 0) {
            //基层职位是9位编码，可以有多人任职 - 领导职位只能一人任职 - 是6位编码
            Jobpos jobposTemp = data.get(0);
            String jobposCode = jobposTemp.getJobposCode();

            String jobposCodeP = "";

            if (jobposCode.length() == 9) {
                jobposCodeP = jobposCode.substring(0, 5) + 1;
            }
            else if (jobposCode.length() == 6) {
                jobposCodeP = jobposCode.substring(0, 2) + "01";

                if (!"0201".equals(jobposCodeP)) {//    //测试部和项管部的主要领导暂且替换为 - 技术总监
                    //其它部长的直属领导暂且都归到总经理
                    jobposCodeP = "0103";
                }
            }
            else if (jobposCode.length() == 4) {
                jobposCodeP = "0101";
            }

            if (!"".equals(jobposCodeP)) {
                jobpos = new Jobpos();
                jobpos.setJobposCode(jobposCodeP);
                rd = jobposService.selectByParam(null, jobpos);
                data = (List<Jobpos>) rd.getData().get("data");

                int jobposIdTemp = 1;
                if (data.size() > 0) {
                    jobposTemp = data.get(0);
                    jobposIdTemp = jobposTemp.getId();
                }

                Employee emp = new Employee();
                emp.setJobposId(jobposIdTemp);
                rd = employeeService.selectByParam(null, emp);
                List<Employee> dataEmp = (List<Employee>) rd.getData().get("data");
                if (dataEmp.size() > 0) {
                    empP = dataEmp.get(0);
                }
            }
        }

        if (empP != null) {
            //通过查询获取的用户，指定任务的办理人
            delegateTask.setAssignee(empP.getRealname());
        }
    }

}
