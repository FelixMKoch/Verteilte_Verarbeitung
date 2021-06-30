package de.throsenheim.application.connections;

import de.throsenheim.domain.models.Sensor;
import de.throsenheim.persistence.repositories.SensorRepository;
import de.throsenheim.presentation.controller.exceptions.IdAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Connecting the frontend of Sensor with the repositories
 */
@Service
public class SensorConnection {

    @Autowired
    private SensorRepository sensorRepository;

    /**
     * Connection between database and Controller to get Sensor with a certain ID
     * @param id id of the sensor you want to get
     * @return Sensor with this ID from the database
     */
    public Sensor getSensorFromId(Integer id){
        var sensorOptional = sensorRepository.findBySensorid(id);

        return sensorOptional.orElse(null);
    }

    /**
     * Used to get all the Sensors fro mthe database
     * @return A list of sensors from the database
     */
    public List<Sensor> getAllSensors(){
        List<Sensor> result = new ArrayList<>();
        result.addAll(sensorRepository.findAll());

        return result;
    }

    /**
     * "Deletes a sensor with a certain id
     * @param sensorId Id to be delted aka removalDate is set to now
     */
    public void deleteSensor(Integer sensorId){
        sensorRepository.setRemovalDate(sensorId);
    }

    /**
     * Adds a sensor to the database. If id already exists, throws expception
     * @param sensor sensor to be added
     */
    public Sensor addSensor(Sensor sensor){
        if(sensorRepository.findBySensorid(sensor.getSensorId()).isPresent()) throw new IdAlreadyExistsException();
        sensor.setDate(new Date());

        sensorRepository.save(sensor);

        return sensor;
    }

    /**
     * Persist a Sensor with a certain ID
     * @param sensor sensor you want to persist
     * @param sensorId the Id of this sensor
     * @return the Sensor that was saved
     */
    public Sensor sensorPut(Sensor sensor, Integer sensorId){
        sensor.setDate(new Date());
        sensor.setSensorId(sensorId);
        sensorRepository.save(sensor);

        return sensor;
    }
}
