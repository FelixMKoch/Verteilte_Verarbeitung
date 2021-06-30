package de.throsenheim.presentation.controller;

import com.google.gson.Gson;
import de.throsenheim.application.connections.RuleConnection;
import de.throsenheim.application.connections.SensorConnection;
import de.throsenheim.common.gson.GsonFactory;
import de.throsenheim.domain.models.Rule;
import de.throsenheim.domain.models.Sensor;
import de.throsenheim.presentation.dto.RuleDto;
import de.throsenheim.presentation.dto.SensorDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SensorController.class)
class SensorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    SensorConnection sensorConnection;

    ModelMapper modelMapper = new ModelMapper();


    static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.
            APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Test
    void getAllSensorShouldReturnOK() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/sensors");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals("application/json", result.getResponse().getContentType());
        assertEquals(200 ,result.getResponse().getStatus());
    }


    @Test
    void postSensorWithoutBodyShouldReturnBadRequest() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.post("/sensors");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(null, result.getResponse().getContentType());
        assertEquals(400 ,result.getResponse().getStatus());
    }


    @Test
    void shoudlRunThroughWhenDelete() throws Exception{
        when(sensorConnection.getSensorFromId(42)).thenReturn(new Sensor());
        RequestBuilder request = MockMvcRequestBuilders.delete("/sensors/42");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void shoudlReturnBadRequestWhenDeleteWithNoNumber() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.delete("/sensors/aaa");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    void shouldGiveSensorWhenLookingForCertainId() throws Exception{
        when(sensorConnection.getSensorFromId(42)).thenReturn(new Sensor());
        RequestBuilder request = MockMvcRequestBuilders.get("/sensors/42");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void shouldReturnBadRequestWhenGetWithNoId() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/sensors/aa");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }


    @Test
    void shouldUpdateAndReturnCreatedWhenPutSensorDto() throws Exception{
        Sensor sensor = modelMapper.map(new SensorDto(11,"a","a"), Sensor.class);
        when(sensorConnection.sensorPut(sensor, 42)).thenReturn(new Sensor());
        Gson gson = GsonFactory.getGson();
        SensorDto ruleDto = new SensorDto(11,"a","a");
        mvc.perform(put("/sensors/42").contentType(APPLICATION_JSON_UTF8)
                .content(gson.toJson(ruleDto))).andExpect(status().isCreated());
    }


    @Test
    void shouldReturnBadRequestWhenInvalidIDInPUTMapping() throws Exception{
        Sensor sensor = modelMapper.map(new SensorDto(11,"a","a"), Sensor.class);
        when(sensorConnection.sensorPut(sensor, 42)).thenReturn(new Sensor());
        Gson gson = GsonFactory.getGson();
        SensorDto ruleDto = new SensorDto(11,"a","a");
        mvc.perform(put("/sensors/aa").contentType(APPLICATION_JSON_UTF8)
                .content(gson.toJson(ruleDto))).andExpect(status().isBadRequest());
    }


    @Test
    void shouldCreateWhenPostingSensor() throws Exception{
        Sensor sensor = modelMapper.map(new SensorDto(11,"a","a"), Sensor.class);
        when(sensorConnection.addSensor(sensor)).thenReturn(new Sensor());
        Gson gson = GsonFactory.getGson();
        SensorDto ruleDto = new SensorDto(11,"a","a");
        mvc.perform(post("/sensors").contentType(APPLICATION_JSON_UTF8)
                .content(gson.toJson(ruleDto))).andExpect(status().isCreated());
    }
}