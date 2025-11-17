package com.pradip.authservice.userauthnauthzservice.dto;

import com.pradip.authservice.userauthnauthzservice.models.SessionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateResponseTokenDto {
    private SessionStatus sessionStatus;
    private UserDto userDto;
}
