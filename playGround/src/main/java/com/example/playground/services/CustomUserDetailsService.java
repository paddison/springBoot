package com.example.playground.services;

import com.example.playground.models.CustomUserDetails;
import com.example.playground.models.Role;
import com.example.playground.models.User;
import com.example.playground.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    UserRepository repository;
    private static boolean IN_TEST_MODE = true;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails;
        if (!IN_TEST_MODE){
            Optional<User> user =  repository.getUserByEmail(username);
            user.orElseThrow(() -> new UsernameNotFoundException("No user found for: " + username));
            userDetails = user.map(CustomUserDetails::new).get();
        }else {
            userDetails = new CustomUserDetails(new User("123", "123", "123", "{noop}123", Role.ADMIN, true));
        }
        return userDetails;
    }
}
