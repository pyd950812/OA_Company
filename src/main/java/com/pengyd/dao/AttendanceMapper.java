package com.pengyd.dao;

import java.util.List;
import java.util.Map;

import com.pengyd.bean.Attendance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author pengyd
 * @Date 2018/3/22 16:32
 * @function:
 */
@Repository
public interface AttendanceMapper {

    /**
     * attendance 执行插入 Attendance 操作
     */
    void insert(Attendance attendance);

    /**
     * attendance 执行删除 数据操作
     */
    void delete(Attendance attendance);

    /**
     * attendance 执行 批量删除 数据操作
     */
    void deleteBatch(@Param("ids") String[] ids);

    /**
     * attendance 执行修改 数据操作
     */
    void update(Attendance attendance);

    /**
     * 根据条件查询Attendance总数据量
     */
    int selectCount(Attendance attendance);

    /**
     * 根据条件查询Attendance数据
     */
    List<Attendance> selectData(@Param("attendance") Attendance attendance, @Param("limit") int limit,
                                @Param("offset") int offset, @Param("order_by") String order_by);

    /**
     * 根据条件查询Attendance总数据量 - 关联查询
     */
    int selectRelationCount(Attendance attendance);

    /**
     * 根据条件查询Attendance数据 - 关联查询
     */
    List<Map<String, Object>> selectRelationData(@Param("attendance") Attendance attendance, @Param("limit") int limit,
                                                 @Param("offset") int offset, @Param("order_by") String order_by);

    int selectRelationCountByEmpRealname(@Param("attendance") Attendance attendance,
                                         @Param("empRealname") String empRealname);

    List<Map<String, Object>> selectRelationDataByEmpRealname(@Param("attendance") Attendance attendance,
                                                              @Param("limit") int limit, @Param("offset") int offset, @Param("order_by") String order_by,
                                                              @Param("empRealname") String empRealname);

    /**
     * 根据条件查询Attendance数据不分页
     */
    List<Attendance> selectByParam(@Param("attendance") Attendance attendance, @Param("order_by") String order_by);

}