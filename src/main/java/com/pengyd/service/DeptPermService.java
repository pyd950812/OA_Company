package com.pengyd.service;


import com.pengyd.bean.DeptPerm;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;

/**
 * @Author pengyd
 * @Date 2018/3/22 17:00
 * @function:
 */
public interface DeptPermService {
 
 	/**
	  * 执行 DeptPerm 插入操作
	  */
	ReturnData insert(DeptPerm deptPerm);
	
	/**
	  * 执行 DeptPerm 删除操作
	  */
	ReturnData delete(DeptPerm deptPerm);
	
	/**
	  * 执行 DeptPerm 批量删除操作
	  */
    ReturnData deleteBatch(String[] ids);
	
	/**
	  * 执行 DeptPerm 修改操作
	  */
	ReturnData update(DeptPerm deptPerm);
	
	/**
	  * 执行 DeptPerm 分页查询操作
	  */
	JqGridJsonBean select(String page, String rows, String order_by, DeptPerm deptPerm);
	
	/**
	  * 执行 DeptPerm 分页查询操作 - 关联查询
	  */
    JqGridJsonBean selectRelationData(String page, String rows, String order_by, DeptPerm deptPerm);
	
	/**
	  * 执行 DeptPerm 查询不分页操作
	  */
	ReturnData selectByParam(String order_by, DeptPerm deptPerm);
}