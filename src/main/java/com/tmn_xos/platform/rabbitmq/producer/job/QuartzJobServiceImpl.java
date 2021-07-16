package com.tmn_xos.platform.rabbitmq.producer.job;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuartzJobServiceImpl implements QuartzJobService {

    private static final Logger log = LoggerFactory.getLogger(QuartzJobServiceImpl.class);

    @Autowired
    private Scheduler scheduler;

    @Override
    public void addJob(String clazzName, String jobName, String groupName, String cronExp, Map<String, Object> param) {
        try {
            // 
//            scheduler.start();
            //job情報生成
            Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(clazzName);
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, groupName).build();
            //正規表現
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExp);
            //trigger生成
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, groupName).withSchedule(scheduleBuilder).build();
            //JobDataMapに設定値を書き込み
            if (param != null) {
                trigger.getJobDataMap().putAll(param);
            }
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            log.error("job生成失敗", e);
        }
    }

    @Override
    public void pauseJob(String jobName, String groupName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobName, groupName));
        } catch (SchedulerException e) {
            log.error("一時停止失敗", e);
        }
    }

    @Override
    public void resumeJob(String jobName, String groupName) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobName, groupName));
        } catch (SchedulerException e) {
            log.error("job復旧失敗", e);
        }
    }

    @Override
    public void runOnce(String jobName, String groupName) {
        try {
            scheduler.triggerJob(JobKey.jobKey(jobName, groupName));
        } catch (SchedulerException e) {
            log.error("Job一回起動失敗", e);
        }
    }

    @Override
    public void updateJob(String jobName, String groupName, String cronExp, Map<String, Object> param) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, groupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (cronExp != null) {
                // 
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExp);
                // 
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            }
            //
            if (param != null) {
                trigger.getJobDataMap().putAll(param);
            }
            // 
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (Exception e) {
            log.error("更新失敗", e);
        }
    }

    @Override
    public void deleteJob(String jobName, String groupName) {
        try {
            //
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, groupName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, groupName));
            scheduler.deleteJob(JobKey.jobKey(jobName, groupName));
        } catch (Exception e) {
            log.error("削除失敗", e);
        }
    }

    @Override
    public void startAllJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            log.error("全部Job起動失敗", e);
        }
    }

    @Override
    public void pauseAllJobs() {
        try {
            scheduler.pauseAll();
        } catch (Exception e) {
            log.error("全部Job一時停止失敗", e);
        }
    }

    @Override
    public void resumeAllJobs() {
        try {
            scheduler.resumeAll();
        } catch (Exception e) {
            log.error("全部Job復旧失敗", e);
        }
    }

    @Override
    public void shutdownAllJobs() {
        try {

            if (!scheduler.isShutdown()) {
                // 
                // 
                scheduler.shutdown(true);
            }
        } catch (Exception e) {
            log.error("全部Job停止失敗", e);
        }
    }
}