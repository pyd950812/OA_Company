package com.pengyd.dao;

import java.util.List;
import java.util.Map;

import com.pengyd.bean.Department;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author pengyd
 * @Date 2018/3/22 16:32
 * @function:
 */
@Repository
public interface DepartmentMapper {

    /**
     * department 执行插入 Department 操作
     */
    int insert(Department department);

    /**
     * department 执行删除 数据操作
     */
    void delete(Department department);

    /**
     * department 执行 批量删除 数据操作
     */
    void deleteBatch(@Param("ids") String[] ids);

    /**
     * department 执行修改 数据操作
     */
    void update(Department department);

    /**
     * 根据条件查询Department总数据量
     */
    int selectCount(Department department);

    /**
     * 根据条件查询Department数据
     */
    List<Department> selectData(@Param("department") Department department, @Param("limit") int limit,
                                @Param("offset") int offset, @Param("order_by") String order_by);

    /**
     * 根据条件查询Department数据不分页
     */
    List<Department> selectByParam(@Param("department") Department department, @Param("order_by") String order_by);

    List<Map<Integer, String>> ajaxSelectDept();
}