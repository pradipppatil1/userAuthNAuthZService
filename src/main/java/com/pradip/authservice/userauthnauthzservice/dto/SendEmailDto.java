package com.pradip.authservice.userauthnauthzservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailDto {
    private String email;
    private String emailSubject;
    private String emailBody;
}
