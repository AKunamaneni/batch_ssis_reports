package com.uhg.reports.batch.controller;

import com.uhg.reports.batch.exception.UHGRuntimeException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class BatchLaunchController {

    private Job job;

    private JobLauncher joblauncher;

    public BatchLaunchController(Job job, JobLauncher joblauncher) {
        this.job = job;
        this.joblauncher = joblauncher;
    }

    @GetMapping("/batch/launch/reports")
    public String launchEmbeddedReportsBatch() {
        JobExecution jobExecution = null;
        try {
            jobExecution = joblauncher.run(job,
                    new JobParametersBuilder().addString("time", new Date().toString()).toJobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            throw new UHGRuntimeException("Exception occured while running job from rest service", e);
        }
        return jobExecution.getExitStatus().getExitCode();
    }
}
