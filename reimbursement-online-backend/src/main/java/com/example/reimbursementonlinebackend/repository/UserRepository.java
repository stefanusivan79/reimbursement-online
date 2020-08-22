package com.example.reimbursementonlinebackend.repository;

import com.example.reimbursementonlinebackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByUsername(String username);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneBySecureId(String secureId);

}
