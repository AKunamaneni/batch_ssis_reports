package com.uhg.reports.batch.job;

import com.uhg.reports.batch.entity.DashboardEmbeddedReport;
import com.uhg.reports.batch.entity.DashboardEmbeddedReportProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.LocalDateTime;

@Component
@StepScope
@Slf4j
public class ReportsProcessor implements ItemProcessor<DashboardEmbeddedReportProperty, DashboardEmbeddedReport> {

    @Value("#{stepExecution}")
    private StepExecution stepExecution;

    @Override
    public DashboardEmbeddedReport process(DashboardEmbeddedReportProperty dashboardEmbeddedReportProperty) throws Exception {
        log.info("job id:{}, Step id: {} Reports processor start for {} ",stepExecution.getJobExecutionId(),
                stepExecution.getId(), dashboardEmbeddedReportProperty.getReportname());

        BufferedImage bufferedImage = ImageIO.read(new URL(dashboardEmbeddedReportProperty.getReportLink()));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage,"jpg",byteArrayOutputStream);
        byteArrayOutputStream.flush();
        DashboardEmbeddedReport dashboardEmbeddedReport = new DashboardEmbeddedReport();
        dashboardEmbeddedReport.setImage(byteArrayOutputStream.toByteArray());
        dashboardEmbeddedReport.setReportname(dashboardEmbeddedReportProperty.getReportname());
        dashboardEmbeddedReport.setWeekendDate(LocalDateTime.now().toString());
        return dashboardEmbeddedReport;
    }
    

}
