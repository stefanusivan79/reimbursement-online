package com.example.reimbursementonlinebackend.service;

import com.example.reimbursementonlinebackend.domain.Employee;
import com.example.reimbursementonlinebackend.domain.Reimbursement;
import com.example.reimbursementonlinebackend.domain.ReimbursementAttachment;
import com.example.reimbursementonlinebackend.enums.StatusReimbursement;
import com.example.reimbursementonlinebackend.exception.ReimbursementNotFoundException;
import com.example.reimbursementonlinebackend.repository.ReimbursementAttachmentRepository;
import com.example.reimbursementonlinebackend.repository.ReimbursementRepository;
import com.example.reimbursementonlinebackend.service.dto.ReimbursementDTO;
import com.example.reimbursementonlinebackend.service.dto.SubmitReimbursementDTO;
import com.example.reimbursementonlinebackend.service.mapper.ReimbursementMapper;
import com.example.reimbursementonlinebackend.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReimbursementService {

    private ReimbursementRepository reimbursementRepository;
    private ReimbursementMapper reimbursementMapper;
    private ReimbursementAttachmentRepository reimbursementAttachmentRepository;

    public ReimbursementService(ReimbursementRepository reimbursementRepository, ReimbursementMapper reimbursementMapper,
                                ReimbursementAttachmentRepository reimbursementAttachmentRepository) {
        this.reimbursementRepository = reimbursementRepository;
        this.reimbursementMapper = reimbursementMapper;
        this.reimbursementAttachmentRepository = reimbursementAttachmentRepository;
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


}
