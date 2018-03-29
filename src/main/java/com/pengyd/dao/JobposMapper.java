package com.pengyd.dao;

import java.util.List;
import java.util.Map;

import com.pengyd.bean.Jobpos;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @Author pengyd
 * @Date 2018/3/22 16:32
 * @function:
 */
@Repository
public interface JobposMapper {

    /**
     * jobpos 执行插入 Jobpos 操作
     */
    void insert(Jobpos jobpos);

    /**
     * jobpos 执行删除 数据操作
     */
    void delete(Jobpos jobpos);

    /**
     * jobpos 执行 批量删除 数据操作
     */
    void deleteBatch(@Param("ids") String[] ids);

    /**
     * jobpos 执行修改 数据操作
     */
    void update(Jobpos jobpos);

    /**
     * 根据条件查询Jobpos总数据量
     */
    int selectCount(Jobpos jobpos);

    int selectRelationCount(Jobpos jobpos);

    /**
     * 根据条件查询Jobpos数据
     */
    List<Jobpos> selectData(@Param("jobpos") Jobpos jobpos, @Param("limit") int limit, @Param("offset") int offset,
                            @Param("order_by") String order_by);

    List<Map<String, Object>> selectRelationData(@Param("jobpos") Jobpos jobpos, @Param("limit") int limit,
                                                 @Param("offset") int offset, @Param("order_by") String order_by);

    /**
     * 根据条件查询Jobpos数据不分页
     */
    List<Jobpos> selectByParam(@Param("jobpos") Jobpos jobpos, @Param("order_by") String order_by);

    List<Map<Integer, String>> ajaxSelectJobposByDeptId(@Param("deptId") String deptId);
}