package com.pengyd.service;


import com.pengyd.bean.Employee;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;

/**
 * <p>服务层接口</p>
 * <p>Table: employee - </p>
 *
 * @since ${.now}
 */
public interface EmployeeService {

    /**
      * 执行 Employee 插入操作
      * @param employee
      * @return
      */
    ReturnData insert(Employee employee);

    /**
      * 执行 Employee 删除操作
      * @param employee
      * @return
      */
    ReturnData delete(Employee employee);

    /**
      * 执行 Employee 批量删除操作
      * @param employee
      * @return
      */
    ReturnData deleteBatch(String[] ids);

    /**
      * 执行 Employee 修改操作
      * @param employee
      * @return
      */
    ReturnData update(Employee employee);

    /**
      * 执行 Employee 分页查询操作
      * @param employee
      * @return
      */
    JqGridJsonBean select(String page, String rows, String order_by, Employee employee);

    /**
      * 执行 Employee 查询不分页操作
      * @param employee
      * @param order_by
      * @return
      */
    ReturnData selectByParam(String order_by, Employee employee);

    ReturnData ajaxSelectMaxEmpCode();

    JqGridJsonBean selectRelationData(String page, String rows, String order_by, Employee employee);

    ReturnData ajaxSelectEmpByJobposId(String jobposId);

    String selectRealnameById(Integer id);
}