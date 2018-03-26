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
    *  插入
    */
   void insert(EmployeeEvection employeeEvection);

   /**
    * 根据条件查询Employee总数据量
    */
   int selectCount(EmployeeEvection employeeEvection);

   /**
    * 根据条件查询Employee数据
    */
   List<EmployeeEvection> selectData(@Param("employeeEvection") EmployeeEvection employeeEvection, @Param("limit") int limit,
                             @Param("offset") int offset, @Param("order_by") String order_by);






}
