package com.pengyd.bean;

import java.sql.Date;

import org.apache.ibatis.type.Alias;


@Alias("AttdApproveList")
public class AttdApproveList {

    /** 主键 */
    private Integer id;
    /** 批注用户 */
    private Integer empId;
    /** 批注内容 */
    private String annotation;
    /** 批注日期 */
    private Date createTime;
    /** 关联的审批申请表的ID */
    private Integer aAInfoId;


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

    public String getAnnotation(){
        return this.annotation;
    }
    public void setAnnotation(String annotation){
        this.annotation = annotation;
    }

    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public Integer getaAInfoId(){
        return this.aAInfoId;
    }
    public void setaAInfoId(Integer aAInfoId){
        this.aAInfoId = aAInfoId;
    }
 	@Override
    public String toString() {
    String toString = "AttdApproveList ["+
    					"id = "+id+","+
						"emp_id = "+empId+","+
						"annotation = "+annotation+","+
						"create_time = "+createTime+","+
						"a_a_info_id = "+aAInfoId+","+
						"]";
        return toString;
	}

}