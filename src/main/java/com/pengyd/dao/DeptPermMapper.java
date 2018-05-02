package com.pengyd.dao;


import java.util.List;
import java.util.Map;

import com.pengyd.bean.DeptPerm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author pengyd
 * @Date 2018/3/22 16:32
 * @function:
 */
@Repository
public interface DeptPermMapper{
	
	/**
	 * dept_perm 执行插入 DeptPerm 操作
	 */
	void insert(DeptPerm deptPerm);
	
	/**
	 * dept_perm 执行删除 数据操作
	 */
	void delete(DeptPerm deptPerm);
	
	/**
	 * dept_perm 执行 批量删除 数据操作
	 */
    void deleteBatch(@Param("ids") String[] ids);

	/**
	 * dept_perm 执行修改 数据操作
	 */
	void update(DeptPerm deptPerm);
	
	/**
	 * 根据条件查询DeptPerm总数据量
	 */
	int selectCount(DeptPerm deptPerm);
	
	/**
	 * 根据条件查询DeptPerm数据
	 */
	List<DeptPerm> selectData(@Param("deptPerm") DeptPerm deptPerm, @Param("limit") int limit, @Param("offset") int offset, @Param("order_by") String order_by);
	
	/**
	 * 根据条件查询DeptPerm总数据量 - 关联查询
	 */
	int selectRelationCount(DeptPerm deptPerm);
	
	/**
	 * 根据条件查询DeptPerm数据 - 关联查询
	 */
	List<Map<String, Object>> selectRelationData(@Param("deptPerm") DeptPerm deptPerm, @Param("limit") int limit, @Param("offset") int offset, @Param("order_by") String order_by);
	
	/**
	 * 根据条件查询DeptPerm数据不分页
	 */
	List<DeptPerm> selectByParam(@Param("deptPerm") DeptPerm deptPerm, @Param("order_by") String order_by);
}