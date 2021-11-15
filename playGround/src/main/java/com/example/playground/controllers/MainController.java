package com.example.playground.controllers;

import com.example.playground.services.UserService;
import com.example.playground.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class MainController {

    UserService service;

    /**
     * Accessible by all
     * @return page
     */
    @GetMapping
    public String index(){
        return "hello";
    }

    @GetMapping("register")
    public String register(){
        return "register";
    }

    @PostMapping("register")
    public void registerUser(@RequestBody User user){
        service.save(user);
    }

    /**
     * Only accessible with role ADMIN
     * @return page
     */
    @GetMapping("admin")
    public String getAdmin(){
        return "admin";
    }

    /**
     * Only accessible with role USER
     * @return page
     */
    @GetMapping("user")
    public String getUsers() {
        return "user";
    }

}
