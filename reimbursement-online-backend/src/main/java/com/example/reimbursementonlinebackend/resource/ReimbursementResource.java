package com.example.reimbursementonlinebackend.resource;

import com.example.reimbursementonlinebackend.domain.Employee;
import com.example.reimbursementonlinebackend.service.ReimbursementService;
import com.example.reimbursementonlinebackend.service.dto.RequestReportPerMonthDTO;
import com.example.reimbursementonlinebackend.service.dto.SubmitReimbursementDTO;
import com.example.reimbursementonlinebackend.util.AuthenticationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("api/reimbursement")
public class ReimbursementResource {

    private final ReimbursementService reimbursementService;

    private final AuthenticationUtil authenticationUtil;

    public ReimbursementResource(ReimbursementService reimbursementService, AuthenticationUtil authenticationUtil) {
        this.reimbursementService = reimbursementService;
        this.authenticationUtil = authenticationUtil;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/employee")
    public ResponseEntity<Map<String, Object>> getAllReimbursementEmployee(@RequestParam Integer page, @RequestParam(defaultValue = "10") Integer limit,
                                                                           @RequestParam(defaultValue = "descend") String sortOrder,
                                                                           @RequestParam(defaultValue = "creationDate") String sortField) {

        Employee employee = authenticationUtil.getCurrentEmployee();
        return ResponseEntity.ok(reimbursementService.findReimbursementByEmployee(page, limit, sortOrder, sortField, employee));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllReimbursement(@RequestParam Integer page, @RequestParam(defaultValue = "10") Integer limit,
                                                                   @RequestParam(defaultValue = "descend") String sortOrder,
                                                                   @RequestParam(defaultValue = "creationDate") String sortField) {

        return ResponseEntity.ok(reimbursementService.findAll(page, limit, sortOrder, sortField));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Boolean> submitReimbursement(@RequestBody SubmitReimbursementDTO dto) {

        Employee employee = authenticationUtil.getCurrentEmployee();

        return ResponseEntity.status(HttpStatus.CREATED).body(reimbursementService.submitReimbursement(dto, employee));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/reject")
    public ResponseEntity<Map<String, Object>> rejectReimbursement(@RequestParam String reimbursementId) {
        return ResponseEntity.ok(reimbursementService.rejectReimbursement(reimbursementId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/accept")
    public ResponseEntity<Map<String, Object>> acceptReimbursement(@RequestParam String reimbursementId) {
        return ResponseEntity.ok(reimbursementService.acceptReimbursement(reimbursementId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/complete")
    public ResponseEntity<Map<String, Object>> completeReimbursement(@RequestParam String reimbursementId) {
        return ResponseEntity.ok(reimbursementService.completeReimbursement(reimbursementId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/report")
    public void getReportPerMonth(HttpServletResponse response, @RequestBody RequestReportPerMonthDTO request) {
        reimbursementService.getReportPerMonth(response, request);
    }

}
