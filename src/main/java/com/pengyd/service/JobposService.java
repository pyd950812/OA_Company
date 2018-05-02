package com.pengyd.service;


import com.pengyd.bean.Jobpos;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;

/**
 * @Author pengyd
 * @Date 2018/3/22 17:00
 * @function:
 */
public interface JobposService {

    /**
      * 执行 Jobpos 插入操作
      */
    ReturnData insert(Jobpos jobpos);

    /**
      * 执行 Jobpos 删除操作
      */
    ReturnData delete(Jobpos jobpos);

    /**
      * 执行 Jobpos 批量删除操作
      */
    ReturnData deleteBatch(String[] ids);

    /**
      * 执行 Jobpos 修改操作
      */
    ReturnData update(Jobpos jobpos);

    /**
      * 执行 Jobpos 分页查询操作
      */
    JqGridJsonBean select(String page, String rows, String order_by, Jobpos jobpos);

    /**
      * 执行 Jobpos 查询不分页操作
      */
    ReturnData selectByParam(String order_by, Jobpos jobpos);

    ReturnData ajaxSelectJobposByDeptId(String deptId);

    JqGridJsonBean selectRelationData(String page, String rows, String order_by, Jobpos jobpos);

    ReturnData selectIdListBySubId(String subId);
}