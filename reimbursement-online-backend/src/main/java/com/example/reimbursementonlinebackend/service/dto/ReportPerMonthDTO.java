package com.example.reimbursementonlinebackend.service.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "report_per_month")
public class ReportPerMonthDTO {

    @Id
    private Integer id;

    private String fullName;

    private BigDecimal total;
}