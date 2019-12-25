package com.uhg.reports.batch.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
@Slf4j
public class ReportsService {

    private Job reportsJob;
    private JobLauncher joblauncher;

    public ReportsService(Job reportsJob, JobLauncher joblauncher) {
        this.reportsJob = reportsJob;
        this.joblauncher = joblauncher;
    }

    @Async
    public void launchJob(JobExecutionContext context){
        log.info("launchJob start: {}", context);
        JobExecution  jobExecution = null;
        try {

            jobExecution = joblauncher.run(reportsJob, new JobParametersBuilder().addDate("timestamp", new Timestamp(new Date().getTime()))
                    .toJobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            log.error("Excpetion while launching batch job", e);
        }
        log.info("launchJob end: {}, job execution: {}", context, jobExecution);
    }
    
}
