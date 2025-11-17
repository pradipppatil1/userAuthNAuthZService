package com.pradip.authservice.userauthnauthzservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Session extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    String token;
    private Date expiration;
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus status;
}
