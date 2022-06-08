package com.WetherRestAPI.repositories;

import com.WetherRestAPI.models.Measurement;
import com.WetherRestAPI.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Integer> {

    Optional<Sensor> findByName(String name);


}
