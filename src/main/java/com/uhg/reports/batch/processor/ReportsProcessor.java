package com.uhg.reports.batch.job;

import com.uhg.reports.batch.entity.DashboardEmbeddedReport;
import com.uhg.reports.batch.entity.DashboardEmbeddedReportProperty;
import com.uhg.reports.batch.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.LocalDateTime;

@Component
@EnableScheduling
public class ReportsProcessor {

    private final ReportsService reportsService;

    @Autowired
    public ReportsProcessor(ReportsService reportsService) {
        this.reportsService = reportsService;
    }


    @Scheduled(cron = "${batch.process.cron}")
    public void process(DashboardEmbeddedReportProperty dashboardEmbeddedReportProperty) throws Exception {

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
