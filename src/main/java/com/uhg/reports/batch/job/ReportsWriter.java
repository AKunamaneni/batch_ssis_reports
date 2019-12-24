package com.uhg.reports.batch.job;

import com.uhg.reports.batch.entity.DashboardEmbeddedReport;
import com.uhg.reports.batch.properties.ReportsBatchProperties;
import com.uhg.reports.batch.repository.DashboardEmbeddedReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.FutureTask;

@Component
@StepScope
@Slf4j
public class ReportsWriter implements ItemWriter<FutureTask<DashboardEmbeddedReport>> {

    
    @Value("#{stepExecution}")
    private StepExecution stepExecution;
    private ReportsBatchProperties reportsBatchProperties;
    private DashboardEmbeddedReportRepository dashboardEmbeddedReportRepository;
    
    
    public ReportsWriter(DashboardEmbeddedReportRepository dashboardEmbeddedReportRepository, ReportsBatchProperties reportsBatchProperties) {
        this.dashboardEmbeddedReportRepository = dashboardEmbeddedReportRepository;
        this.reportsBatchProperties = reportsBatchProperties;
    }
    
    @Override
    public void write(List<? extends FutureTask<DashboardEmbeddedReport>> inTransitNotificationDtos) throws Exception {

            //code to save the image into DB
            log.info("job id:{}, Step id: {} In-Transit writer end for {} ",stepExecution.getJobExecutionId(),
                    stepExecution.getId());
    }

}
