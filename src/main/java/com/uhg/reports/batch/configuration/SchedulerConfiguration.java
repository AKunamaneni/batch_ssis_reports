package com.uhg.reports.batch.configuration;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.uhg.reports.batch.properties.ReportsBatchProperties;

@Configuration
public class SchedulerConfiguration {
    private final ReportsBatchProperties reportsBatchProperties;
    public SchedulerConfiguration(ReportsBatchProperties reportsBatchProperties) {
        this.reportsBatchProperties = reportsBatchProperties;
    }
    
    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(Optional.
                ofNullable(reportsBatchProperties.getConcurrencyLimit()).orElse(5));
        simpleAsyncTaskExecutor.setDaemon(false);
        simpleAsyncTaskExecutor.setThreadGroupName("uhg batch job launcher");
        simpleAsyncTaskExecutor.setThreadNamePrefix("REPORTS JOB EXECUTOR");
        return simpleAsyncTaskExecutor;
    }
}
