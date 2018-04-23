package com.pengyd.bean;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

@Alias("EmpFriend")
public class EmpFriend {

    /** 主键 */
    private Integer id;
    /** 用户关系表述1 */
    private Integer empId1;
    /** 用户关系表述2 */
    private Integer empId2;
    /** 关系状态 0-正常 1-屏蔽 */
    private Integer friState;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date updateTime;


    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public Integer getEmpId1(){
        return this.empId1;
    }
    public void setEmpId1(Integer empId1){
        this.empId1 = empId1;
    }

    public Integer getEmpId2(){
        return this.empId2;
    }
    public void setEmpId2(Integer empId2){
        this.empId2 = empId2;
    }

    public Integer getFriState(){
        return this.friState;
    }
    public void setFriState(Integer friState){
        this.friState = friState;
    }

    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public Date getUpdateTime(){
        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
 	@Override
    public String toString() {
    String toString = "EmpFriend ["+
    					"id = "+id+","+
						"emp_id1 = "+empId1+","+
						"emp_id2 = "+empId2+","+
						"fri_state = "+friState+","+
						"create_time = "+createTime+","+
						"update_time = "+updateTime+","+
						"]";
        return toString;
	}

}