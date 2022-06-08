package com.WetherRestAPI.utils.validators;


import com.WetherRestAPI.dto.SensorDTO;
import com.WetherRestAPI.models.Sensor;
import com.WetherRestAPI.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensor = (SensorDTO) target;

        if(sensorsService.findByName(sensor.getName()).isPresent()){
            errors.rejectValue("name", "", "This name already taken.");
        }
    }
}
