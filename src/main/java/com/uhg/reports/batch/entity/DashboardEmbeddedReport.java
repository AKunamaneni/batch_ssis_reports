package com.uhg.reports.batch.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "dashboard_embedded_report",
        schema = "schema"
)
@Data
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
}
