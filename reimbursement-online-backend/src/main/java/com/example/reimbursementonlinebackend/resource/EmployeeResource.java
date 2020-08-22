package com.example.reimbursementonlinebackend.resource;

import com.example.reimbursementonlinebackend.domain.Employee;
import com.example.reimbursementonlinebackend.domain.User;
import com.example.reimbursementonlinebackend.service.EmployeeService;
import com.example.reimbursementonlinebackend.service.dto.PasswordDTO;
import com.example.reimbursementonlinebackend.service.dto.ProfileDTO;
import com.example.reimbursementonlinebackend.util.AuthenticationUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/employee")
public class EmployeeResource {

    private EmployeeService employeeService;

    private AuthenticationUtil authenticationUtil;

    public EmployeeResource(EmployeeService employeeService, AuthenticationUtil authenticationUtil) {
        this.employeeService = employeeService;
        this.authenticationUtil = authenticationUtil;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getProfile() {
        Employee employee = authenticationUtil.getCurrentEmployee();
        return ResponseEntity.ok(employeeService.getProfile(employee));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/edit-profile")
    public ResponseEntity<ProfileDTO> editProfile(@RequestBody ProfileDTO dto) {
        Employee employee = authenticationUtil.getCurrentEmployee();
        return ResponseEntity.ok(employeeService.editProfile(dto, employee));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/edit-password")
    public ResponseEntity<Boolean> editPassword(@RequestBody PasswordDTO dto) {
        User user = authenticationUtil.getCurrentUser();
        return ResponseEntity.ok(employeeService.editPassword(dto, user));
    }
}
