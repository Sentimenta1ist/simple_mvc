package com.WetherRestAPI.services;

import com.WetherRestAPI.models.Measurement;
import com.WetherRestAPI.repositories.MeasurementsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final SensorsService sensorsService;
    private final MeasurementsRepository measurementsRepository;

    public MeasurementsService(SensorsService sensorsService, MeasurementsRepository measurementsRepository) {
        this.sensorsService = sensorsService;
        this.measurementsRepository = measurementsRepository;
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurement(measurement);

        measurementsRepository.save(measurement);
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setSensor(sensorsService.findByName(measurement.getSensor().getName()).get());
        measurement.setCurrentTime(LocalDateTime.now());
    }

    public int getAllRainyDays(){
        return measurementsRepository.countAllRainyDays().size();
    }

    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }
}
