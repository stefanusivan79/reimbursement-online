package com.example.reimbursementonlinebackend.service.dto;

import com.example.reimbursementonlinebackend.enums.StatusReimbursement;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReimbursementDTO {

    private String secureId;

    private StatusReimbursement status;

    private Long datePurchase;

    private String description;

    private BigDecimal amount;

    private Long creationDate;

    private List<RequestDownloadDTO> attachments;
}
