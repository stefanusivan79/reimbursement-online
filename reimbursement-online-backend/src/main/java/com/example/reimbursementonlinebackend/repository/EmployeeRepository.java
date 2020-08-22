package com.example.reimbursementonlinebackend.repository;

import com.example.reimbursementonlinebackend.domain.Employee;
import com.example.reimbursementonlinebackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByUser(User user);
}
