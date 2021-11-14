package com.example.jwtToken.service;

import com.example.jwtToken.domain.AppUser;
import com.example.jwtToken.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Defines all the methods to manage AppUsers
 */
@Service
public interface AppUserService {

    AppUser saveUser(AppUser appUser);
    Role saveRole(Role role);
    void addRoleToAppUser(String username, String rolename);
    AppUser getUser(String username);
    List<AppUser> getAllAppUsers(); // it's important to use pagination for this

}
