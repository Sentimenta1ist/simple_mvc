package com.example.FirstSpringSecurityApp.controllers;

import com.example.FirstSpringSecurityApp.security.PersonDetails;
import com.example.FirstSpringSecurityApp.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    private final AdminService adminService;

    @Autowired
    public HelloController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/showUser")
    @ResponseBody
    public String showUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails);
        return personDetails.getUsername();
    }

    @GetMapping("/admin")
    public String admin(){
        adminService.doAdminShit();
        return "admin";
    }
}
