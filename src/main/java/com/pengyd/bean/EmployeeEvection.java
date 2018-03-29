package com.pengyd.bean;

import org.apache.ibatis.type.Alias;

import java.sql.Date;


/**
 * @Author pengyd
 * @Date 2018/3/22 16:18
 * @function:  员工出差信息
 */
@Alias("EmployeeEvection")
public class EmployeeEvection {

    /** 主键 */
    private Integer id;
    /** 员工id */
    private String empId;
    /** 员工姓名 */
    private String empName;
    /** 出差开始时间 */
    private Date evectionTimebegin;
    /** 出差结束时间 */
    private Date evectionTimeover;
    /** 出差理由 */
    private String evectionReason;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date updateTime;

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

    public Date getEvectionTimebegin() {
        return evectionTimebegin;
    }

    public void setEvectionTimebegin(Date evectionTimebegin) {
        this.evectionTimebegin = evectionTimebegin;
    }

    public Date getEvectionTimeover() {
        return evectionTimeover;
    }

    public void setEvectionTimeover(Date evectionTimeover) {
        this.evectionTimeover = evectionTimeover;
    }

    public String getEvectionReason() {
        return evectionReason;
    }

    public void setEvectionReason(String evectionReason) {
        this.evectionReason = evectionReason;
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

    @Override
    public String toString() {
        return "EmployeeEvection{" +
                "id=" + id +
                ", empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", evectionTimebegin=" + evectionTimebegin +
                ", evectionTimeover=" + evectionTimeover +
                ", evectionReason='" + evectionReason + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
