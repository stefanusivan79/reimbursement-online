package com.example.reimbursementonlinebackend.resource;

import com.example.reimbursementonlinebackend.service.DashboardService;
import com.example.reimbursementonlinebackend.service.dto.DashboardStatisticDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dashboard")
public class DashboardResource {

    private final DashboardService dashboardService;

    public DashboardResource(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/statistic")
    public ResponseEntity<DashboardStatisticDTO> getDataDashboardStatic() {
        return ResponseEntity.ok(dashboardService.getDataDashboardStatic());
    }
}
