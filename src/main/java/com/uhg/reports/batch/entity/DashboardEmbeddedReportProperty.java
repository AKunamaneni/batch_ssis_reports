package com.uhg.reports.batch.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "dashboard_embedded_report_property",
        schema = "schema"
)
@Data
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


}
