/**
 * 版权所有, 
 * Author: 郭 荣誉出品
 * E-mail:gwq20521@163.com
 * copyright: 2018
 */
package com.pengyd.service;


import com.pengyd.bean.Department;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;

/**
 * <p>服务层接口</p>
 * <p>Table: department - </p>
 *
 * @since ${.now}
 */
public interface DepartmentService {

    /**
      * 执行 Department 插入操作
      * @param department
      * @return
      */
    ReturnData insert(Department department);

    /**
      * 执行 Department 删除操作
      * @param department
      * @return
      */
    ReturnData delete(Department department);

    /**
      * 执行 Department 批量删除操作
      * @param department
      * @return
      */
    ReturnData deleteBatch(String[] ids);

    /**
      * 执行 Department 修改操作
      * @param department
      * @return
      */
    ReturnData update(Department department);

    /**
      * 执行 Department 分页查询操作
      * @param department
      * @return
      */
    JqGridJsonBean select(String page, String rows, String order_by, Department department);

    /**
      * 执行 Department 查询不分页操作
      * @param department
      * @param order_by
      * @return
      */
    ReturnData selectByParam(String order_by, Department department);

    ReturnData ajaxSelectDept();
}