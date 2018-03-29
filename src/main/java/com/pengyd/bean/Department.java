package com.pengyd.bean;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

/**
 * @Author pengyd
 * @Date 2018/3/22 16:18
 * @function:  部门信息
 */
@Alias("Department")
public class Department {

    /** 主键 */
    private Integer id;
    /** 部门编码 */
    private String deptCode;
    /** 部门名称 */
    private String deptname;
    /** 部门信息 */
    private String deptinfo;
    /** 创建时间 */
    private Date createTime;
    /** 是否能直接分配所属工作-1-分配，2-不分配 */
    private Integer isDis;


    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public String getDeptCode(){
        return this.deptCode;
    }
    public void setDeptCode(String deptCode){
        this.deptCode = deptCode;
    }

    public String getDeptname(){
        return this.deptname;
    }
    public void setDeptname(String deptname){
        this.deptname = deptname;
    }

    public String getDeptinfo(){
        return this.deptinfo;
    }
    public void setDeptinfo(String deptinfo){
        this.deptinfo = deptinfo;
    }

    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public Integer getIsDis() {
        return isDis;
    }

    public void setIsDis(Integer isDis) {
        this.isDis = isDis;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", deptCode='" + deptCode + '\'' +
                ", deptname='" + deptname + '\'' +
                ", deptinfo='" + deptinfo + '\'' +
                ", createTime=" + createTime +
                ", isDis=" + isDis +
                '}';
    }
}