package com.example.reimbursementonlinebackend.service.mapper;

import com.example.reimbursementonlinebackend.domain.Reimbursement;
import com.example.reimbursementonlinebackend.service.dto.ReimbursementDTO;
import com.example.reimbursementonlinebackend.service.dto.SubmitReimbursementDTO;
import com.example.reimbursementonlinebackend.util.InstantsUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {InstantsUtil.class}, componentModel = "spring")
public interface ReimbursementMapper {

    @Mapping(source = "datePurchase", target = "datePurchase", qualifiedByName = {"InstantUtil", "ConvertInstantToEpochSecond"})
    @Mapping(source = "creationDate", target = "creationDate", qualifiedByName = {"InstantUtil", "ConvertInstantToEpochSecond"})
    ReimbursementDTO reimbursementToReimbursementDto(Reimbursement reimbursement);

    Reimbursement submitReimbursementDtoToReimbursement(SubmitReimbursementDTO dto);

}
