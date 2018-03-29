package com.pengyd.bean;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

/**
 * @Author pengyd
 * @Date 2018/3/22 16:18
 * @function:  员工信息
 */
@Alias("Employee")
public class Employee {

    /** 主键 */
    private Integer id;
    /** 员工编码 */
    private String empCode;
    /** 用户名 */
    private String loginname;
    /** 用户密码 */
    private String password;
    /** 真实姓名 */
    private String realname;
    /** 入职时间 */
    private Date entryTime;
    /** 所属职位 */
    private Integer jobposId;
    /** 注册时间 */
    private Date registerTime;


    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public String getEmpCode(){
        return this.empCode;
    }
    public void setEmpCode(String empCode){
        this.empCode = empCode;
    }

    public String getLoginname(){
        return this.loginname;
    }
    public void setLoginname(String loginname){
        this.loginname = loginname;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getRealname(){
        return this.realname;
    }
    public void setRealname(String realname){
        this.realname = realname;
    }

    public Date getEntryTime(){
        return this.entryTime;
    }
    public void setEntryTime(Date entryTime){
        this.entryTime = entryTime;
    }

    public Integer getJobposId(){
        return this.jobposId;
    }
    public void setJobposId(Integer jobposId){
        this.jobposId = jobposId;
    }

    public Date getRegisterTime(){
        return this.registerTime;
    }
    public void setRegisterTime(Date registerTime){
        this.registerTime = registerTime;
    }
 	@Override
    public String toString() {
    String toString = "Employee ["+
    					"id = "+id+","+
						"emp_code = "+empCode+","+
						"loginname = "+loginname+","+
						"password = "+password+","+
						"realname = "+realname+","+
						"entry_time = "+entryTime+","+
						"jobpos_id = "+jobposId+","+
						"register_time = "+registerTime+","+
						"]";
        return toString;
	}

}