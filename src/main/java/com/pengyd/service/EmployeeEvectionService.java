package com.pengyd.service;

import com.pengyd.bean.EmployeeEvection;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;

/**
 * @Author pengyd
 * @Date 2018/3/22 17:00
 * @function:
 */
public interface EmployeeEvectionService {

    /**
     * 执行插入操作
     */
    ReturnData insert(EmployeeEvection employeeEvection);

    /**
     * admin 执行 EmployeeEvection 分页查询操作
     */
    JqGridJsonBean adminSelect(String page, String rows, String order_by, EmployeeEvection employeeEvection);




}
