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

    public EmployeeEvection setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getEmpId() {
        return empId;
    }

    public EmployeeEvection setEmpId(String empId) {
        this.empId = empId;
        return this;
    }

    public String getEmpName() {
        return empName;
    }

    public EmployeeEvection setEmpName(String empName) {
        this.empName = empName;
        return this;
    }

    public Date getEvectionTimebegin() {
        return evectionTimebegin;
    }

    public EmployeeEvection setEvectionTimebegin(Date evectionTimebegin) {
        this.evectionTimebegin = evectionTimebegin;
        return this;
    }

    public Date getEvectionTimeover() {
        return evectionTimeover;
    }

    public EmployeeEvection setEvectionTimeover(Date evectionTimeover) {
        this.evectionTimeover = evectionTimeover;
        return this;
    }

    public String getEvectionReason() {
        return evectionReason;
    }

    public EmployeeEvection setEvectionReason(String evectionReason) {
        this.evectionReason = evectionReason;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public EmployeeEvection setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public EmployeeEvection setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
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
