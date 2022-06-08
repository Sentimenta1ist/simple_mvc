package com.WetherRestAPI.dto;

import javax.validation.constraints.NotEmpty;

public class SensorDTO {

    @NotEmpty(message = "Empty name!")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
