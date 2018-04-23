package com.pengyd.service;


import com.pengyd.bean.EmpFriendInfo;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;

public interface EmpFriendInfoService {
 
 	/**
	  * 执行 EmpFriendInfo 插入操作
	  */
	ReturnData insert(EmpFriendInfo empFriendInfo);
	
	/**
	  * 执行 EmpFriendInfo 删除操作
	  */
	ReturnData delete(EmpFriendInfo empFriendInfo);
	
	/**
	  * 执行 EmpFriendInfo 批量删除操作
	  */
    ReturnData deleteBatch(String[] ids);
	
	/**
	  * 执行 EmpFriendInfo 修改操作
	  */
	ReturnData update(EmpFriendInfo empFriendInfo);
	
	/**
	  * 执行 EmpFriendInfo 分页查询操作
	  */
	JqGridJsonBean select(String page, String rows, String order_by, EmpFriendInfo empFriendInfo);
	
	/**
	  * 执行 EmpFriendInfo 分页查询操作 - 关联查询
	  */
    JqGridJsonBean selectRelationData(String page, String rows, String order_by, EmpFriendInfo empFriendInfo);
	
	/**
	  * 执行 EmpFriendInfo 查询不分页操作
	  */
	ReturnData selectByParam(String order_by, EmpFriendInfo empFriendInfo);
}