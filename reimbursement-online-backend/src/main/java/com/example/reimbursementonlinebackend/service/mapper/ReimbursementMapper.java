package com.example.reimbursementonlinebackend.service.mapper;

import com.example.reimbursementonlinebackend.domain.Reimbursement;
import com.example.reimbursementonlinebackend.domain.ReimbursementAttachment;
import com.example.reimbursementonlinebackend.service.dto.ReimbursementDTO;
import com.example.reimbursementonlinebackend.service.dto.RequestDownloadDTO;
import com.example.reimbursementonlinebackend.service.dto.SubmitReimbursementDTO;
import com.example.reimbursementonlinebackend.util.InstantsUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {InstantsUtil.class}, componentModel = "spring")
public interface ReimbursementMapper {

    @Mapping(source = "reimbursement.datePurchase", target = "datePurchase", qualifiedByName = {"InstantUtil", "ConvertInstantToEpochSecond"})
    @Mapping(source = "reimbursement.creationDate", target = "creationDate", qualifiedByName = {"InstantUtil", "ConvertInstantToEpochSecond"})
    @Mapping(target = "attachments", qualifiedByName = "convertAttachment")
    @Mapping(source = "reimbursement.employee.fullName", target = "employeeName")
    ReimbursementDTO reimbursementToReimbursementDto(Reimbursement reimbursement, List<ReimbursementAttachment> attachments);

    Reimbursement submitReimbursementDtoToReimbursement(SubmitReimbursementDTO dto);

    @Named("convertAttachment")
    default List<RequestDownloadDTO> convertAttachment(List<ReimbursementAttachment> attachments) {
        return attachments.stream()
                .map(attachment -> {
                    String[] split = attachment.getUrl().split("/reimbursement.file/");
                    RequestDownloadDTO dto = new RequestDownloadDTO();
                    dto.setFileName(split[1]);
                    dto.setBucket("reimbursement.file");
                    dto.setNameOriginal(attachment.getName());
                    return dto;
                }).collect(Collectors.toList());
    }


}
