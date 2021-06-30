package de.throsenheim.application.connections;

import de.throsenheim.domain.models.Sensor;
import de.throsenheim.persistence.repositories.SensorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@WebMvcTest(SensorConnection.class)
class SensorConnectionTest {

    @Autowired
    private SensorConnection sensorConnection;

    @MockBean
    private SensorRepository sensorRepository;

    @Test
    void getSensorFromId() {
        when(sensorRepository.findBySensorid(42)).thenReturn(Optional.of(new Sensor()));
        assertEquals(new Sensor(), sensorConnection.getSensorFromId(42));
    }

    @Test
    void getAllSensors() {
        Sensor sensor1 = new Sensor();
        Sensor sensor2 = new Sensor();

        List<Sensor> sensorList = new ArrayList<>();
        sensorList.add(sensor1);
        sensorList.add(sensor2);

        when(sensorRepository.findAll()).thenReturn(sensorList);

        assertEquals(sensorList, sensorConnection.getAllSensors());
    }

    @Test
    void deleteSensor() {
        sensorConnection.deleteSensor(42);
        assertTrue(true);
    }

}