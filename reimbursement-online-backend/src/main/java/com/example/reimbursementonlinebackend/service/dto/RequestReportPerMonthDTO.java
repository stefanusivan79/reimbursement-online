package com.example.reimbursementonlinebackend.service.dto;

import lombok.Data;

@Data
public class RequestReportPerMonthDTO {

    private Integer year;

    private String month;

    private Integer monthNumber;
}
