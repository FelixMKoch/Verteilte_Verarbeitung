package de.throsenheim.presentation.controller;

import com.google.gson.Gson;
import de.throsenheim.application.connections.AktorConnection;
import de.throsenheim.application.connections.RuleConnection;
import de.throsenheim.common.gson.GsonFactory;
import de.throsenheim.domain.models.Rule;
import de.throsenheim.presentation.dto.AktorDto;
import de.throsenheim.presentation.dto.RuleDto;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RulesController.class)
class RulesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    RuleConnection ruleConnection;

   static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.
            APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Test
    void getAllRulesShouldReturnOK() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/rules");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals("application/json", result.getResponse().getContentType());
        assertEquals(200 ,result.getResponse().getStatus());
    }


    @Test
    void postRuleWithoutBodyShouldReturnBadRequest() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.post("/rules");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(null, result.getResponse().getContentType());
        assertEquals(400 ,result.getResponse().getStatus());
    }


    @Test
    void getRulesShouldReturnACertainRule() throws Exception{
        when(ruleConnection.getById(42)).thenReturn(new Rule());
        RequestBuilder request = MockMvcRequestBuilders.get("/rules/42");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(200 ,result.getResponse().getStatus());
    }


    @Test
    void shoudlReturnBadRequestWhenSearchingForRuleWithNOtAnInt() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/rules/aaa");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals(400 ,result.getResponse().getStatus());
    }


    @Test
    void shouldPostWrongRuleAndGiveBackNOtFound() throws Exception{
        Gson gson = GsonFactory.getGson();
        RuleDto ruleDto = new RuleDto("a", 1, 1, 1);
        mvc.perform(post("/aktors").contentType(APPLICATION_JSON_UTF8)
                .content(gson.toJson(ruleDto))).andExpect(status().isNotFound());
    }

}