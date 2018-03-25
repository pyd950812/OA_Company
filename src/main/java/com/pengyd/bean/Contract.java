package com.pengyd.bean;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

@Alias("Contract")
public class Contract {

    /** 主键 */
    private Integer id;
    /** 所属员工 */
    private Integer empId;
    /** 合同存储的路径 */
    private String contractUrl;
    /** 合同文件名称 */
    private String contractName;
    /** 创建用户 */
    private Integer createUserId;
    /** 创建时间 */
    private Date createTime;
    /** 修改用户的ID */
    private Integer modifyUserId;
    /** 修改日期 */
    private Date modifyTime;


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

    public String getContractUrl(){
        return this.contractUrl;
    }
    public void setContractUrl(String contractUrl){
        this.contractUrl = contractUrl;
    }

    public String getContractName(){
        return this.contractName;
    }
    public void setContractName(String contractName){
        this.contractName = contractName;
    }

    public Integer getCreateUserId(){
        return this.createUserId;
    }
    public void setCreateUserId(Integer createUserId){
        this.createUserId = createUserId;
    }

    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public Integer getModifyUserId(){
        return this.modifyUserId;
    }
    public void setModifyUserId(Integer modifyUserId){
        this.modifyUserId = modifyUserId;
    }

    public Date getModifyTime(){
        return this.modifyTime;
    }
    public void setModifyTime(Date modifyTime){
        this.modifyTime = modifyTime;
    }
 	@Override
    public String toString() {
    String toString = "Contract ["+
    					"id = "+id+","+
						"emp_id = "+empId+","+
						"contract_url = "+contractUrl+","+
						"contract_name = "+contractName+","+
						"create_user_id = "+createUserId+","+
						"create_time = "+createTime+","+
						"modify_user_id = "+modifyUserId+","+
						"modify_time = "+modifyTime+","+
						"]";
        return toString;
	}

}