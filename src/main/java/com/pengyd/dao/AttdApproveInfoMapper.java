package com.pengyd.dao;


import java.util.List;
import java.util.Map;

import com.pengyd.bean.AttdApproveInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttdApproveInfoMapper{
	
	/**
	 * attd_approve_info 执行插入 AttdApproveInfo 操作
	 */
	void insert(AttdApproveInfo attdApproveInfo);
	
	/**
	 * attd_approve_info 执行删除 数据操作
	 */
	void delete(AttdApproveInfo attdApproveInfo);
	
	/**
	 * attd_approve_info 执行 批量删除 数据操作
	 */
    void deleteBatch(@Param("ids") String[] ids);

	/**
	 * attd_approve_info 执行修改 数据操作
	 */
	void update(AttdApproveInfo attdApproveInfo);
	
	/**
	 * 根据条件查询AttdApproveInfo总数据量
	 */
	int selectCount(AttdApproveInfo attdApproveInfo);
	
	/**
	 * 根据条件查询AttdApproveInfo数据
	 */
	List<AttdApproveInfo> selectData(@Param("attdApproveInfo") AttdApproveInfo attdApproveInfo, @Param("limit") int limit, @Param("offset") int offset, @Param("order_by") String order_by);
	
	/**
	 * 根据条件查询AttdApproveInfo总数据量 - 关联查询
	 */
	int selectRelationCount(AttdApproveInfo attdApproveInfo);
	
	/**
	 * 根据条件查询AttdApproveInfo数据 - 关联查询
	 * @param attdApproveInfo
	 */
	List<Map<String, Object>> selectRelationData(@Param("attdApproveInfo") AttdApproveInfo attdApproveInfo, @Param("limit") int limit, @Param("offset") int offset, @Param("order_by") String order_by);
	
	/**
	 * 根据条件查询AttdApproveInfo数据不分页
	 * @param attdApproveInfo
	 */
	List<AttdApproveInfo> selectByParam(@Param("attdApproveInfo") AttdApproveInfo attdApproveInfo, @Param("order_by") String order_by);
}