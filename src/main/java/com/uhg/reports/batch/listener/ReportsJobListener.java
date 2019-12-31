package com.uhg.reports.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Component

public class ReportsJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
    	System.out.println("beforeJob: {}"+ jobExecution);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
    	System.out.println("afterJob : {}"+ jobExecution);
    }

}
