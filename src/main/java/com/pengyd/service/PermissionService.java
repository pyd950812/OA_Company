package com.pengyd.service;


import com.pengyd.bean.Permission;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;

/**
 * <p>服务层接口</p>
 * <p>Table: permission - </p>
 *
 * @since ${.now}
 */
public interface PermissionService {

    /**
      * 执行 Permission 插入操作
      */
    ReturnData insert(Permission permission);

    /**
      * 执行 Permission 删除操作
      */
    ReturnData delete(Permission permission);

    /**
      * 执行 Permission 批量删除操作
      */
    ReturnData deleteBatch(String[] ids);

    /**
      * 执行 Permission 修改操作
      */
    ReturnData update(Permission permission);

    /**
      * 执行 Permission 分页查询操作
      */
    JqGridJsonBean select(String page, String rows, String order_by, Permission permission);

    /**
      * 执行 Permission 分页查询操作 - 关联查询
      */
    JqGridJsonBean selectRelationData(String page, String rows, String order_by, Permission permission);

    /**
      * 执行 Permission 查询不分页操作
      */
    ReturnData selectByParam(String order_by, Permission permission);

    ReturnData ajaxSelectPermListByUse();

    ReturnData selectByPermIds(String permIds);

}