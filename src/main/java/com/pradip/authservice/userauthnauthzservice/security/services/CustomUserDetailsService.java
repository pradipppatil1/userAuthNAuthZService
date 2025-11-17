package com.pradip.authservice.userauthnauthzservice.security.services;

import com.pradip.authservice.userauthnauthzservice.models.User;
import com.pradip.authservice.userauthnauthzservice.repositories.UserRepository;
import com.pradip.authservice.userauthnauthzservice.security.models.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);

        if(user.isEmpty())
        {
            throw new UsernameNotFoundException(username +" not found");
        }



        return new CustomUserDetails(user.get());
    }
}
