package com.uhg.reports.batch.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
        name = "dashboard_embedded_report",
        schema = "redis"
)

public class DashboardEmbeddedReport implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id"
    )
    private int id;

    @Column(
            name = "week_end_date"
    )

    private String weekendDate;
    @Column(
            name = "report_name"
    )
    private String reportname;

    @Column(
            name = "image"
    )
    private byte[] image;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWeekendDate() {
		return weekendDate;
	}

	public void setWeekendDate(String weekendDate) {
		this.weekendDate = weekendDate;
	}

	public String getReportname() {
		return reportname;
	}

	public void setReportname(String reportname) {
		this.reportname = reportname;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
    
    
}
