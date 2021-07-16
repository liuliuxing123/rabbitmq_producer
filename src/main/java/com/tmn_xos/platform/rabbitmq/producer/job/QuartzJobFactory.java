package com.tmn_xos.platform.rabbitmq.producer.job;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;
/**
 *  Quartz 
 * @author liuli
 *
 */
@Component
public class QuartzJobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        //
        Object jobInstance = super.createJobInstance(bundle);
        //
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}