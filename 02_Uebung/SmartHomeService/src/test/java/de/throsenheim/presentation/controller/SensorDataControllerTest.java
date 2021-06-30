package de.throsenheim.presentation.controller;

import com.google.gson.Gson;
import de.throsenheim.application.connections.RuleConnection;
import de.throsenheim.application.connections.SensorDataConnection;
import de.throsenheim.common.gson.GsonFactory;
import de.throsenheim.presentation.dto.AktorDto;
import de.throsenheim.presentation.dto.SensorDataDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SensorDataController.class)
class SensorDataControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    SensorDataConnection sensorDataConnection;

    static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.
            APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));


    @Test
    void postAktorWithoutBodyShouldReturnBadRequest() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.post("/sensors/1");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(null, result.getResponse().getContentType());
        assertEquals(400 ,result.getResponse().getStatus());
    }


    @Test
    void shouldPostSensorDataAndGiveBackCreated() throws Exception{
        Gson gson = GsonFactory.getGson();
        SensorDataDto aktorDto = new SensorDataDto(42L, 1, "a");
        mvc.perform(post("/sensors/42").contentType(APPLICATION_JSON_UTF8)
                .content(gson.toJson(aktorDto))).andExpect(status().isCreated());
    }


    @Test
    void PostSensorDataWithNotNumericIDShouldReturnBadRequest() throws Exception{
        Gson gson = GsonFactory.getGson();
        SensorDataDto aktorDto = new SensorDataDto(42L, 1, "a");
        mvc.perform(post("/sensors/aa").contentType(APPLICATION_JSON_UTF8)
                .content(gson.toJson(aktorDto))).andExpect(status().isBadRequest());
    }
}