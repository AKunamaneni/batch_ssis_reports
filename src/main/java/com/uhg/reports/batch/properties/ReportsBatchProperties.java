package com.uhg.reports.batch.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

@RefreshScope
@Component
@ConfigurationProperties("reports.batch.job")

public class ReportsBatchProperties {
    private Long startDelay;
    private String conExpression;
    private Integer concurrencyLimit;
    private Integer pageSize;
	public Long getStartDelay() {
		return startDelay;
	}
	public void setStartDelay(Long startDelay) {
		this.startDelay = startDelay;
	}
	public String getConExpression() {
		return conExpression;
	}
	public void setConExpression(String conExpression) {
		this.conExpression = conExpression;
	}
	public Integer getConcurrencyLimit() {
		return concurrencyLimit;
	}
	public void setConcurrencyLimit(Integer concurrencyLimit) {
		this.concurrencyLimit = concurrencyLimit;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
    
    
}
