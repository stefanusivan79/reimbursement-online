package com.example.reimbursementonlinebackend.repository;

import com.example.reimbursementonlinebackend.domain.OauthClientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthClientDetailRepository extends JpaRepository<OauthClientDetail, String> {
}
