package com.example.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("login")
    public String getLoginView() {
        return "login"; //same name as in resources/templates/ (without html)
    }

    @GetMapping("courses")
    public String getCourseView() {
        return "courses"; //same name as in resources/templates/ (without html)
    }
}
