package com.pengyd.bean;

import java.sql.Date;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;


@Alias("AttdApproveInfo")
public class AttdApproveInfo {

    /** 主键 */
    private Integer id;

    /** 所属员工-提交审批人 */
    private Integer empId;

    /** 请假、调休或出差时长 */
    private Double approveDays;

    /** 请假事由 */
    private String approveContent;

    /** 审批备注 */
    private String approveRemark;

    /** 审批事件类型 1-请假申请 2-调休申请 3-出差申请 */
    private Integer approveType;

    /** 审批内的开始日期 */
    private Timestamp approveTimeStart;

    /** 审批内的结束日期 */
    private Timestamp approveTimeEnd;

    /** 审批提交时间 */
    private Date createTime;

    /** 审批状态 0-等待提交 1-开始审批 2-审批中 3-审批通过 4-审批驳回 */
    private Integer approveState;

    /** 修改时间 */
    private Date updateTime;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpId() {
        return this.empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Double getApproveDays() {
        return this.approveDays;
    }

    public void setApproveDays(Double approveDays) {
        this.approveDays = approveDays;
    }

    public String getApproveContent() {
        return this.approveContent;
    }

    public void setApproveContent(String approveContent) {
        this.approveContent = approveContent;
    }

    public String getApproveRemark() {
        return this.approveRemark;
    }

    public void setApproveRemark(String approveRemark) {
        this.approveRemark = approveRemark;
    }

    public Integer getApproveType() {
        return this.approveType;
    }

    public void setApproveType(Integer approveType) {
        this.approveType = approveType;
    }

    public Timestamp getApproveTimeStart() {
        return this.approveTimeStart;
    }

    public void setApproveTimeStart(Timestamp approveTimeStart) {
        this.approveTimeStart = approveTimeStart;
    }

    public Timestamp getApproveTimeEnd() {
        return this.approveTimeEnd;
    }

    public void setApproveTimeEnd(Timestamp approveTimeEnd) {
        this.approveTimeEnd = approveTimeEnd;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getApproveState() {
        return this.approveState;
    }

    public void setApproveState(Integer approveState) {
        this.approveState = approveState;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        String toString = "AttdApproveInfo [" + "id = " + id + "," + "emp_id = " + empId + "," + "approve_days = "
                + approveDays + "," + "approve_content = " + approveContent + "," + "approve_remark = " + approveRemark
                + "," + "approve_type = " + approveType + "," + "approve_time_start = " + approveTimeStart + ","
                + "approve_time_end = " + approveTimeEnd + "," + "create_time = " + createTime + ","
                + "approve_state = " + approveState + "," + "update_time = " + updateTime + "," + "]";
        return toString;
    }

}