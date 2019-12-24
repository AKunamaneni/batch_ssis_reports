package com.uhg.reports.batch.job;

import com.uhg.reports.batch.entity.DashboardEmbeddedReport;
import com.uhg.reports.batch.entity.DashboardEmbeddedReportProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Slf4j
public class ReportsProcessor implements ItemProcessor<DashboardEmbeddedReportProperty, DashboardEmbeddedReport> {

    @Value("#{stepExecution}")
    private StepExecution stepExecution;

    @Override
    public DashboardEmbeddedReport process(DashboardEmbeddedReportProperty dashboardEmbeddedReportProperty) throws Exception {
        log.info("job id:{}, Step id: {} In-Transit processor start for {} ",stepExecution.getJobExecutionId(),
                stepExecution.getId());
        //code for converting html to image and return DB entity
        return null;
    }
    

}
