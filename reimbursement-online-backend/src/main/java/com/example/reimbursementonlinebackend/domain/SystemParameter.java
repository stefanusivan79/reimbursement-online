package com.example.reimbursementonlinebackend.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "system_parameters")
public class SystemParameter extends DomainBase {

    private String code;

    private String value;

    private String description;
}
