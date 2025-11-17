package com.pradip.authservice.userauthnauthzservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateRequestTokenDto {

    private Long userId;
    private String token;
}
