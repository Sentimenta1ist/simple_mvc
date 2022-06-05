package com.example.FirstSpringSecurityApp.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @PreAuthorize("hasRole('ADMIN')")
    public void doAdminShit(){
        System.out.println("Admin >:");
    }
}
