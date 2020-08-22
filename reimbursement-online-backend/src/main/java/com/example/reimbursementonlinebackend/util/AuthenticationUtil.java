package com.example.reimbursementonlinebackend.util;

import com.example.reimbursementonlinebackend.domain.Employee;
import com.example.reimbursementonlinebackend.domain.User;
import com.example.reimbursementonlinebackend.exception.BadRequestException;
import com.example.reimbursementonlinebackend.repository.EmployeeRepository;
import com.example.reimbursementonlinebackend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtil {

    private UserRepository userRepository;

    private EmployeeRepository employeeRepository;

    public AuthenticationUtil(UserRepository userRepository, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findOneByUsername(authentication.getName())
                .orElseThrow(() -> new BadRequestException("User not found"));
    }

    public Employee getCurrentEmployee() {
        return employeeRepository.findByUser(getCurrentUser());
    }

}