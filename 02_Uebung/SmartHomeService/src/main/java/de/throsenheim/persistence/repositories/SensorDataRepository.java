package de.throsenheim.persistence.repositories;

import de.throsenheim.domain.models.SensorData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SensorDataRepository extends CrudRepository<SensorData, Integer> {

    /**
     * Get a list of all the SensorData from a Sensor with a certain ID
     * @param sensorId sensorId you are looking for
     * @return A List of sensorData all with the specific ID
     */
    @Query(value = "SELECT * FROM sensordata where sensorid = :sid",
    nativeQuery = true)
    List<SensorData> getAllWithSensorId(@Param("sid")Integer sensorId);

}
