package com.example.reimbursementonlinebackend.service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProfileDTO {

    private String bankAccount;

    private String email;

    private String username;

    private String fullName;
}
