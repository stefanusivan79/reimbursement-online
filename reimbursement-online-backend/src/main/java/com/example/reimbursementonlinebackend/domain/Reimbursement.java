package com.example.reimbursementonlinebackend.domain;

import com.example.reimbursementonlinebackend.enums.StatusReimbursement;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity
@Table(name = "reimbursements")
public class Reimbursement extends DomainBase {

    @Enumerated(EnumType.STRING)
    private StatusReimbursement status;

    @Column(name = "date_purchase")
    private Instant datePurchase;

    private String description;

    private BigDecimal amount;

    @OneToOne
    private Employee employee;
}
