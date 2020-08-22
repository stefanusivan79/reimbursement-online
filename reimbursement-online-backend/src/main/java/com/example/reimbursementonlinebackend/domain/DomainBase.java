package com.example.reimbursementonlinebackend.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@DynamicUpdate
public abstract class DomainBase implements Serializable {

    private static final long serialVersionUID = -7369920601847524273L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SECURE_ID", unique = true, length = 36)
    private String secureId;

    @CreatedDate
    @Column(name = "DATE_CREATED", updatable = false)
    private Instant creationDate;

    @CreatedBy
    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "DATE_MODIFIED")
    private Instant modificationDate;

    @LastModifiedBy
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Version
    @Column(name = "VERSION")
    private Integer version = 0;

    @Column(name = "DELETED")
    private Boolean deleted;

    @PrePersist
    public void prePersist() {
        this.secureId = UUID.randomUUID().toString();
        this.deleted = false;
        this.creationDate = Instant.now();
        this.modificationDate = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.modificationDate = Instant.now();
    }
}