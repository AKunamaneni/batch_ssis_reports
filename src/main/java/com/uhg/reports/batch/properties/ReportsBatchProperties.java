package com.uhg.reports.batch.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

@RefreshScope
@Component
@ConfigurationProperties("reports.batch.job")
@Data
public class ReportsBatchProperties {
    private Long startDelay;
    private Double repeatInterval;
    private Integer concurrencyLimit;
    private Integer pageSize;
    private Integer batchSize;
}
