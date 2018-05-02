package com.pengyd.util.job;

import java.util.List;

import com.pengyd.bean.Attendance;
import com.pengyd.service.AttendanceService;
import com.pengyd.service.EmployeeService;
import com.pengyd.util.CommonUtils;
import com.pengyd.util.ReturnData;
import com.pengyd.util.SpringContextUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author pengyd
 * @Date 2018/3/22 17:08
 * @function:  定时任务：每天23:50分执行  查看今天打卡情况，未打卡的考勤设置为缺勤
 */
public class AttendanceDailyQuartzJob implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //String id = context.getJobDetail().getJobDataMap().getString("id");

        EmployeeService employeeService = (EmployeeService) SpringContextUtil.getBean(EmployeeService.class);
        AttendanceService attendanceService = (AttendanceService) SpringContextUtil.getBean(AttendanceService.class);

        ReturnData rd = new ReturnData();

        //获取到所有用户员工的ID
        ReturnData rdAllTemp = employeeService.selectEmpIds();
        List<Integer> empIdsAllList = (List<Integer>) rdAllTemp.getData().get("data");
        //获取到当前时间 时间格式为  yyyy-MM-dd
        String createTime = CommonUtils.getNowDateStrByDate();
        //获取到已打卡的ID
        ReturnData rdTemp = attendanceService.selectEmpIdsByCreateTime(createTime);
        List<Integer> empIdsDutyList = (List<Integer>) rdTemp.getData().get("data");

        for (int i = 0; i < empIdsAllList.size(); i++) {
            Integer empId = empIdsAllList.get(i);
            if (!empIdsDutyList.contains(empId)) {//今天未打卡
                //如果今天未打卡，就录入今天为旷工
                Attendance attendance = new Attendance();

                attendance.setEmpId(empId);
                attendance.setAttdState(1);
//                attendance.setCreateTime(CommonUtils.strToTimestamp(createTime + " 23:50:00.000"));

                attendanceService.insertByTime(attendance);
            }
        }

        rd.setCode("OK");
        rd.setMsg("请求成功");
        System.out.println("在考勤日常定时任务中完成了一次操作---" + rd.getCode());
    }
}
