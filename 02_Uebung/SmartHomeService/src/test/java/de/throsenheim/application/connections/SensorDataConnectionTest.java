package de.throsenheim.application.connections;

import de.throsenheim.domain.models.SensorData;
import de.throsenheim.persistence.repositories.SensorDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@WebMvcTest(SensorDataConnection.class)
class SensorDataConnectionTest {

    @MockBean
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private SensorDataConnection sensorDataConnection;

    @Test
    void save() throws Exception{
        when(sensorDataRepository.save(new SensorData())).thenReturn(new SensorData());
        assertEquals(new SensorData(), sensorDataConnection.save(new SensorData()));
    }
}