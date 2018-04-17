package com.pengyd.bean;

import org.apache.ibatis.type.Alias;

import java.sql.Date;


/**
 * @Author pengyd
 * @Date 2018/3/22 16:18
 * @function:  员工奖罚信息
 */
@Alias("EmployeeReward")
public class EmployeeReward {

    /** 主键 */
    private Integer id;
    /** 员工id */
    private String empId;
    /** 员工姓名 */
    private String empName;
    /** 奖or罚 0-罚 1-奖 */
    private String rewardState;
    /** 奖罚日期 */
    private Date rewardTime;
    /** 奖罚金额 */
    private String rewardMoney;
    /** 奖罚理由 */
    private String rewardReason;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date updateTime;

    @Override
    public String toString() {
        return "EmployeeReward{" +
                "id=" + id +
                ", empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", rewardState='" + rewardState + '\'' +
                ", rewardTime=" + rewardTime +
                ", rewardMoney='" + rewardMoney + '\'' +
                ", rewardReason='" + rewardReason + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getRewardState() {
        return rewardState;
    }

    public void setRewardState(String rewardState) {
        this.rewardState = rewardState;
    }

    public Date getRewardTime() {
        return rewardTime;
    }

    public void setRewardTime(Date rewardTime) {
        this.rewardTime = rewardTime;
    }

    public String getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(String rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public String getRewardReason() {
        return rewardReason;
    }

    public void setRewardReason(String rewardReason) {
        this.rewardReason = rewardReason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
