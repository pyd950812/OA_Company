/**
 * 版权所有, 
 * Author: 郭 荣誉出品
 * E-mail:gwq20521@163.com
 * copyright: 2018
 */
package com.pengyd.dao;

import java.util.List;
import java.util.Map;

import com.pengyd.bean.Department;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>数据层接口</p>
 * <p>Table: department - </p>
 * @since ${.now}
 */
@Repository
public interface DepartmentMapper {

    /**
     * department 执行插入 Department 操作
     * @param department
     */
    void insert(Department department);

    /**
     * department 执行删除 数据操作
     * @param department
     */
    void delete(Department department);

    /**
     * department 执行 批量删除 数据操作
     * @param department
     */
    void deleteBatch(@Param("ids") String[] ids);

    /**
     * department 执行修改 数据操作
     * @param department
     */
    void update(Department department);

    /**
     * 根据条件查询Department总数据量
     * @param department
     */
    int selectCount(Department department);

    /**
     * 根据条件查询Department数据
     * @param department
     */
    List<Department> selectData(@Param("department") Department department, @Param("limit") int limit,
                                @Param("offset") int offset, @Param("order_by") String order_by);

    /**
     * 根据条件查询Department数据不分页
     * @param department
     */
    List<Department> selectByParam(@Param("department") Department department, @Param("order_by") String order_by);

    List<Map<Integer, String>> ajaxSelectDept();
}