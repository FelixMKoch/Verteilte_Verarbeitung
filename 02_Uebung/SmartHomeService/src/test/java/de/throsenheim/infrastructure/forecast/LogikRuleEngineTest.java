package de.throsenheim.infrastructure.forecast;

import de.throsenheim.domain.models.Aktor;
import de.throsenheim.domain.models.Rule;
import de.throsenheim.domain.models.SensorData;
import de.throsenheim.infrastructure.forecast.LogikRuleEngine;
import de.throsenheim.persistence.repositories.AktorsRepository;
import de.throsenheim.persistence.repositories.RulesRepository;
import de.throsenheim.persistence.repositories.SensorDataRepository;
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
@WebMvcTest(LogikRuleEngine.class)
class LogikRuleEngineTest {


    @Autowired
    private LogikRuleEngine logikRuleEngine;

    @MockBean
    private RulesRepository rulesRepository;

    @MockBean
    private SensorDataRepository sensorDataRepository;

    @MockBean
    private AktorsRepository aktorsRepository;

    @Test
    void shouldRunThroughTheWholeRuleEngine(){

        Rule r = new Rule();
        r.setThreshhold(20);
        r.setSensorid(11);
        r.setAktorId(11);
        r.setName("a");
        List<Rule> rulesList = new ArrayList<>();
        rulesList.add(r);

        Aktor aktor = new Aktor();
        aktor.setId(11);
        aktor.setName("a");

        SensorData sensorData = new SensorData();
        sensorData.setCurrentValue(10);
        sensorData.setSensorid(11);

        List<SensorData> sensorDataList = new ArrayList<>();
        sensorDataList.add(sensorData);

        when(rulesRepository.findAll()).thenReturn(rulesList);
        when(aktorsRepository.findById(11)).thenReturn(Optional.of(aktor));
        when(sensorDataRepository.getAllWithSensorId(11)).thenReturn(sensorDataList);


        logikRuleEngine.startEngine(true);

        assertTrue(true);

    }

    @Test
    void shouldJustTryTOgetInformationIfSunnyOrNot() {

        logikRuleEngine.isSunny();
        assertTrue(true);

    }
}