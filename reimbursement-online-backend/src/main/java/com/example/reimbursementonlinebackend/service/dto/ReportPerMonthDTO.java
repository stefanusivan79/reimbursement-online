package com.example.reimbursementonlinebackend.service.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class ReportPerMonthDTO {

    @Id
    private Integer id;

    private String fullName;

    private BigDecimal total;
}