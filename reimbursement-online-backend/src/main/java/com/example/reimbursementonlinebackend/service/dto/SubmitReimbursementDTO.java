package com.example.reimbursementonlinebackend.service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SubmitReimbursementDTO {

    private Instant datePurchase;

    private String description;

    private BigDecimal amount;
}
