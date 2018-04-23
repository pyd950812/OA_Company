package com.pengyd.util;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;


public class ApplicationEnd implements ApplicationListener<ContextClosedEvent> {
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        Scheduler scheduler = SchedulerUtil.getScheduler();

        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        }
        catch (SchedulerException e) {
            e.printStackTrace();
        }

        System.out.println("QUARTZ定时器关闭成功");
    }
}
