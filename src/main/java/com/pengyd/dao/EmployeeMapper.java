package com.pengyd.dao;

import java.util.List;
import java.util.Map;

import com.pengyd.bean.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author pengyd
 * @Date 2018/3/22 16:32
 * @function:
 */
@Repository
public interface EmployeeMapper {

    /**
     * employee 执行插入 Employee 操作
     */
    void insert(Employee employee);

    /**
     * employee 执行删除 数据操作
     */
    void delete(Employee employee);

    /**
     * employee 执行 批量删除 数据操作
     */
    void deleteBatch(@Param("ids") String[] ids);

    /**
     * employee 执行修改 数据操作
     */
    void update(Employee employee);

    /**
     * 根据条件查询Employee总数据量
     */
    int selectCount(Employee employee);

    /**
     *  主要区分拥有的模糊查询
     */
    int selectRelationCount(Employee employee);

    /**
     * 根据条件查询Employee数据
     */
    List<Employee> selectData(@Param("employee") Employee employee, @Param("limit") int limit,
                              @Param("offset") int offset, @Param("order_by") String order_by);

    List<Map<String, Object>> selectRelationData(@Param("employee") Employee employee, @Param("limit") int limit,
                                                 @Param("offset") int offset, @Param("order_by") String order_by);
    /**
     * 根据条件查询Employee数据不分页
     */
    List<Employee> selectByParam(@Param("employee") Employee employee, @Param("order_by") String order_by);

    String ajaxSelectMaxEmpCode();

    List<Map<Integer, String>> ajaxSelectEmpByJobposId(@Param("jobposId") String jobposId);

    String selectRealnameById(@Param("id") Integer id);

    /**
     *  根据empId查询员工是否存在
     */
    Employee selectByEmpId(@Param("empId") String empId);

    /**
     *  根据loginName找到对应的员工
     */
    Employee selectByLoginName(@Param("loginName") String loginName);
}