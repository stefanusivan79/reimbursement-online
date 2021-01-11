package com.example.reimbursementonlinebackend.service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ListReportDTO {

    private String month;

    private Integer monthNumber;

    private Integer year;

    private String periode;
}
