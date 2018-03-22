/**
 * 版权所有, 
 * Author: 郭 荣誉出品
 * E-mail:gwq20521@163.com
 * copyright: 2018
 */
package com.pengyd.dao;

import java.util.List;

import com.pengyd.bean.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * <p>数据层接口</p>
 * <p>Table: employee - </p>
 *
 * @since ${.now}
 */
@Repository
public interface EmployeeMapper {

    /**
     * employee 执行插入 Employee 操作
     * @param employee
     */
    void insert(Employee employee);

    /**
     * employee 执行删除 数据操作
     * @param employee
     */
    void delete(Employee employee);

    /**
     * employee 执行 批量删除 数据操作
     * @param employee
     */
    void deleteBatch(@Param("ids") String[] ids);

    /**
     * employee 执行修改 数据操作
     * @param employee
     */
    void update(Employee employee);

    /**
     * 根据条件查询Employee总数据量
     * @param employee
     */
    int selectCount(Employee employee);

    /**
     * 根据条件查询Employee数据
     * @param employee
     */
    List<Employee> selectData(@Param("employee") Employee employee, @Param("limit") int limit,
                              @Param("offset") int offset, @Param("order_by") String order_by);

    /**
     * 根据条件查询Employee数据不分页
     * @param employee
     */
    List<Employee> selectByParam(@Param("employee") Employee employee, @Param("order_by") String order_by);

    String ajaxSelectMaxEmpCode();
}