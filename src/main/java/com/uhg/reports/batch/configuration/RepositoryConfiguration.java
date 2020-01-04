package com.uhg.reports.batch.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(
            EntityManagerFactoryBuilder builder, @Qualifier("jpaDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.uhg.reports.batch.repository")
                .packages("com.uhg.reports.batch.entity").persistenceUnit("batch").build();

    }

}
