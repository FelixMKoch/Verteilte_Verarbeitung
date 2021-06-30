package de.throsenheim.persistence.repositories;

import de.throsenheim.domain.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {

    /**
     * Set the removal Date to right now
     * @param sensorid id of the sensor the removal date is to be set
     */
    @Query(value = "UPDATE sensors " +
            "SET removaldate = GETDATE() " +
            "WHERE sensorid = :sensorid",
    nativeQuery = true)
    void setRemovalDate(@Param("sensorid") Integer sensorid);


    /**
     * Find sensor by its sensorId
     * @param sensorId Sensorid of which this is filtered
     * @return A sensor with that certain SensorId
     */
    @Query(value = "select * " +
            "from sensors " +
            "WHERE sensorid = :sensorid",
            nativeQuery = true)
    Optional<Sensor> findBySensorid(@Param("sensorid") Integer sensorId);

}
