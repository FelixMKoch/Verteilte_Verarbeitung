package de.throsenheim.application.connections;

import de.throsenheim.domain.models.Rule;
import de.throsenheim.persistence.repositories.AktorsRepository;
import de.throsenheim.persistence.repositories.RulesRepository;
import de.throsenheim.persistence.repositories.SensorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@WebMvcTest(RuleConnection.class)
class RuleConnectionTest {

    @MockBean
    RulesRepository rulesRepository;

    @MockBean
    AktorsRepository aktorsRepository;

    @MockBean
    SensorRepository sensorRepository;

    @Autowired
    private RuleConnection ruleConnection;

    @Test
    void shouldGetListOfRules() throws Exception {
        Rule r1 = new Rule();
        Rule r2 = new Rule();

        List<Rule> ruleList = new ArrayList<>();

        ruleList.add(r1);
        ruleList.add(r2);

        when(rulesRepository.findAll()).thenReturn(ruleList);
        assertEquals(ruleList, ruleConnection.getAllRules());
    }


    @Test
    void shouldGiveBackACertainRuleWithId() throws Exception {
        Rule rule = new Rule();
        rule.setId(42);
        when(rulesRepository.findById(42)).thenReturn(Optional.of(rule));

        assertEquals(rule, ruleConnection.getById(42));
    }


    @Test
    void ruleAddSHouldRunThroughIfOthersAreAvailable() throws Exception{
        Rule rule = new Rule();
        rule.setId(42);
        rule.setName("a");
        rule.setAktorId(11);
        rule.setSensorid(22);
        when(rulesRepository.getByName("a")).thenReturn(null);
        when(aktorsRepository.existsById(11)).thenReturn(true);
        when(sensorRepository.existsById(22)).thenReturn(true);

        assertEquals(rule, ruleConnection.addRule(rule));
    }
}