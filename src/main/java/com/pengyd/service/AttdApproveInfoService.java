package com.pengyd.service;


import com.pengyd.bean.AttdApproveInfo;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;

public interface AttdApproveInfoService {
 
 	/**
	  * 执行 AttdApproveInfo 插入操作
	  */
	ReturnData insert(AttdApproveInfo attdApproveInfo);
	
	/**
	  * 执行 AttdApproveInfo 删除操作
	  */
	ReturnData delete(AttdApproveInfo attdApproveInfo);
	
	/**
	  * 执行 AttdApproveInfo 批量删除操作
	  */
    ReturnData deleteBatch(String[] ids);
	
	/**
	  * 执行 AttdApproveInfo 修改操作
	  */
	ReturnData update(AttdApproveInfo attdApproveInfo);
	
	/**
	  * 执行 AttdApproveInfo 分页查询操作
	  */
	JqGridJsonBean select(String page, String rows, String order_by, AttdApproveInfo attdApproveInfo);
	
	/**
	  * 执行 AttdApproveInfo 分页查询操作 - 关联查询
	  */
    JqGridJsonBean selectRelationData(String page, String rows, String order_by, AttdApproveInfo attdApproveInfo);
	
	/**
	  * 执行 AttdApproveInfo 查询不分页操作
	  */
	ReturnData selectByParam(String order_by, AttdApproveInfo attdApproveInfo);
}