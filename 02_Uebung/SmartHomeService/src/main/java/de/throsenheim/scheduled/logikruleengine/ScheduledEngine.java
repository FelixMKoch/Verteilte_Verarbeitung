package de.throsenheim.scheduled.logikruleengine;

import de.throsenheim.infrastructure.forecast.LogikRuleEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledEngine {

    @Autowired
    private LogikRuleEngine logikRuleEngine;

    /**
     * Scheduling the check of all the rules that are in the database, and afterwards send open/closed to the Aktors
     */
    @Scheduled(fixedRate = 30 * 1000) //every 30 seconds
    public void schedule(){
        logikRuleEngine.startEngine(false);
    }
}
