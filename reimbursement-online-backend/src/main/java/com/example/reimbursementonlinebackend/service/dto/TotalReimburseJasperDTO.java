package com.example.reimbursementonlinebackend.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TotalReimburseJasperDTO {

    private String fullName;
    private BigDecimal total;
}
