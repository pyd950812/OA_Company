package com.pengyd.dao;


import java.util.List;
import java.util.Map;

import com.pengyd.bean.EmpFriend;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpFriendMapper{
	
	/**
	 * emp_friend 执行插入 EmpFriend 操作
	 */
	void insert(EmpFriend empFriend);
	
	/**
	 * emp_friend 执行删除 数据操作
	 */
	void delete(EmpFriend empFriend);
	
	/**
	 * emp_friend 执行 批量删除 数据操作
	 */
    void deleteBatch(@Param("ids") String[] ids);

	/**
	 * emp_friend 执行修改 数据操作
	 */
	void update(EmpFriend empFriend);
	
	/**
	 * 根据条件查询EmpFriend总数据量
	 */
	int selectCount(EmpFriend empFriend);
	
	/**
	 * 根据条件查询EmpFriend数据
	 */
	List<EmpFriend> selectData(@Param("empFriend") EmpFriend empFriend, @Param("limit") int limit, @Param("offset") int offset, @Param("order_by") String order_by);
	
	/**
	 * 根据条件查询EmpFriend总数据量 - 关联查询
	 */
	int selectRelationCount(EmpFriend empFriend);
	
	/**
	 * 根据条件查询EmpFriend数据 - 关联查询
	 */
	List<Map<String, Object>> selectRelationData(@Param("empFriend") EmpFriend empFriend, @Param("limit") int limit, @Param("offset") int offset, @Param("order_by") String order_by);
	
	/**
	 * 根据条件查询EmpFriend数据不分页
	 */
	List<EmpFriend> selectByParam(@Param("empFriend") EmpFriend empFriend, @Param("order_by") String order_by);
}