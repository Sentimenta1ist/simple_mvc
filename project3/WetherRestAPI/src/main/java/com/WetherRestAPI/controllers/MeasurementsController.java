package com.WetherRestAPI.controllers;

import com.WetherRestAPI.dto.MeasurementDTO;
import com.WetherRestAPI.models.Measurement;
import com.WetherRestAPI.services.MeasurementsService;
import com.WetherRestAPI.utils.errorResponse.MeasurementErrorResponse;
import com.WetherRestAPI.utils.exceptions.MeasurementNotAdded;
import com.WetherRestAPI.utils.validators.MeasurementValidator;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.WetherRestAPI.utils.Utils.getErrorString;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementValidator measurementValidator;
    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;

    public MeasurementsController(MeasurementValidator measurementValidator, MeasurementsService measurementsService, ModelMapper modelMapper) {
        this.measurementValidator = measurementValidator;
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> newSensor(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new MeasurementNotAdded(getErrorString(bindingResult));
        }

        measurementValidator.validate(measurementDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new MeasurementNotAdded(getErrorString(bindingResult));
        }

        measurementsService.save(convertToMeasurement(measurementDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }



    @GetMapping("/rainy")
    public String getRainyDays() {
        return "hello" + measurementsService.getAllRainyDays();
    }

    @GetMapping("/all")
    public List<MeasurementDTO> findAll() {
        return measurementsService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }


    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotAdded e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                "measurement not added!",
                System.currentTimeMillis()
        );

        // in http answer - body(response) and status
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
