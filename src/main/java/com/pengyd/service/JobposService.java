package com.pengyd.service;


import com.pengyd.bean.Jobpos;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;

/**
 * <p>服务层接口</p>
 * <p>Table: jobpos - </p>
 *
 * @since ${.now}
 */
public interface JobposService {

    /**
      * 执行 Jobpos 插入操作
      * @param jobpos
      * @return
      */
    ReturnData insert(Jobpos jobpos);

    /**
      * 执行 Jobpos 删除操作
      * @param jobpos
      * @return
      */
    ReturnData delete(Jobpos jobpos);

    /**
      * 执行 Jobpos 批量删除操作
      * @param jobpos
      * @return
      */
    ReturnData deleteBatch(String[] ids);

    /**
      * 执行 Jobpos 修改操作
      * @param jobpos
      * @return
      */
    ReturnData update(Jobpos jobpos);

    /**
      * 执行 Jobpos 分页查询操作
      * @param jobpos
      * @return
      */
    JqGridJsonBean select(String page, String rows, String order_by, Jobpos jobpos);

    /**
      * 执行 Jobpos 查询不分页操作
      * @param jobpos
      * @param order_by
      * @return
      */
    ReturnData selectByParam(String order_by, Jobpos jobpos);

    ReturnData ajaxSelectJobposByDeptId(String deptId);

    JqGridJsonBean selectRelationData(String page, String rows, String order_by, Jobpos jobpos);
}