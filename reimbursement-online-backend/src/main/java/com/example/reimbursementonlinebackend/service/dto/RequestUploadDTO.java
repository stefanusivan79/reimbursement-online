package com.example.reimbursementonlinebackend.service.dto;

import lombok.Data;

@Data
public class RequestUploadDTO {

    private Long lastModified;

    private String name;

    private ResponseUpload response;

    // bytes
    private Long size;

    private String status;

    private String type;

    private String uid;
}
