package com.uhg.reports.batch.properties;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class QuartzProperties {
    
    @Value("${quartz.scheduler.instanceId}")
    private String instanceId;
    @Value("${quartz.scheduler.makeSchedulerThreadDaemon}")
    private String makeSchedulerThreadDaemon;
    @Value("${quartz.threadPool.class}")
    private String threadPoolClz;
    @Value("${quartz.threadPool.makeThreadsDaemons}")
    private String makeThreadsDaemons;
    @Value("${quartz.threadPool.threadCount}")
    private String threadCount;
    @Value("${quartz.threadPool.threadPriority}")
    private String threadPriority;
    @Value("${quartz.jobStore.class}")
    private String jobStoreClz;
    @Value("${quartz.jobStore.driverDelegateClass}")
    private String driverDelegateClz;
    @Value("${quartz.jobStore.tablePrefix}")
    private String tablePrefix;
    @Value("${quartz.jobStore.isClustered}")
    private String isClustered;
    @Value("${quartz.jobStore.misfireThreshold}")
    private String misfireThreshold;
    
    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put("org.quartz.scheduler.instanceId", instanceId);
        properties.put("org.quartz.scheduler.makeSchedulerThreadDaemon", makeSchedulerThreadDaemon);
        properties.put("org.quartz.threadPool.class", threadPoolClz);
        properties.put("org.quartz.threadPool.makeThreadsDaemons", makeThreadsDaemons);
        properties.put("org.quartz.threadPool.threadCount", threadCount);
        properties.put("org.quartz.threadPool.threadPriority", threadPriority);
        properties.put("org.quartz.jobStore.class", jobStoreClz);
        properties.put("org.quartz.jobStore.driverDelegateClass", driverDelegateClz);
        properties.put("org.quartz.jobStore.tablePrefix", tablePrefix);
        properties.put("org.quartz.jobStore.isClustered", isClustered);
        properties.put("org.quartz.jobStore.misfireThreshold", misfireThreshold);
        return properties;
    }
    
}
