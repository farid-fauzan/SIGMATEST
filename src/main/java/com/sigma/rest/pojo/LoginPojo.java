package com.sigma.rest.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class LoginPojo {
    @NotBlank(message = "Email harus diisi")
    @Email(message = "Email tidak valid")
    private String email;

    @NotBlank(message = "Password harus diisi")
    private String password;
}
