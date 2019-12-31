package com.uhg.reports.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Component

public class ReportsStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
    	System.out.println("beforeStep: {}"+ stepExecution);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
    	System.out.println("afterStep: {}"+ stepExecution);
        return stepExecution.getExitStatus();
    }

}
