package com.pradip.authservice.userauthnauthzservice.repositories;

import com.pradip.authservice.userauthnauthzservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findByEmail(String email);
}
