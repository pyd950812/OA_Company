package com.pengyd.bean;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

@Alias("EmpFriendInfo")
public class EmpFriendInfo {

    /** 主键 */
    private Integer id;
    /** 关联交互身份信息主键 */
    private Integer empFriId;
    /** 聊天记录的发送者 */
    private Integer empId;
    /** 聊天内容 - 不能为空 */
    private String chatInfo;
    /** 聊天记录生成时间 */
    private Date createTime;


    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public Integer getEmpFriId(){
        return this.empFriId;
    }
    public void setEmpFriId(Integer empFriId){
        this.empFriId = empFriId;
    }

    public Integer getEmpId(){
        return this.empId;
    }
    public void setEmpId(Integer empId){
        this.empId = empId;
    }

    public String getChatInfo(){
        return this.chatInfo;
    }
    public void setChatInfo(String chatInfo){
        this.chatInfo = chatInfo;
    }

    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
 	@Override
    public String toString() {
    String toString = "EmpFriendInfo ["+
    					"id = "+id+","+
						"emp_fri_id = "+empFriId+","+
						"emp_id = "+empId+","+
						"chat_info = "+chatInfo+","+
						"create_time = "+createTime+","+
						"]";
        return toString;
	}

}