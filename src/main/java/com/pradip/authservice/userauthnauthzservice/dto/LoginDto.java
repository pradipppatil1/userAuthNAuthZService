package com.pradip.authservice.userauthnauthzservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    private String email;
    private String password;
}
