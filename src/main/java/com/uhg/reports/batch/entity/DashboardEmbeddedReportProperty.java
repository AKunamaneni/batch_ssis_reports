package com.uhg.reports.batch.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "dashboard_embedded_report_property",
        schema = "schema"
)

public class DashboardEmbeddedReportProperty implements Serializable {

    @Id
    @Column(
            name = "report_name"
    )
    private String reportname;

    @Column(
            name = "report_link"
    )

    private String reportLink;


    @Column(
            name = "image_length"
    )
    private int imageLength;

    @Column(
            name = "image_width"
    )
    private int imageWidth;

    @Column(
            name = "is_active"
    )
    private String isActive;

	public String getReportname() {
		return reportname;
	}

	public void setReportname(String reportname) {
		this.reportname = reportname;
	}

	public String getReportLink() {
		return reportLink;
	}

	public void setReportLink(String reportLink) {
		this.reportLink = reportLink;
	}

	public int getImageLength() {
		return imageLength;
	}

	public void setImageLength(int imageLength) {
		this.imageLength = imageLength;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


    
}
