package com.example.reimbursementonlinebackend.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "roles")
public class Role extends DomainBase {

    private String name;

}