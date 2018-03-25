package com.pengyd.bean;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

@Alias("Attendance")
public class Attendance {

    /** 主键 */
    private Integer id;
    /** 所属员工 */
    private Integer empId;
    /** 考勤状态 - 0-缺勤(旷工) - 1-正常 2-迟到 3-请假 4-调休 */
    private Integer attdState;
    /** 考核日期 */
    private Date createTime;


    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public Integer getEmpId(){
        return this.empId;
    }
    public void setEmpId(Integer empId){
        this.empId = empId;
    }

    public Integer getAttdState(){
        return this.attdState;
    }
    public void setAttdState(Integer attdState){
        this.attdState = attdState;
    }

    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
 	@Override
    public String toString() {
    String toString = "Attendance ["+
    					"id = "+id+","+
						"emp_id = "+empId+","+
						"attd_state = "+attdState+","+
						"create_time = "+createTime+","+
						"]";
        return toString;
	}

}