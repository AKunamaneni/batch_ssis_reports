package com.uhg.reports.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableJpaRepositories(entityManagerFactoryRef = "localContainerEntityManagerFactoryBean", basePackages = {
        "com.uhg.reports.batch.entity","com.uhg.reports.batch.repository" })
public class Application {

    public static void main(String[] args) {
    	SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);  
    	builder.headless(false);  
    	ConfigurableApplicationContext context = builder.run(args);
    }
}
