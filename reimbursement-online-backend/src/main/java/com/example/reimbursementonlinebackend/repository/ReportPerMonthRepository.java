package com.example.reimbursementonlinebackend.repository;

import com.example.reimbursementonlinebackend.service.dto.ReportPerMonthDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportPerMonthRepository extends JpaRepository<ReportPerMonthDTO, Integer> {

    @Query(value = "CALL sp_getReportPerMonth(:year_month_report);", nativeQuery = true)
    List<ReportPerMonthDTO> getReportPerMonth(@Param("year_month_report") String year_month_report);

}
