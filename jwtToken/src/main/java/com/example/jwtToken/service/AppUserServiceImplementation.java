package com.example.jwtToken.service;

import com.example.jwtToken.domain.AppUser;
import com.example.jwtToken.domain.Role;
import com.example.jwtToken.repository.AppUserRepository;
import com.example.jwtToken.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j // creates logging
public class AppUserServiceImplementation implements AppUserService, UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String USER_NOT_FOUND_MSG = "User %s not found in database";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) {
            log.error(String.format(USER_NOT_FOUND_MSG, username));
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username));
        } else {
            log.info("User {} found in database", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach((role) -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("Saving new User {} to database", appUser.getUsername());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new Role {} to database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToAppUser(String username, String rolename) {
        // in the real world you'd have some validation etc.
        log.info("Adding role {} to user {}", rolename, username);

        AppUser appUser = appUserRepository.findByUsername(username);
        Role role = roleRepository.findByName(rolename);
        // since we have @Transactional it's will be automatically save user in database if object changes
        appUser.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Fetching user {}", username);

        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> getAllAppUsers() {
        log.info("Fetching all users");
        return appUserRepository.findAll();
    }

}
