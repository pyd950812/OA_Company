package com.pengyd.service;


import com.pengyd.bean.EmpFriend;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;

public interface EmpFriendService {
 
 	/**
	  * 执行 EmpFriend 插入操作
	  */
	ReturnData insert(EmpFriend empFriend);
	
	/**
	  * 执行 EmpFriend 删除操作
	  */
	ReturnData delete(EmpFriend empFriend);
	
	/**
	  * 执行 EmpFriend 批量删除操作
	  */
    ReturnData deleteBatch(String[] ids);
	
	/**
	  * 执行 EmpFriend 修改操作
	  */
	ReturnData update(EmpFriend empFriend);
	
	/**
	  * 执行 EmpFriend 分页查询操作
	  */
	JqGridJsonBean select(String page, String rows, String order_by, EmpFriend empFriend);
	
	/**
	  * 执行 EmpFriend 分页查询操作 - 关联查询
	  */
    JqGridJsonBean selectRelationData(String page, String rows, String order_by, EmpFriend empFriend);
	
	/**
	  * 执行 EmpFriend 查询不分页操作
	  */
	ReturnData selectByParam(String order_by, EmpFriend empFriend);
}