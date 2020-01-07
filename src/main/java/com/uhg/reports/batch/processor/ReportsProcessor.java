package com.uhg.reports.batch.processor;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.uhg.reports.batch.entity.DashboardEmbeddedReport;
import com.uhg.reports.batch.entity.DashboardEmbeddedReportProperty;
import com.uhg.reports.batch.repository.DashboardEmbeddedReportPropertyRepository;
import com.uhg.reports.batch.repository.DashboardEmbeddedReportRepository;

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
                	File imgPath = new File("C:\\Users\\akunam\\Desktop",dashboardEmbeddedReportProperty.getReportname()+".png");  
                	bufferedImage = ImageIO.read(imgPath);
                	byteArrayOutputStream = new ByteArrayOutputStream();
                	ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
                    DashboardEmbeddedReport dashboardEmbeddedReport = new DashboardEmbeddedReport();
                    dashboardEmbeddedReport.setImage(byteArrayOutputStream.toByteArray());
                    dashboardEmbeddedReport.setReportname(dashboardEmbeddedReportProperty.getReportname());
                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");  
                     dashboardEmbeddedReport.setWeekendDate(sdfDate.format(new Date()));                    
                    byteArrayOutputStream.flush();
                    reportImages.add(dashboardEmbeddedReport);

                } catch (IOException e) {
                    e.printStackTrace();
                } 

            });
        }
        reportImages.forEach(dashboardEmbeddedReport -> {
            System.out.println("Saving report to DB: "+dashboardEmbeddedReport.getReportname());
            dashboardEmbeddedReportRepository.save(dashboardEmbeddedReport);
        });
    }
    

}
