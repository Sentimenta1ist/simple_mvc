package com.WetherRestAPI.utils.validators;

import com.WetherRestAPI.dto.MeasurementDTO;
import com.WetherRestAPI.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;
        System.out.println(measurementDTO.getSensor().getName());

        if(sensorsService.findByName(measurementDTO.getSensor().getName()).isEmpty()){
            errors.rejectValue("sensor", "", "Sensor with this name is absent.");
        }
    }
}
