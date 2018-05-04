package com.pengyd.controller.activiti;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Component;


@Component
public class ActivitiConsoleUtils {

    @Resource(name = "processEngine") //来自配置文件中activiti和Spring的整合
    private ProcessEngine processEngine;

    /**
     * @description: TODO - 
     * 
     * 流程文件的上传与部署
     *
     * @createTime: 2018年4月11日 上午10:59:30
     * @param file 在action层文件上传的内容
     * @param processName 流程名称  
     * @throws Exception
     */
    public void deploy(File file, String processName) throws Exception {
        InputStream in = new FileInputStream(file);
        ZipInputStream zipInputStream = new ZipInputStream(in);

        processEngine.getRepositoryService().createDeployment().name(processName).addZipInputStream(zipInputStream)
                .deploy();
    }

    /**
     *  查询所有的部署信息
     */
    public List<Deployment> getAllDeployment() {
        return processEngine.getRepositoryService().createDeploymentQuery().orderByDeploymenTime().desc().list();
    }

    /**
     *  查询所有的流程定义的信息
     */
    public List<ProcessDefinition> getAllProcessDefinition() {
        return processEngine.getRepositoryService().createProcessDefinitionQuery().orderByProcessDefinitionVersion()
                .desc().list();
    }

    /**
     *  查询所有的流程定义的信息
     */
    public ProcessDefinition getProcessDefinitionByDeploymentID(String deploymentID) {
        ProcessDefinitionQuery processDefinitionQuery = processEngine.getRepositoryService()
                .createProcessDefinitionQuery();
        processDefinitionQuery = processDefinitionQuery.deploymentId(deploymentID);

        return processDefinitionQuery.orderByProcessDefinitionVersion().desc().list().get(0);
    }

    /**
     *   删除某一个部署
     */
    public void deleteDeployment(String deploymentId) {
        processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
    }

    /**
     *  查看流程图
     */
    public InputStream showImages(String pdid) {
        return processEngine.getRepositoryService().getProcessDiagram(pdid);
    }

    /**
     *
     */
    public void startPI(String attdApproveId, String attdApproveType, String empId) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("empId", empId);

        processEngine.getRuntimeService().startProcessInstanceByKey(attdApproveType, "" + attdApproveId, variables);//第二个参数是businesskey:请假单的主键
    }

    /**
     * 启动流程实例
     *   1、启动流程实例的api
     *   2、传入一个参数：新增加的请假单的id
     *   3、因为在提交申请的任务中有#{userID},所以在进入提交申请的任务之前，必须通过流程变量给userID赋值
     */
    /**
     *  根据任务执行人查看正在执行的任务  Assignee任务执行人  当前登录人登录系统以后要执行的任务
     */
    public List<Task> getTasksByAssignee(String userId) {
        //任务执行人当前存在的所有任务
        return processEngine.getTaskService().createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc()
                .list();
    }

    /**
     *  根据taskId得到当前任务所在的流程实例正在执行的节点的所有的sequenceFlow的名称
     */
    public List<PvmTransition> getPvmTransitions(String taskId) {
        ActivityImpl activityImpl = getActivityImplByTaskId(taskId);

        return activityImpl.getOutgoingTransitions();
    }

    /**
     *  根据taskId得到当前流程实例正在执行的节点ActivityImpl
     */
    public ActivityImpl getActivityImplByTaskId(String taskId) {
        //根据taskId获取到task
        Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();

        //根据task获取到pi
        ProcessInstance pi = processEngine.getRuntimeService().createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId()).singleResult();

        ProcessDefinitionEntity processDefinitionEntity = getProcessDefinitionEntityByTaskId(taskId);

        return processDefinitionEntity.findActivity(pi.getActivityId());
    }

    /**
     *  根据taskId获取到ProcessDefinitionEntity
     */
    public ProcessDefinitionEntity getProcessDefinitionEntityByTaskId(String taskId) {
        Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();

        return (ProcessDefinitionEntity) processEngine.getRepositoryService()
                .getProcessDefinition(task.getProcessDefinitionId());
    }

    /**
     *  根据taskId查找businessKey
     */
    public String getBusinessKeyByTaskId(String taskId) {
        Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();

        ProcessInstance pi = processEngine.getRuntimeService().createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId()).singleResult();

        return pi.getBusinessKey();
    }

    /**
     *  根据taskId完成任务，并且在完成任务以后判断流程实例是否结束
     */
    public ProcessInstance finishTask(String taskId) {
        //根据taskId提取任务、
        Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
        //根据任务得到piid
        String piid = task.getProcessInstanceId();

        processEngine.getTaskService().complete(taskId);

        //根据piid过滤流程实例
        ProcessInstance pi = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(piid)
                .singleResult();

        return pi;//如果整个流程实例结束了，则pi为null,如果没有结束就是一个对象
    }

    /**
     *  根据taskId完成任务，并且在完成任务以后判断流程实例是否结束
     */
    public ProcessInstance finishTask(String taskId, Map<String, Object> variables) {
        //根据taskId提取任务、
        Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
        //根据任务得到piid
        String piid = task.getProcessInstanceId();

        processEngine.getTaskService().complete(taskId, variables);

        //根据piid过滤流程实例
        ProcessInstance pi = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(piid)
                .singleResult();

        return pi;//如果整个流程实例结束了，则pi为null,如果没有结束就是一个对象
    }

    public List<Task> getTaskListByBusinessKey(String businessKey) {
        return processEngine.getTaskService().createTaskQuery().processInstanceBusinessKey(businessKey).list();
    }

    public ProcessInstance getProcessInstanceByBusinessKey(String businessKey) {
        return processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceBusinessKey(businessKey)
                .singleResult();
    }

    public ProcessInstance deleteProcessInstance(Task task) {
        //The task cannot be deleted because is part of a running process
        //processEngine.getTaskService().deleteTask(task.getId());

        //根据taskId提取任务 - 根据任务得到piid
        String piid = task.getProcessInstanceId();

        processEngine.getRuntimeService().deleteProcessInstance(piid, "系统删除");

        //根据piid过滤流程实例
        ProcessInstance pi = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(piid)
                .singleResult();

        return pi;//如果整个流程实例结束了，则pi为null,如果没有结束就是一个对象
    }

}
