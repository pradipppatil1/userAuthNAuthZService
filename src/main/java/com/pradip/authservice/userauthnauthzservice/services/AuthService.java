package com.pradip.authservice.userauthnauthzservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pradip.authservice.userauthnauthzservice.dto.UserDto;
import com.pradip.authservice.userauthnauthzservice.dto.ValidateRequestTokenDto;
import com.pradip.authservice.userauthnauthzservice.exception.UserAlreadyExistException;
import com.pradip.authservice.userauthnauthzservice.exception.UserNotExistException;
import com.pradip.authservice.userauthnauthzservice.models.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


public interface AuthService {

    public ResponseEntity<UserDto> login(String email, String password) throws UserNotExistException;

    public User signUp(String fullName, String email, String password) throws UserAlreadyExistException, JsonProcessingException;

    public Optional<User> validate(String token, Long userId) throws UserNotExistException;
}
