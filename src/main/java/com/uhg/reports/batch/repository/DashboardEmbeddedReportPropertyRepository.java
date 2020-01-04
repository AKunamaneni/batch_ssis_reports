package com.uhg.reports.batch.repository;

import com.uhg.reports.batch.entity.DashboardEmbeddedReportProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DashboardEmbeddedReportPropertyRepository extends JpaRepository<DashboardEmbeddedReportProperty, Integer> {

    List<DashboardEmbeddedReportProperty> findByIsActiveEquals(String isActive);
    
}
