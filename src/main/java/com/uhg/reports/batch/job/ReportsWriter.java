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
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

@Component
@StepScope
@Slf4j
public class ReportsWriter implements ItemWriter<FutureTask<DashboardEmbeddedReport>> {

    
    @Value("#{stepExecution}")
    private StepExecution stepExecution;
    private DashboardEmbeddedReportRepository dashboardEmbeddedReportRepository;
    
    
    public ReportsWriter(DashboardEmbeddedReportRepository dashboardEmbeddedReportRepository) {
        this.dashboardEmbeddedReportRepository = dashboardEmbeddedReportRepository;
    }
    
    @Override
    public void write(List<? extends FutureTask<DashboardEmbeddedReport>> dashboardEmbeddedReports) throws Exception {

        List<DashboardEmbeddedReport>  dashboardEmbeddedReportList =  dashboardEmbeddedReports.stream().map(t -> {
            try {
                return t.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("exception while getting dashboardEmbeddedReports from futureTask", e);
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        log.info("job id:{}, Step id: {} In-Transit writer end for {} ",stepExecution.getJobExecutionId(),
                stepExecution.getId());
        dashboardEmbeddedReportList.forEach(dashboardEmbeddedReport -> {
            log.info("saving image for report {} to database", dashboardEmbeddedReport.getImage());
                    dashboardEmbeddedReportRepository.save(dashboardEmbeddedReport);
                });
            log.info("job id:{}, Step id: {} In-Transit writer end for {} ",stepExecution.getJobExecutionId(),
                    stepExecution.getId());
    }

}
