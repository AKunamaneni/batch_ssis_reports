package com.uhg.reports.batch.controller;

import com.uhg.reports.batch.processor.ReportsProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchLaunchController {

    private final ReportsProcessor reportsProcessor;

    public BatchLaunchController(ReportsProcessor reportsProcessor) {
        this.reportsProcessor = reportsProcessor;
    }


    @GetMapping("/batch/launch/reports")
    public void launchEmbeddedReportsBatch() {
        reportsProcessor.process();
    }
}
