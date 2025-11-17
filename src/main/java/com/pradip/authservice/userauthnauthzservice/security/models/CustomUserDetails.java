package com.pradip.authservice.userauthnauthzservice.security.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pradip.authservice.userauthnauthzservice.models.Role;
import com.pradip.authservice.userauthnauthzservice.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@JsonSerialize
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    //User user;
    String username;
    String password;
    List<CustomRoles> authorities = new ArrayList<>();
    boolean accountNonExpired;
    boolean accountNonLocked;
    boolean credentialsNonExpired;
    boolean enabled;
    Long userId;

    public CustomUserDetails(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        for(Role role : user.getRoles()){
            this.authorities.add(new CustomRoles(role));
        }
        this.userId = user.getId();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // or map from your User entity if you store expiry
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // or map from your User entity if you track lock status
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // or map from DB if you want password expiry
    }

    @Override
    public boolean isEnabled() {
        return true; // or map from User.isActive flag
    }


}
