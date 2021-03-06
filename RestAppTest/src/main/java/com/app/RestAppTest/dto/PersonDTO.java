package com.app.RestAppTest.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PersonDTO {

    @NotEmpty(message = "Empty name!")
    @Size(min = 2, max = 30, message = "name cane be from 2 to 30")
    private String name;

    @Min(value = 0, message = "Age can be more than 0")
    private int age;

    @NotEmpty(message = "Empty message")
    @Email(message = "Incorrect email")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
