package com.pradip.authservice.userauthnauthzservice.dto;

import com.pradip.authservice.userauthnauthzservice.models.Role;
import com.pradip.authservice.userauthnauthzservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {

    private String fullName;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();

    public static UserDto from(User user) {
        UserDto  userDto = new UserDto();
        userDto.fullName = user.getFullName();
        userDto.email = user.getEmail();
        userDto.roles = user.getRoles();
        return userDto;
    }
}
