package com.pradip.authservice.userauthnauthzservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pradip.authservice.userauthnauthzservice.dto.LoginDto;
import com.pradip.authservice.userauthnauthzservice.dto.UserDto;
import com.pradip.authservice.userauthnauthzservice.dto.ValidateRequestTokenDto;
import com.pradip.authservice.userauthnauthzservice.dto.ValidateResponseTokenDto;
import com.pradip.authservice.userauthnauthzservice.exception.UserAlreadyExistException;
import com.pradip.authservice.userauthnauthzservice.exception.UserNotExistException;
import com.pradip.authservice.userauthnauthzservice.models.SessionStatus;
import com.pradip.authservice.userauthnauthzservice.models.User;
import com.pradip.authservice.userauthnauthzservice.services.AuthServiceImpl;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController

public class AuthController {

    AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto user) throws UserNotExistException {

       return authService.login(user.getEmail(), user.getPassword());

    }

    @PostMapping("/auth/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody UserDto user) throws UserAlreadyExistException, JsonProcessingException {
        User userEntity = new User();
        userEntity = authService.signUp(user.getFullName(),user.getEmail(), user.getPassword());
       UserDto userDto = UserDto.from(userEntity);

        return new  ResponseEntity<>(userDto, HttpStatus.OK);

    }

    @PostMapping("/auth/validate")
    public ResponseEntity<ValidateResponseTokenDto> validate(@RequestBody ValidateRequestTokenDto tokenDto)  {

        if(tokenDto.getToken() == null || tokenDto.getUserId() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Optional<User> user = authService.validate(tokenDto.getToken(), tokenDto.getUserId());
        if(user.isEmpty())
        {
            ValidateResponseTokenDto validateResponseTokenDto = new ValidateResponseTokenDto();
            validateResponseTokenDto.setSessionStatus(SessionStatus.ACTIVE);
            return new ResponseEntity<>(validateResponseTokenDto,HttpStatus.UNAUTHORIZED);
        }
        UserDto userDto = UserDto.from(user.get());
        ValidateResponseTokenDto validateResponseTokenDto = new ValidateResponseTokenDto();
        validateResponseTokenDto.setUserDto(userDto);
        validateResponseTokenDto.setSessionStatus(SessionStatus.ACTIVE);

        return new ResponseEntity<>(validateResponseTokenDto, HttpStatus.OK);
    }

    @GetMapping("/auth/logout")
    public boolean logout(@RequestHeader("authZ-token") String token) {
        return token != null && !token.isEmpty();
    }

}
