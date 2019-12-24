package com.uhg.reports.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class ReportsStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
      log.info("beforeStep: {}", stepExecution);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("afterStep: {}", stepExecution);
        return stepExecution.getExitStatus();
    }

}
