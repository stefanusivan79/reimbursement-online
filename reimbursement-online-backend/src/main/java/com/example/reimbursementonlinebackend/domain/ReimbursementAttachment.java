package com.example.reimbursementonlinebackend.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "reimbursement_attachment")
public class ReimbursementAttachment extends DomainBase {

    private String url;

    private String name;

    @ManyToOne
    private Reimbursement reimbursement;
}
