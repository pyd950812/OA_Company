package com.pengyd.dao;


import java.util.List;
import java.util.Map;

import com.pengyd.bean.EmpFriendInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * @Author pengyd
 * @Date 2018/3/22 16:32
 * @function:
 */
@Repository
public interface EmpFriendInfoMapper{
	
	/**
	 * emp_friend_info 执行插入 EmpFriendInfo 操作
	 */
	void insert(EmpFriendInfo empFriendInfo);
	
	/**
	 * emp_friend_info 执行删除 数据操作
	 */
	void delete(EmpFriendInfo empFriendInfo);
	
	/**
	 * emp_friend_info 执行 批量删除 数据操作
	 */
    void deleteBatch(@Param("ids") String[] ids);

	/**
	 * emp_friend_info 执行修改 数据操作
	 */
	void update(EmpFriendInfo empFriendInfo);
	
	/**
	 * 根据条件查询EmpFriendInfo总数据量
	 */
	int selectCount(EmpFriendInfo empFriendInfo);
	
	/**
	 * 根据条件查询EmpFriendInfo数据
	 */
	List<EmpFriendInfo> selectData(@Param("empFriendInfo") EmpFriendInfo empFriendInfo, @Param("limit") int limit, @Param("offset") int offset, @Param("order_by") String order_by);
	
	/**
	 * 根据条件查询EmpFriendInfo总数据量 - 关联查询
	 */
	int selectRelationCount(EmpFriendInfo empFriendInfo);
	
	/**
	 * 根据条件查询EmpFriendInfo数据 - 关联查询
	 */
	List<Map<String, Object>> selectRelationData(@Param("empFriendInfo") EmpFriendInfo empFriendInfo, @Param("limit") int limit, @Param("offset") int offset, @Param("order_by") String order_by);
	
	/**
	 * 根据条件查询EmpFriendInfo数据不分页
	 */
	List<EmpFriendInfo> selectByParam(@Param("empFriendInfo") EmpFriendInfo empFriendInfo, @Param("order_by") String order_by);
}