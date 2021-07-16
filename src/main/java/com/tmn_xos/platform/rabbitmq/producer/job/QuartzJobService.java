package com.tmn_xos.platform.rabbitmq.producer.job;

import java.util.Map;

public interface QuartzJobService {
    /**
     * 
     * @param clazzName
     * @param jobName
     * @param groupName
     * @param cronExp
     * @param param
     */
    void addJob(String clazzName, String jobName, String groupName, String cronExp, Map<String, Object> param);

    /**
     * 
     * @param jobName
     * @param groupName
     */
    void pauseJob(String jobName, String groupName);

    /**
     * 
     * @param jobName
     * @param groupName
     */
    void resumeJob(String jobName, String groupName);

    /**
     * 
     * @param jobName
     * @param groupName
     */
    void runOnce(String jobName, String groupName);

    /**
     * 
     * @param jobName
     * @param groupName
     * @param cronExp
     * @param param
     */
    void updateJob(String jobName, String groupName, String cronExp, Map<String, Object> param);

    /**
     * 
     * @param jobName
     * @param groupName
     */
    void deleteJob(String jobName, String groupName);

    /**
     * 
     */
    void startAllJobs();

    /**
     * 
     */
    void pauseAllJobs();

    /**
     * 
     */
    void resumeAllJobs();

    /**
     * 
     */
    void shutdownAllJobs();
}