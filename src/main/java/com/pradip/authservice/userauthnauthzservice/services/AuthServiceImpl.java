package com.pradip.authservice.userauthnauthzservice.services;


import com.pradip.authservice.userauthnauthzservice.dto.UserDto;
import com.pradip.authservice.userauthnauthzservice.dto.ValidateRequestTokenDto;
import com.pradip.authservice.userauthnauthzservice.exception.UserAlreadyExistException;
import com.pradip.authservice.userauthnauthzservice.exception.UserNotExistException;
import com.pradip.authservice.userauthnauthzservice.models.Session;
import com.pradip.authservice.userauthnauthzservice.models.SessionStatus;
import com.pradip.authservice.userauthnauthzservice.models.User;
import com.pradip.authservice.userauthnauthzservice.repositories.SessionRepository;
import com.pradip.authservice.userauthnauthzservice.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final SecretKey secretKey;
    private final long expirationTime;
    PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, SessionRepository sessionRepository,
                           @Value("${jwt.secret}") String secret,
                           @Value("${jwt.expiration}") Long expiration,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationTime = expiration;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<UserDto> login(String email, String password) throws UserNotExistException {

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotExistException("User with email " + email + " does not exist");

        }
        boolean res = passwordEncoder.matches(password, user.get().getPassword());
        if(!res)
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else {
            Map<String, Object> claims = new HashMap<>();
            claims.put("roles", user.get().getRoles());
            claims.put("email", user.get().getEmail());

            String token = Jwts.builder()
                    //.header().add("alag", "HMAC256").and()
                    .claims(claims)
                    .subject(user.get().getFullName())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + expirationTime))
                    .signWith(secretKey)
                    .compact();




            //String token = UUID.randomUUID().toString().substring(0, 20);
            Session session = new Session();
            session.setToken(token);
            session.setUser(user.get());
            session.setStatus(SessionStatus.ACTIVE);
            sessionRepository.save(session);
            UserDto userDto = UserDto.from(user.get());

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Authorization", "Bearer " + token);
            return new ResponseEntity<>(userDto, headers, HttpStatus.OK);
        }

    }

    @Override
    public User signUp(String fullName, String email, String password) throws UserAlreadyExistException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isPresent()) {
            throw new UserAlreadyExistException("User already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fullName);

        userRepository.save(user);

        return user;
    }

    @Override
    public Optional<User> validate(String token, Long userId) {

        Optional<Session> session = sessionRepository.findByTokenAndUser_Id(token, userId);
        //if session is empty
        if(session.isEmpty())
        {
            return Optional.empty();
        }
        // if session is not active
        if(!session.get().getStatus().equals(SessionStatus.ACTIVE))
        {
            return Optional.empty();
        }


        return userRepository.findById(userId);
    }
}
