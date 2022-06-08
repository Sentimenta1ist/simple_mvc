package com.WetherRestAPI.dto;

import javax.validation.constraints.*;

public class MeasurementDTO {

    @NotNull(message = "value should be defined")
    @Min(value = -100, message = "Age should be greater than -100")
    @Max(value = 100, message = "Age should be less than 100")
    private Float value;

    @NotNull(message = "raining should be defined")
    private Boolean raining;

    @NotNull(message = "Sensor should be defined!")
    private SensorDTO sensor;

    public Float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
