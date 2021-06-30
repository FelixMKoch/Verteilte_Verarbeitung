package de.throsenheim.presentation.controller;

import com.google.gson.Gson;
import de.throsenheim.application.connections.AktorConnection;
import de.throsenheim.common.gson.GsonFactory;
import de.throsenheim.domain.models.Aktor;
import de.throsenheim.presentation.dto.AktorDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AktorController.class)
class AktorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    AktorConnection aktorConnection;

    static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.
            APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Test
    void getAllAktorsShouldReturnHttpOk() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/aktors");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals("application/json", result.getResponse().getContentType());
        assertEquals(200 ,result.getResponse().getStatus());
    }


    @Test
    void postAktorWithoutBodyShouldReturnBadRequest() throws Exception{
        RequestBuilder request = post("/aktors");
        MvcResult result = mvc.perform(request).andReturn();
        assertNull(result.getResponse().getContentType());
        assertEquals(400 ,result.getResponse().getStatus());
    }

    @Test
    void getASingleInstanceOfAnAktor() throws Exception{
        when(aktorConnection.getAktor(42)).thenReturn(new Aktor());
        RequestBuilder request = MockMvcRequestBuilders.get("/aktors/42");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals("application/json", result.getResponse().getContentType());
        assertEquals(200 ,result.getResponse().getStatus());
    }

    @Test
    void getNoInstanceOdAnAktor() throws Exception{
        when(aktorConnection.getAktor(42)).thenReturn(null);
        RequestBuilder request = MockMvcRequestBuilders.get("/aktors/42");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    void shouldGiveBadREquestForNotAnIntAsRequestParam() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/aktors/test");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    void shouldPostAktoAndGiveBackCreated() throws Exception{
        Gson gson = GsonFactory.getGson();
        AktorDto aktorDto = new AktorDto("a", "a", "a", 1);
        mvc.perform(post("/aktors").contentType(APPLICATION_JSON_UTF8)
            .content(gson.toJson(aktorDto))).andExpect(status().isCreated());
    }

}