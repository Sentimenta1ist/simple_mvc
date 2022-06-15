package com.example.FirstSpringSecurityApp.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

public class AuthenticationDTO {
    @NotEmpty(message = "Empty name!")
    @Column(name = "username")
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
