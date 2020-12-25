package com.example.reimbursementonlinebackend.service;

import com.example.reimbursementonlinebackend.domain.Employee;
import com.example.reimbursementonlinebackend.domain.Reimbursement;
import com.example.reimbursementonlinebackend.domain.ReimbursementAttachment;
import com.example.reimbursementonlinebackend.enums.StatusReimbursement;
import com.example.reimbursementonlinebackend.exception.ReimbursementNotFoundException;
import com.example.reimbursementonlinebackend.repository.ReimbursementAttachmentRepository;
import com.example.reimbursementonlinebackend.repository.ReimbursementRepository;
import com.example.reimbursementonlinebackend.repository.ReportPerMonthRepository;
import com.example.reimbursementonlinebackend.service.dto.ReimbursementDTO;
import com.example.reimbursementonlinebackend.service.dto.ReportPerMonthDTO;
import com.example.reimbursementonlinebackend.service.dto.RequestReportPerMonthDTO;
import com.example.reimbursementonlinebackend.service.dto.SubmitReimbursementDTO;
import com.example.reimbursementonlinebackend.service.mapper.ReimbursementMapper;
import com.example.reimbursementonlinebackend.util.PDFUtil;
import com.example.reimbursementonlinebackend.util.PaginationUtil;
import com.example.reimbursementonlinebackend.util.SlackUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ReimbursementService {

    private final ReimbursementRepository reimbursementRepository;
    private final ReimbursementMapper reimbursementMapper;
    private final ReimbursementAttachmentRepository reimbursementAttachmentRepository;
    private final SlackUtil slackUtil;
    private final ReportPerMonthRepository reportRepository;
    private final PDFUtil pdfUtil;

    public ReimbursementService(ReimbursementRepository reimbursementRepository, ReimbursementMapper reimbursementMapper,
                                ReimbursementAttachmentRepository reimbursementAttachmentRepository, SlackUtil slackUtil,
                                ReportPerMonthRepository reportRepository, PDFUtil pdfUtil) {
        this.reimbursementRepository = reimbursementRepository;
        this.reimbursementMapper = reimbursementMapper;
        this.reimbursementAttachmentRepository = reimbursementAttachmentRepository;
        this.slackUtil = slackUtil;
        this.reportRepository = reportRepository;
        this.pdfUtil = pdfUtil;
    }

    private List<ReimbursementDTO> getReimbursement(Page<Reimbursement> reimbursements) {

        return reimbursements.getContent().stream().map(reimbursement -> {
            List<ReimbursementAttachment> attachments = reimbursementAttachmentRepository.findByReimbursement(reimbursement);
            return reimbursementMapper.reimbursementToReimbursementDto(reimbursement, attachments);
        }).collect(Collectors.toList());
    }

    public Map<String, Object> findAll(Integer page, Integer limit, String dir, String sortColumn) {
        Pageable pageable = PaginationUtil.constructPageable(page, limit, dir, sortColumn);

        Page<Reimbursement> reimbursements = reimbursementRepository.findAll(pageable);

        List<ReimbursementDTO> data = getReimbursement(reimbursements);

        return PaginationUtil.constructMapReturn(data, reimbursements.getTotalElements(), reimbursements.getTotalPages(), pageable);
    }

    public Map<String, Object> findReimbursementByEmployee(Integer page, Integer limit, String dir, String sortColumn, Employee employee) {
        Pageable pageable = PaginationUtil.constructPageable(page, limit, dir, sortColumn);

        Page<Reimbursement> reimbursements = reimbursementRepository.findByEmployee(employee, pageable);

        List<ReimbursementDTO> data = getReimbursement(reimbursements);

        return PaginationUtil.constructMapReturn(data, reimbursements.getTotalElements(), reimbursements.getTotalPages(), pageable);
    }

    public boolean submitReimbursement(SubmitReimbursementDTO dto, Employee employee) {

        Reimbursement reimbursement = reimbursementMapper.submitReimbursementDtoToReimbursement(dto);
        reimbursement.setEmployee(employee);
        reimbursement.setStatus(StatusReimbursement.SUBMITTED);
        Reimbursement savedReimbursement = reimbursementRepository.save(reimbursement);

        dto.getAttachment().forEach(x -> {
            ReimbursementAttachment attachment = new ReimbursementAttachment();
            attachment.setName(x.getName());
            attachment.setUrl(x.getResponse().getUrl());
            attachment.setReimbursement(savedReimbursement);
            reimbursementAttachmentRepository.save(attachment);
        });

        slackUtil.sendMessage(String.format("Notification: %s just submit reimbursement", employee.getFullName()));

        return true;
    }

    public Map<String, Object> rejectReimbursement(String reimbursementId) {
        Reimbursement reimbursement = reimbursementRepository.findBySecureId(reimbursementId)
                .orElseThrow(ReimbursementNotFoundException::new);
        reimbursement.setStatus(StatusReimbursement.REJECTED);
        reimbursementRepository.save(reimbursement);

        int page = 1;
        int limit = 10;
        Pageable pageable = PaginationUtil.constructPageable(page, limit, null, null);
        Page<Reimbursement> reimbursements = reimbursementRepository.findAll(pageable);
        List<ReimbursementDTO> data = getReimbursement(reimbursements);
        return PaginationUtil.constructMapReturn(data, reimbursements.getTotalElements(), reimbursements.getTotalPages(), pageable);
    }

    public Map<String, Object> acceptReimbursement(String reimbursementId) {
        Reimbursement reimbursement = reimbursementRepository.findBySecureId(reimbursementId)
                .orElseThrow(ReimbursementNotFoundException::new);
        reimbursement.setStatus(StatusReimbursement.ON_PROGRESS);
        reimbursementRepository.save(reimbursement);

        int page = 1;
        int limit = 10;
        Pageable pageable = PaginationUtil.constructPageable(page, limit, null, null);
        Page<Reimbursement> reimbursements = reimbursementRepository.findAll(pageable);
        List<ReimbursementDTO> data = getReimbursement(reimbursements);
        return PaginationUtil.constructMapReturn(data, reimbursements.getTotalElements(), reimbursements.getTotalPages(), pageable);
    }

    public Map<String, Object> completeReimbursement(String reimbursementId) {
        Reimbursement reimbursement = reimbursementRepository.findBySecureId(reimbursementId)
                .orElseThrow(ReimbursementNotFoundException::new);
        reimbursement.setStatus(StatusReimbursement.COMPLETED);
        reimbursementRepository.save(reimbursement);

        int page = 1;
        int limit = 10;
        Pageable pageable = PaginationUtil.constructPageable(page, limit, null, null);
        Page<Reimbursement> reimbursements = reimbursementRepository.findAll(pageable);
        List<ReimbursementDTO> data = getReimbursement(reimbursements);
        return PaginationUtil.constructMapReturn(data, reimbursements.getTotalElements(), reimbursements.getTotalPages(), pageable);
    }

    public void getReportPerMonth(HttpServletResponse response, RequestReportPerMonthDTO requestDTO) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("periode", String.format("%s %s", requestDTO.getMonth(), requestDTO.getYear()));
            params.put("logo", "http://localhost:9000/logo/logoExpenses.png");

            String yearMonth = String.format("%s%s", requestDTO.getYear(), requestDTO.getMonthNumber());
            List<ReportPerMonthDTO> list = reportRepository.getReportPerMonth(yearMonth);

            String fileName = String.format("REPORT %s %s.pdf", requestDTO.getMonth().toUpperCase(), requestDTO.getYear());
            pdfUtil.generatePdfFileJasper(params, list, fileName, "ReportReimbursePerMonth.jasper");

            File file = new File("data/" + fileName);
            InputStream inputStream = new FileInputStream(file);

            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(inputStream, response.getOutputStream());

            inputStream.close();
            FileUtils.deleteQuietly(file);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}
