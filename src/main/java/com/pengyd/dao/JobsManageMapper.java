package com.pengyd.dao;

import java.util.List;
import java.util.Map;

import com.pengyd.bean.JobsManage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author pengyd
 * @Date 2018/3/22 16:32
 * @function:
 */
@Repository
public interface JobsManageMapper{
	
	/**
	 * jobs_manage 执行插入 JobsManage 操作
	 */
	void insert(JobsManage jobsManage);
	
	/**
	 * jobs_manage 执行删除 数据操作
	 */
	void delete(JobsManage jobsManage);
	
	/**
	 * jobs_manage 执行 批量删除 数据操作
	 */
    void deleteBatch(@Param("ids") String[] ids);

	/**
	 * jobs_manage 执行修改 数据操作
	 */
	void update(JobsManage jobsManage);
	
	/**
	 * 根据条件查询JobsManage总数据量
	 */
	int selectCount(JobsManage jobsManage);
	
	/**
	 * 根据条件查询JobsManage数据
	 */
	List<JobsManage> selectData(@Param("jobsManage") JobsManage jobsManage, @Param("limit") int limit, @Param("offset") int offset, @Param("order_by") String order_by);
	
	/**
	 * 根据条件查询JobsManage总数据量 - 关联查询
	 */
	int selectRelationCount(JobsManage jobsManage);
	
	/**
	 * 根据条件查询JobsManage数据 - 关联查询
	 */
	List<Map<String, Object>> selectRelationData(@Param("jobsManage") JobsManage jobsManage, @Param("limit") int limit, @Param("offset") int offset, @Param("order_by") String order_by);
	
	/**
	 * 根据条件查询JobsManage数据不分页
	 */
	List<JobsManage> selectByParam(@Param("jobsManage") JobsManage jobsManage, @Param("order_by") String order_by);


	int selectRelationCountByEmpRealname(@Param("jobsManage") JobsManage jobsManage,
										 @Param("empRealname") String empRealname);

	List<Map<String, Object>> selectRelationDataByEmpRealname(@Param("jobsManage") JobsManage jobsManage,
															  @Param("limit") int limit, @Param("offset") int offset, @Param("order_by") String order_by,
															  @Param("empRealname") String empRealname);
}