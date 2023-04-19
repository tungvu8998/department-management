package com.vti.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountUpdateForm {
    private int id;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private int departmentId;
}
