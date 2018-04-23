package com.pengyd.util;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


public class ApplicationInit implements ApplicationListener<ContextRefreshedEvent> {
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        try {
            SchedulerUtil.executeAttendanceDailyQuartz();
            System.out.println("考勤日常定时任务初始化成功");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //初始化预定义的请假审批任务

        //

    }
}