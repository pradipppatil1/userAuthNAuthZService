package com.pradip.authservice.userauthnauthzservice.models;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class User extends BaseModel {

    private String fullName;
    private String password;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles =  new HashSet<Role>();

}
