package com.pengyd.service;

import com.pengyd.bean.EmployeeEvection;
import com.pengyd.bean.EmployeeReward;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;

/**
 * @Author pengyd
 * @Date 2018/4/4 17:21
 * @function:
 */
public interface EmployeeRewardService {

    /**
     * 执行插入操作
     */
    ReturnData insert(EmployeeReward employeeReward);

    /**
     * admin 执行 EmployeeEvection 分页查询操作
     */
    JqGridJsonBean adminSelect(String page, String rows, String order_by, EmployeeReward employeeReward);

    /**
     * 执行修改操作
     */
    ReturnData update(EmployeeReward employeeReward);

    /**
     * 根据条件查询 employeeReward
     */
    ReturnData selectByParam(EmployeeReward employeeReward,String order_by);

    /**
     * 支持批量删除 EmployeeEvection的数据
     */
    ReturnData deleteBatch(String[] ids);



}
