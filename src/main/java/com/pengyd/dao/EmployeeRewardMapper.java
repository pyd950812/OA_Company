package com.pengyd.dao;


import com.pengyd.bean.EmployeeEvection;
import com.pengyd.bean.EmployeeReward;
import org.apache.ecs.wml.Em;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author pengyd
 * @Date 2018/4/4 16:54
 * @function:
 */
@Repository
public interface EmployeeRewardMapper {

    /**
     *  employeeReward 插入EmployeeReward
     */
    void insert(EmployeeReward employeeReward);

    /**
     * employeeReward 根据条件查询EmployeeReward总数据量
     */
    int selectCount(EmployeeReward employeeReward);

    /**
     * employeeReward 根据条件查询EmployeeReward数据 分页
     */
    List<EmployeeReward> selectData(@Param("employeeReward") EmployeeReward employeeReward, @Param("limit") int limit,
                                    @Param("offset") int offset, @Param("order_by") String order_by);

    /**
     *  employeeReward 对EmployeeReward数据进行修改
     */
    void update(EmployeeReward employeeReward);

    /**
     * employeeReward 根据条件查询employeeReward数据
     */
    List<EmployeeReward> seleteByParam(@Param("employeeReward") EmployeeReward employeeReward,@Param("order_by") String order_by);

    /**
     * employeeReward 支持批量删除 employeeReward的数据
     */
    void deleteBatch(@Param("ids") String[] ids);






}
