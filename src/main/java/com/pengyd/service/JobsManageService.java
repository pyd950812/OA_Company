package com.pengyd.service;


import com.pengyd.bean.JobsManage;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;


public interface JobsManageService {
 
 	/**
	  * 执行 JobsManage 插入操作
	  */
	ReturnData insert(JobsManage jobsManage);
	
	/**
	  * 执行 JobsManage 删除操作
	  */
	ReturnData delete(JobsManage jobsManage);
	
	/**
	  * 执行 JobsManage 批量删除操作
	  */
    ReturnData deleteBatch(String[] ids);
	
	/**
	  * 执行 JobsManage 修改操作
	  */
	ReturnData update(JobsManage jobsManage);
	
	/**
	  * 执行 JobsManage 分页查询操作
	  */
	JqGridJsonBean select(String page, String rows, String order_by, JobsManage jobsManage);
	
	/**
	  * 执行 JobsManage 分页查询操作 - 关联查询
	  */
    JqGridJsonBean selectRelationData(String page, String rows, String order_by, JobsManage jobsManage);
	
	/**
	  * 执行 JobsManage 查询不分页操作
	  */
	ReturnData selectByParam(String order_by, JobsManage jobsManage);
}