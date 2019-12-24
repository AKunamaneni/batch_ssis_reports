package com.uhg.reports.batch.repository;

import com.uhg.reports.batch.entity.DashboardEmbeddedReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardEmbeddedReportRepository extends JpaRepository<DashboardEmbeddedReport, Integer> {

    <E> E find(Integer id, Class<E> clz);

    public void flush();
}
