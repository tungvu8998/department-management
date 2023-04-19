package com.vti.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {
    private InternalError id;
    private String role;
    private String username;
    private String fullName;
    private String department_name;
}
