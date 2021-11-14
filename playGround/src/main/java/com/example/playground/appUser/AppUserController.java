package com.example.playground.appUser;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/")
public class AppUserController {

    /**
     * Accessible by all
     * @return page
     */
    @GetMapping
    public String index(){
        return "hello";
    }

    @GetMapping("/auth")
    public String getAuthenticatedUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication().getPrincipal().toString();
    }

    /**
     * Only accessible with role ADMIN
     * @return page
     */
    @GetMapping("/admin")
    public String getAdmin(){
        return "admin";
    }

    /**
     * Only accessible with role USER
     * @return page
     */
    @GetMapping("/user")
    public String getUsers() {
        return "user";
    }

}
