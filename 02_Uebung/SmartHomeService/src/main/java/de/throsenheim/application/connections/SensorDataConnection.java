package de.throsenheim.application.connections;

import de.throsenheim.domain.models.SensorData;
import de.throsenheim.persistence.repositories.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Connecting the frontend of SensorData with the repositories
 */
@Service
public class SensorDataConnection {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    /**
     * Saves a SensorData into the database
     * @param sensorData Data to be saved
     * @return SensorData that was saved
     */
    public SensorData save(SensorData sensorData){

        sensorDataRepository.save(sensorData);

        return sensorData;

    }
}
