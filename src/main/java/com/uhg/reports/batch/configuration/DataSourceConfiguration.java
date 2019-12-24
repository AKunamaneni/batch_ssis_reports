package com.uhg.reports.batch.configuration;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "uhg.mysql.datasource.reports")
    public DataSource jpaDataSource() {
        return DataSourceBuilder.create().type(org.apache.commons.dbcp2.BasicDataSource.class).build();
    }
}
