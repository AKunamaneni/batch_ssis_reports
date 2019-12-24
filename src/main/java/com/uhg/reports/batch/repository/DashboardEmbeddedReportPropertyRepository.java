package com.uhg.reports.batch.repository;

import com.uhg.reports.batch.entity.DashboardEmbeddedReportProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardEmbeddedReportPropertyRepository extends JpaRepository<DashboardEmbeddedReportProperty, Integer> {
    <E> E find(Integer id, Class<E> clz);
    
    public void flush();
    
}
