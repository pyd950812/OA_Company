package com.pengyd.service;


import com.pengyd.bean.AttdApproveList;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;


public interface AttdApproveListService {
 
 	/**
	  * 执行 AttdApproveList 插入操作
	  */
	ReturnData insert(AttdApproveList attdApproveList);
	
	/**
	  * 执行 AttdApproveList 删除操作
	  */
	ReturnData delete(AttdApproveList attdApproveList);
	
	/**
	  * 执行 AttdApproveList 批量删除操作
	  */
    ReturnData deleteBatch(String[] ids);
	
	/**
	  * 执行 AttdApproveList 修改操作
	  */
	ReturnData update(AttdApproveList attdApproveList);
	
	/**
	  * 执行 AttdApproveList 分页查询操作
	  */
	JqGridJsonBean select(String page, String rows, String order_by, AttdApproveList attdApproveList);
	
	/**
	  * 执行 AttdApproveList 分页查询操作 - 关联查询
	  */
    JqGridJsonBean selectRelationData(String page, String rows, String order_by, AttdApproveList attdApproveList);
	
	/**
	  * 执行 AttdApproveList 查询不分页操作
	  */
	ReturnData selectByParam(String order_by, AttdApproveList attdApproveList);
}