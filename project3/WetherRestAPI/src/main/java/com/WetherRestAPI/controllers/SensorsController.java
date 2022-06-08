package com.WetherRestAPI.controllers;

import com.WetherRestAPI.dto.SensorDTO;
import com.WetherRestAPI.models.Sensor;
import com.WetherRestAPI.services.SensorsService;
import com.WetherRestAPI.utils.errorResponse.MeasurementErrorResponse;
import com.WetherRestAPI.utils.errorResponse.SensorErrorResponse;
import com.WetherRestAPI.utils.exceptions.SensorNotCreatedException;
import com.WetherRestAPI.utils.validators.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.WetherRestAPI.utils.Utils.getErrorString;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;
    private final SensorsService sensorsService;

    @Autowired
    public SensorsController(ModelMapper modelMapper, SensorValidator sensorValidator, SensorsService sensorsService) {
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
        this.sensorsService = sensorsService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> newSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult br) {
        sensorValidator.validate(sensorDTO, br);

        if (br.hasErrors()) {
            throw new SensorNotCreatedException(getErrorString(br));
        }

        sensorsService.save(convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }


    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }


    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                "sensor not created!",
                System.currentTimeMillis()
        );

        // in http answer - body(response) and status
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
