package com.example.playground.services;

import com.example.playground.models.Role;
import com.example.playground.models.User;
import com.example.playground.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    PasswordEncoder encoder;
    UserRepository repository;

    public User save(User user){
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.USER);
        user.setActive(true);
        User savedUser;
        try {
            savedUser =  repository.save(user);

        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            savedUser = null;
        }
        return savedUser;
    }
}
