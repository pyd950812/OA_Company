package com.pengyd.service;


import com.pengyd.bean.Department;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;

/**
 * @Author pengyd
 * @Date 2018/3/22 17:00
 * @function:
 */
public interface DepartmentService {

    /**
      * 执行 Department 插入操作
      */
    ReturnData insert(Department department);

    /**
      * 执行 Department 删除操作
      */
    ReturnData delete(Department department);

    /**
      * 执行 Department 批量删除操作
      */
    ReturnData deleteBatch(String[] ids);

    /**
      * 执行 Department 修改操作
      */
    ReturnData update(Department department);

    /**
      * 执行 Department 分页查询操作
      */
    JqGridJsonBean select(String page, String rows, String order_by, Department department);

    /**
      * 执行 Department 查询不分页操作
      */
    ReturnData selectByParam(String order_by, Department department);

    ReturnData ajaxSelectDept();
}