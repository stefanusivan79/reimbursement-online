package com.example.reimbursementonlinebackend.service.dto;

import lombok.Data;

@Data
public class SlackDTO {

    private String text;

    public SlackDTO(String text) {
        this.text = text;
    }
}
