package com.pengyd.dao;

import com.pengyd.bean.Employee;
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

   void insert(EmployeeEvection employeeEvection);




}
