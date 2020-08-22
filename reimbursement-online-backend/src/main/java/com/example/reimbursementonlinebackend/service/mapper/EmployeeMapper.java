package com.example.reimbursementonlinebackend.service.mapper;

import com.example.reimbursementonlinebackend.domain.Employee;
import com.example.reimbursementonlinebackend.service.dto.ProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.username", target = "username")
    ProfileDTO employeeToProfileDto(Employee employee);
}
