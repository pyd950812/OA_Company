package com.pengyd.dao;


import java.util.List;
import java.util.Map;

import com.pengyd.bean.AttdApproveList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author pengyd
 * @Date 2018/3/22 16:32
 * @function:
 */
@Repository
public interface AttdApproveListMapper{
	
	/**
	 * attd_approve_list 执行插入 AttdApproveList 操作
	 */
	void insert(AttdApproveList attdApproveList);
	
	/**
	 * attd_approve_list 执行删除 数据操作
	 */
	void delete(AttdApproveList attdApproveList);
	
	/**
	 * attd_approve_list 执行 批量删除 数据操作
	 */
    void deleteBatch(@Param("ids") String[] ids);

	/**
	 * attd_approve_list 执行修改 数据操作
	 */
	void update(AttdApproveList attdApproveList);
	
	/**
	 * 根据条件查询AttdApproveList总数据量
	 */
	int selectCount(AttdApproveList attdApproveList);
	
	/**
	 * 根据条件查询AttdApproveList数据
	 */
	List<AttdApproveList> selectData(@Param("attdApproveList") AttdApproveList attdApproveList, @Param("limit") int limit, @Param("offset") int offset, @Param("order_by") String order_by);
	
	/**
	 * 根据条件查询AttdApproveList总数据量 - 关联查询
	 */
	int selectRelationCount(AttdApproveList attdApproveList);
	
	/**
	 * 根据条件查询AttdApproveList数据 - 关联查询
	 */
	List<Map<String, Object>> selectRelationData(@Param("attdApproveList") AttdApproveList attdApproveList, @Param("limit") int limit, @Param("offset") int offset, @Param("order_by") String order_by);
	
	/**
	 * 根据条件查询AttdApproveList数据不分页
	 */
	List<AttdApproveList> selectByParam(@Param("attdApproveList") AttdApproveList attdApproveList, @Param("order_by") String order_by);
}