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

        JobDetail jobDetail = JobBuilder.newJob(AttendanceDailyQuartzJob.class).withIdentity(jobName, jobGroupName)
                .build();

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronTriggerStr);

        String triggerName = "trigger" + id;
        String triggerGroupName = "triggerGroup" + id;

        CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
                .withSchedule(cronScheduleBuilder).build();

        if (!scheduler.isShutdown()) {
            scheduler.pauseTrigger(trigger.getKey());
            scheduler.unscheduleJob(trigger.getKey());
            scheduler.interrupt(jobDetail.getKey());
            scheduler.deleteJob(jobDetail.getKey());
        }

        scheduler.scheduleJob(jobDetail, trigger);

        if (!scheduler.isShutdown())
            scheduler.start();
    }
}