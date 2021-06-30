package de.throsenheim.application.connections;

import de.throsenheim.application.connections.exceptions.DependenyDoesNotExistException;
import de.throsenheim.domain.models.Rule;
import de.throsenheim.persistence.repositories.AktorsRepository;
import de.throsenheim.persistence.repositories.RulesRepository;
import de.throsenheim.persistence.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Connecting the frontend of Rule with the repositories
 */
@Service
public class RuleConnection {

    @Autowired
    private RulesRepository rulesRepository;

    @Autowired
    private AktorsRepository aktorsRepository;

    @Autowired
    private SensorRepository sensorRepository;


    /**
     * Get all the rules of the Database
     * @return A List of rules
     */
    public List<Rule> getAllRules(){
        List<Rule> result = new ArrayList<>();
        rulesRepository.findAll().forEach(result::add);

        return result;
    }

    /**
     * Get an Rule by an ID
     * @param id ID of the rule you want to get
     * @return THe rule with the certain ID, or null if such a rule does not exist
     */
    public Rule getById(Integer id){
        var ruleOpt = rulesRepository.findById(id);

        return ruleOpt.orElse(null);
    }

    /**
     * Adds a rule to the database. Watch out: There needs to exist matching aktor and sensor in other databases, or this wil throw an exception.
     * @param rule Rule to be added
     * @throws DependenyDoesNotExistException If there is a problem with duplicate or foreign keys
     */
    public Rule addRule(Rule rule){
        if(rulesRepository.getByName(rule.getName()) != null) throw new DependenyDoesNotExistException();

        if(!aktorsRepository.existsById(rule.getAktorId())) throw new DependenyDoesNotExistException();

        if(!sensorRepository.existsById(rule.getSensorid())) throw new DependenyDoesNotExistException();

        rulesRepository.save(rule);

        return rule;
    }
}
