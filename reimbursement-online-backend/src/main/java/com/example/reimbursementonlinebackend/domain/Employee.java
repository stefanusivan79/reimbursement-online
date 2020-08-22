package com.example.reimbursementonlinebackend.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "employee")
public class Employee extends DomainBase {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "bank_account")
    private String bankAccount;

    @OneToOne
    private User user;
}
