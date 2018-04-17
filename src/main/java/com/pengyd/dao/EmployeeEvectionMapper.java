package com.pengyd.dao;



import com.pengyd.bean.EmployeeEvection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author pengyd
 * @Date 2018/3/22 16:32
 * @function:
 */
@Repository
public interface EmployeeEvectionMapper {

   /**
    *  employeeEvection 插入EmployeeEvection
    */
   void insert(EmployeeEvection employeeEvection);

   /**
    * employeeEvection 根据条件查询EmployeeEvection总数据量
    */
   int selectCount(EmployeeEvection employeeEvection);

   /**
    * employeeEvection 根据条件查询EmployeeEvection数据 分页
    */
   List<EmployeeEvection> selectData(@Param("employeeEvection") EmployeeEvection employeeEvection, @Param("limit") int limit,
                                     @Param("offset") int offset, @Param("order_by") String order_by);

   /**
    * employeeEvection 根据条件查询EmployeeEvection数据 不分页
    */
   List<EmployeeEvection> selectByParam(@Param("employeeEvection") EmployeeEvection employeeEvection,@Param("order_by") String order_by);

   /**
    *  employeeEvection 修改EmployeeEvection的数据
    */
   void update(EmployeeEvection employeeEvection);

   /**
    * employeeEvection 支持批量删除 EmployeeEvection的数据
    */
   void deleteBatch(@Param("ids") String[] ids);

   /**
    * employeeEvection 根据员工的真实姓名查询所有的出差记录
    */
   List<EmployeeEvection> selectByRealName(String realName);










}
