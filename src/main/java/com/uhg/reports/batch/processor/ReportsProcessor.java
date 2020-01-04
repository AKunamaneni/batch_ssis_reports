package com.uhg.reports.batch.processor;

import com.uhg.reports.batch.entity.DashboardEmbeddedReport;
import com.uhg.reports.batch.entity.DashboardEmbeddedReportProperty;
import com.uhg.reports.batch.repository.DashboardEmbeddedReportPropertyRepository;
import com.uhg.reports.batch.repository.DashboardEmbeddedReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@EnableScheduling
public class ReportsProcessor {

    private final DashboardEmbeddedReportRepository dashboardEmbeddedReportRepository;
    private final DashboardEmbeddedReportPropertyRepository dashboardEmbeddedReportPropertyRepository;

    @Autowired
    public ReportsProcessor(DashboardEmbeddedReportRepository dashboardEmbeddedReportRepository, DashboardEmbeddedReportPropertyRepository dashboardEmbeddedReportPropertyRepository) {
        this.dashboardEmbeddedReportRepository = dashboardEmbeddedReportRepository;
        this.dashboardEmbeddedReportPropertyRepository = dashboardEmbeddedReportPropertyRepository;
    }


    @Scheduled(cron = "${reports.batch.cron}")
    public void process() {
        List<DashboardEmbeddedReportProperty> reports = dashboardEmbeddedReportPropertyRepository.findByIsActiveEquals("Y");
        System.out.println("reports list to be processed: "+reports);
        List<DashboardEmbeddedReport> reportImages = new ArrayList<>();
        if(!CollectionUtils.isEmpty(reports)){
            reports.forEach(dashboardEmbeddedReportProperty -> {
                System.out.println("converting url to image for report: "+dashboardEmbeddedReportProperty.getReportname());
                BufferedImage bufferedImage = null;
                ByteArrayOutputStream byteArrayOutputStream = null;
                try {
                    bufferedImage = ImageIO.read(new URL(dashboardEmbeddedReportProperty.getReportLink()));
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage,"jpg",byteArrayOutputStream);
                    DashboardEmbeddedReport dashboardEmbeddedReport = new DashboardEmbeddedReport();
                    dashboardEmbeddedReport.setImage(byteArrayOutputStream.toByteArray());
                    dashboardEmbeddedReport.setReportname(dashboardEmbeddedReportProperty.getReportname());
                    dashboardEmbeddedReport.setWeekendDate(LocalDateTime.now().toString());
                    byteArrayOutputStream.flush();
                    reportImages.add(dashboardEmbeddedReport);

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });
        }
        reportImages.forEach(dashboardEmbeddedReport -> {
            System.out.println("Saving report to DB: "+dashboardEmbeddedReport.getReportname());
            dashboardEmbeddedReportRepository.save(dashboardEmbeddedReport);
        });
    }
    

}
