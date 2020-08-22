package com.example.reimbursementonlinebackend.repository;

import com.example.reimbursementonlinebackend.domain.SystemParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemParameterRepository extends JpaRepository<SystemParameter, Long> {

    Optional<SystemParameter> findByCodeAndValue(String code, String value);
}
