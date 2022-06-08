package com.WetherRestAPI.repositories;

import com.WetherRestAPI.models.Measurement;
import com.WetherRestAPI.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {

    @Query(value =
            "select" +
                    " date_trunc('day', curr_time)" +
                    " from measurement" +
                    " where raining = true" +
                    " group by 1",
            nativeQuery = true)
    List<Object> countAllRainyDays();
}
