package com.uhg.reports.batch.schedule;

import com.uhg.reports.batch.service.ReportsService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;


public class ReportsJobScheduler extends QuartzJobBean{

    @Autowired
    private ReportsService reportsService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    	System.out.println("Quartz job started: {}"+ context);
        reportsService.launchJob(context);
    }
}
