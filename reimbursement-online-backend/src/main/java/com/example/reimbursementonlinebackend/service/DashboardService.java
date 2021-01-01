package com.example.reimbursementonlinebackend.service;

import com.example.reimbursementonlinebackend.domain.Reimbursement;
import com.example.reimbursementonlinebackend.enums.StatusReimbursement;
import com.example.reimbursementonlinebackend.repository.ReimbursementRepository;
import com.example.reimbursementonlinebackend.service.dto.DashboardStatisticDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class DashboardService {

    private final ReimbursementRepository reimbursementRepository;

    public DashboardService(ReimbursementRepository reimbursementRepository) {
        this.reimbursementRepository = reimbursementRepository;
    }

    public DashboardStatisticDTO getDataDashboardStatic() {
        int[] count = new int[4];
        List<Reimbursement> reimbursements = reimbursementRepository.findAll().stream().filter(x -> {
            int monthNow = Instant.now().atZone(ZoneId.systemDefault()).getMonthValue();
            int monthDatePurchase = x.getDatePurchase().atZone(ZoneId.systemDefault()).getMonthValue();
            return monthNow == monthDatePurchase;
        }).collect(Collectors.toList());

        for (Reimbursement reimbursement: reimbursements) {
            if (reimbursement.getStatus().equals(StatusReimbursement.SUBMITTED)) {
                count[0]++;
            } else if (reimbursement.getStatus().equals(StatusReimbursement.ON_PROGRESS)) {
                count[1]++;
            } else if (reimbursement.getStatus().equals(StatusReimbursement.COMPLETED)) {
                count[2]++;
            } else {
                count[3]++;
            }
        }

        DashboardStatisticDTO result = new DashboardStatisticDTO();
        result.setSubmitted(count[0]);
        result.setInProgress(count[1]);
        result.setCompleted(count[2]);
        result.setRejected(count[3]);

        return result;
    }
}
