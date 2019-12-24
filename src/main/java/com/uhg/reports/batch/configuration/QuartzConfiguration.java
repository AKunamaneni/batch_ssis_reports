package com.uhg.reports.batch.configuration;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.uhg.reports.batch.properties.ReportsBatchProperties;
import com.uhg.reports.batch.properties.QuartzProperties;
import com.uhg.reports.batch.schedule.InTransitJobScheduler;

@Configuration
public class QuartzConfiguration {

    private final ReportsBatchProperties reportsBatchProperties;
    private final QuartzProperties quartzProperties;
    
    public QuartzConfiguration(ReportsBatchProperties reportsBatchProperties, QuartzProperties quartzProperties) {
        this.reportsBatchProperties = reportsBatchProperties;
        this.quartzProperties = quartzProperties;
    }
    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, JobFactory jobFactory,
            List<Trigger> listOfTrigger) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setAutoStartup(true);
        factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties.getProperties());
        factory.setTriggers(listOfTrigger.toArray(new Trigger[]{}));
        return factory;
    }
    
    @Bean
    public SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay((long)(1000*60*Optional.ofNullable(reportsBatchProperties.getStartDelay()).orElse(0D)));
        factoryBean.setRepeatInterval((long)(1000*60*Optional.ofNullable(reportsBatchProperties.getRepeatInterval()).orElse(1.0)));
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
        return factoryBean;
    }
    
    @Bean
    public static JobDetailFactoryBean inTransitQuartzJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(InTransitJobScheduler.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }
}

