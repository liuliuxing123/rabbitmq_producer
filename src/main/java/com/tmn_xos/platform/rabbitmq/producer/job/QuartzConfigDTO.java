package com.tmn_xos.platform.rabbitmq.producer.job;

import java.io.Serializable;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

public class QuartzConfigDTO implements Serializable {


    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    private String jobName;

    /**
     * 
     */
    private String groupName;

    /**
     * 
     */
    private String jobClass;

    /**
     * 
     */
    private String cronExpression;

    /**
     * 
     */
    private Map<String, Object> param;


    public String getJobName() {
        return jobName;
    }

    public QuartzConfigDTO setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public QuartzConfigDTO setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getJobClass() {
        return jobClass;
    }

    public QuartzConfigDTO setJobClass(String jobClass) {
        this.jobClass = jobClass;
        return this;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public QuartzConfigDTO setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
        return this;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public QuartzConfigDTO setParam(Map<String, Object> param) {
        this.param = param;
        return this;
    }
}
