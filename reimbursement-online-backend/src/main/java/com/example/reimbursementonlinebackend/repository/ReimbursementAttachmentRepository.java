package com.example.reimbursementonlinebackend.repository;

import com.example.reimbursementonlinebackend.domain.Reimbursement;
import com.example.reimbursementonlinebackend.domain.ReimbursementAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbursementAttachmentRepository extends JpaRepository<ReimbursementAttachment, Long> {

    List<ReimbursementAttachment> findByReimbursement(Reimbursement reimbursement);
}
