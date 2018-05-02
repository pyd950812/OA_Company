package com.pengyd.util;

import com.pengyd.util.job.AttendanceDailyQuartzJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;


/**
 * @Author pengyd
 * @Date 2018/3/22 17:08
 * @function:  定时器 quartz
 */
public class SchedulerUtil {
    private static Scheduler scheduler;

    private static StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();

    static {
        try {
            scheduler = getStdSchedulerFactory().getScheduler();
        }
        catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public static StdSchedulerFactory getStdSchedulerFactory() {
        return stdSchedulerFactory;
    }

    public static Scheduler getScheduler() {
        return scheduler;
    }


    public static void executeAttendanceDailyQuartz() throws Exception {
        String id = "attendanceDailyId";
        String cronTriggerStr = PropertyUtil.getValue("attendanceDailyTriggerStr", "application.properties");

        String jobName = "job" + id;
        String jobGroupName = "jobGroup" + id;
        //新建一个job，执行内容是AttendanceDailyQuartzJob
        JobDetail jobDetail = JobBuilder.newJob(AttendanceDailyQuartzJob.class).withIdentity(jobName, jobGroupName)
                .build();
        //设置定时器的时间
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronTriggerStr);

        String triggerName = "trigger" + id;
        String triggerGroupName = "triggerGroup" + id;

        //quartz有两种Trigger（SimpleTrigger/CronTrigger） 我这里用的是CronTrigger，简单的任务采用的SimpleTrigger
        CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
                .withSchedule(cronScheduleBuilder).build();

        if (!scheduler.isShutdown()) {
            scheduler.pauseTrigger(trigger.getKey());
            scheduler.unscheduleJob(trigger.getKey());
            scheduler.interrupt(jobDetail.getKey());
            scheduler.deleteJob(jobDetail.getKey());
        }
        //启动定时器
        scheduler.scheduleJob(jobDetail, trigger);

        if (!scheduler.isShutdown())
            scheduler.start();
    }
}