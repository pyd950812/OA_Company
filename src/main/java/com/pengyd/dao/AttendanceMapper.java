package com.pengyd.dao;

import java.util.List;
import java.util.Map;

import com.pengyd.bean.Attendance;
import org.apache.ibatis.annotations.Param;



public interface AttendanceMapper {

    /**
     * attendance 执行插入 Attendance 操作
     * @param attendance
     */
    void insert(Attendance attendance);

    /**
     * attendance 执行删除 数据操作
     * @param attendance
     */
    void delete(Attendance attendance);

    /**
     * attendance 执行 批量删除 数据操作
     * @param attendance
     */
    void deleteBatch(@Param("ids") String[] ids);

    /**
     * attendance 执行修改 数据操作
     * @param attendance
     */
    void update(Attendance attendance);

    /**
     * 根据条件查询Attendance总数据量
     * @param attendance
     */
    int selectCount(Attendance attendance);

    /**
     * 根据条件查询Attendance数据
     * @param attendance
     */
    List<Attendance> selectData(@Param("attendance") Attendance attendance, @Param("limit") int limit,
                                @Param("offset") int offset, @Param("order_by") String order_by);

    /**
     * 根据条件查询Attendance总数据量 - 关联查询
     * @param attendance
     */
    int selectRelationCount(Attendance attendance);

    /**
     * 根据条件查询Attendance数据 - 关联查询
     * @param attendance
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
     * @param attendance
     */
    List<Attendance> selectByParam(@Param("attendance") Attendance attendance, @Param("order_by") String order_by);

}