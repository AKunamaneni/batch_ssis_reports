package com.uhg.reports.batch.repository;

import com.uhg.reports.batch.entity.DashboardEmbeddedReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DashboardEmbeddedReportRepository extends JpaRepository<DashboardEmbeddedReport, Integer> {


}
