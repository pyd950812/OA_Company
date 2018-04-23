package com.pengyd.bean;

import java.sql.Date;

import org.apache.ibatis.type.Alias;


@Alias("JobsManage")
public class JobsManage {

    /** 主键 */
    private Integer id;
    /** 创建领导 */
    private Integer createUserId;
    /** 指定分配员工 */
    private Integer workUserId;
    /** 工作描述 */
    private String jobInfo;
    /** 工作状态 0-新建 1-进行中 2-已解决 3-已关闭 4-已驳回 */
    private Integer jobState;
    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date updateTime;
    /** 工作具体小结 */
    private String jobWorkInfo;


    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public Integer getCreateUserId(){
        return this.createUserId;
    }
    public void setCreateUserId(Integer createUserId){
        this.createUserId = createUserId;
    }

    public Integer getWorkUserId(){
        return this.workUserId;
    }
    public void setWorkUserId(Integer workUserId){
        this.workUserId = workUserId;
    }

    public String getJobInfo(){
        return this.jobInfo;
    }
    public void setJobInfo(String jobInfo){
        this.jobInfo = jobInfo;
    }

    public Integer getJobState(){
        return this.jobState;
    }
    public void setJobState(Integer jobState){
        this.jobState = jobState;
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

    public String getJobWorkInfo(){
        return this.jobWorkInfo;
    }
    public void setJobWorkInfo(String jobWorkInfo){
        this.jobWorkInfo = jobWorkInfo;
    }
 	@Override
    public String toString() {
    String toString = "JobsManage ["+
    					"id = "+id+","+
						"create_user_id = "+createUserId+","+
						"work_user_id = "+workUserId+","+
						"job_info = "+jobInfo+","+
						"job_state = "+jobState+","+
						"create_time = "+createTime+","+
						"update_time = "+updateTime+","+
						"job_work_info = "+jobWorkInfo+","+
						"]";
        return toString;
	}

}