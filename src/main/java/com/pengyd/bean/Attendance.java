package com.pengyd.bean;


import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;
/**
 * @Author pengyd
 * @Date 2018/3/22 16:18
 * @function:  考勤信息
 */
@Alias("Attendance")
public class Attendance {

    /** 主键 */
    private Integer id;

    /** 所属员工 */
    private Integer empId;

    /** 考勤状态 - 1 缺勤(全天旷工) - 2 半天旷工 - 3 正常上班 - 4 正常下班 - 5 迟到 - 6 早退 - 7 请假 - 8 调休 - 9 出差 */
    private Integer attdState;

    /** 考核日期 */
    private Timestamp createTime;

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

    public Integer getAttdState() {
        return this.attdState;
    }

    public void setAttdState(Integer attdState) {
        this.attdState = attdState;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        String toString = "Attendance [" + "id = " + id + "," + "emp_id = " + empId + "," + "attd_state = " + attdState
                + "," + "create_time = " + createTime + "," + "]";
        return toString;
    }

}