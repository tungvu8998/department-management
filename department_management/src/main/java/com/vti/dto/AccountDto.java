package com.vti.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
public class AccountDto extends RepresentationModel<AccountDto> {

    private int id;
    private String username;
    private String fullName;
    private String role;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String departmentName;
//    departmentName = department.name
}

