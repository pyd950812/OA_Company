package com.pengyd.service;


import com.pengyd.bean.Attendance;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;

/**
 * @Author pengyd
 * @Date 2018/3/22 17:00
 * @function:
 */
public interface AttendanceService {


    /**
     * 执行 Attendance 插入操作
     */
    ReturnData insert(Attendance attendance);

    /**
     * 执行 Attendance 删除操作
     */
    ReturnData delete(Attendance attendance);

    /**
     * 执行 Attendance 批量删除操作
     */
    ReturnData deleteBatch(String[] ids);

    /**
     * 执行 Attendance 修改操作
     */
    ReturnData update(Attendance attendance);

    /**
     * 执行 Attendance 分页查询操作
     */
    JqGridJsonBean select(String page, String rows, String order_by, Attendance attendance);

    /**
     * 执行 Attendance 分页查询操作 - 关联查询
     */
    JqGridJsonBean selectRelationData(String page, String rows, String order_by, Attendance attendance);

    /**
     * 执行 Attendance 查询不分页操作
     */
    ReturnData selectByParam(String order_by, Attendance attendance);

    JqGridJsonBean selectRelationDataByEmpRealname(String page, String rows, String order_by, Attendance attendance,
                                                   String empRealname, String createTimeStr);

    ReturnData selectByNowDateStr(Attendance attendance, String nowDateStr);

    ReturnData insertByTime(Attendance attendance);

    ReturnData selectEmpIdsByCreateTime(String createTime);
}