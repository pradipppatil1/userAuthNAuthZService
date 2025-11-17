package com.pradip.authservice.userauthnauthzservice.security.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pradip.authservice.userauthnauthzservice.models.Role;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@JsonSerialize
@NoArgsConstructor
public class CustomRoles implements GrantedAuthority {
    //Role role;
    String authority;

    public CustomRoles(Role role) {
        this.authority = role.getRole();
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
