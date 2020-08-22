package com.example.reimbursementonlinebackend.service;

import com.example.reimbursementonlinebackend.domain.Employee;
import com.example.reimbursementonlinebackend.domain.User;
import com.example.reimbursementonlinebackend.exception.BadRequestException;
import com.example.reimbursementonlinebackend.repository.EmployeeRepository;
import com.example.reimbursementonlinebackend.repository.UserRepository;
import com.example.reimbursementonlinebackend.service.dto.PasswordDTO;
import com.example.reimbursementonlinebackend.service.dto.ProfileDTO;
import com.example.reimbursementonlinebackend.service.mapper.EmployeeMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, UserRepository userRepository,
                           PasswordEncoder passwordEncoder, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeMapper = employeeMapper;
    }

    public ProfileDTO getProfile(Employee employee) {
        return employeeMapper.employeeToProfileDto(employee);
    }

    public ProfileDTO editProfile(ProfileDTO dto, Employee employee) {

        employee.setFullName(dto.getFullName());
        employee.setBankAccount(dto.getBankAccount());
        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.employeeToProfileDto(savedEmployee);
    }

    public boolean editPassword(PasswordDTO dto, User user) {
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Your current password is incorrect!");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
        return true;
    }

}
