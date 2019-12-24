package com.uhg.reports.batch.configuration;

import com.jbhunt.infrastructure.notification.entity.EDINotificationSubscription;
import com.uhg.reports.batch.entity.DashboardEmbeddedReport;
import com.uhg.reports.batch.entity.DashboardEmbeddedReportProperty;
import com.uhg.reports.batch.job.ReportsProcessor;
import com.uhg.reports.batch.job.ReportsWriter;
import com.uhg.reports.batch.listener.ReportsChunkListener;
import com.uhg.reports.batch.listener.ReportsJobListener;
import com.uhg.reports.batch.listener.ReportsStepListener;
import com.uhg.reports.batch.properties.ReportsBatchProperties;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.FutureTask;

@Configuration
public class BatchConfiguration {

    private static final String GET_MOST_RECENT_RECORD_QUERY = "";

    private JobBuilderFactory jobFactory;

    private StepBuilderFactory stepFactory;
    
    private final ReportsBatchProperties reportsBatchProperties;

    public BatchConfiguration(JobBuilderFactory jobFactory, StepBuilderFactory stepFactory, ReportsBatchProperties reportsBatchProperties) {
        this.jobFactory = jobFactory;
        this.stepFactory = stepFactory;
        this.reportsBatchProperties = reportsBatchProperties;
    }

    @Bean
    public Job reportsJob(Step reportsStep, ReportsJobListener reportsJobListener) {
        return jobFactory.get("REPORTS_JOB").start(reportsStep).listener(reportsJobListener).build();
    }

    @SuppressWarnings("unchecked")
    @Bean
    public Step inTransitStep(JpaPagingItemReader<DashboardEmbeddedReportProperty> reportsJPAPagingItemReader, ReportsWriter writer,
                              ReportsProcessor processor, PlatformTransactionManager jpaTransactionManager,
                              SimpleAsyncTaskExecutor simpleAsyncTaskExecutor, ReportsStepListener inTransitStepListener,
                              ReportsChunkListener reportsChunkListener) {
        
        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        
        @SuppressWarnings("rawtypes")
        AsyncItemProcessor asyn = new AsyncItemProcessor<>();
        asyn.setDelegate(processor);
        asyn.setTaskExecutor(simpleAsyncTaskExecutor);
        
        return stepFactory.get("REPORTS_STEP").
                <DashboardEmbeddedReportProperty, FutureTask<DashboardEmbeddedReport>>chunk(100).
                reader(reportsJPAPagingItemReader).
                processor((ItemProcessor<? super DashboardEmbeddedReportProperty, ? extends FutureTask<DashboardEmbeddedReport>>) asyn).
                writer(writer).
                listener(inTransitStepListener).
                listener(reportsChunkListener).
                transactionManager(jpaTransactionManager).
                transactionAttribute(transactionAttribute).
                allowStartIfComplete(true).build();
    }
    
    @Bean
    public SimpleAsyncTaskExecutor simpleAsyncTaskExecutor(){
         return new  SimpleAsyncTaskExecutor();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<DashboardEmbeddedReportProperty> reportsJPAPagingItemReader(
            EntityManagerFactory entityManagerFactory, @Value("#{stepExecution}")StepExecution stepExecution) {
        JpaPagingItemReader<DashboardEmbeddedReportProperty> itemReader = new JpaPagingItemReader<>();
        itemReader.setTransacted(false);
        itemReader.setPageSize(Optional.ofNullable(reportsBatchProperties.getPageSize()).orElse(100));
        JpaNativeQueryProvider<DashboardEmbeddedReportProperty> jpaNativeQueryProvider = new JpaNativeQueryProvider<>();
        jpaNativeQueryProvider.setEntityManager(entityManagerFactory.createEntityManager());
        jpaNativeQueryProvider.setEntityClass(DashboardEmbeddedReportProperty.class);
        jpaNativeQueryProvider
                .setSqlQuery(GET_MOST_RECENT_RECORD_QUERY);
   
        itemReader.setEntityManagerFactory(entityManagerFactory);
        itemReader.setQueryProvider(jpaNativeQueryProvider);
        Map<String, Object> params = new HashMap<>();
        params.put("batchTime", stepExecution.getJobExecution().getStartTime());
        itemReader.setParameterValues(params);
        return itemReader;
    }
    
    
}

