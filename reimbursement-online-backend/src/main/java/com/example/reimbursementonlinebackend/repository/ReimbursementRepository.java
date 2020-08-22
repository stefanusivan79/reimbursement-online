package com.example.reimbursementonlinebackend.repository;

import com.example.reimbursementonlinebackend.domain.Employee;
import com.example.reimbursementonlinebackend.domain.Reimbursement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReimbursementRepository extends JpaRepository<Reimbursement, Long> {

    Page<Reimbursement> findByEmployee(Employee employee, Pageable pageable);

    Optional<Reimbursement> findBySecureId(String reimbursementId);

}
