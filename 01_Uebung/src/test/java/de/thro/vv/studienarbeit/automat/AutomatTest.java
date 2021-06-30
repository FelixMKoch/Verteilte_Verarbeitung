package de.thro.vv.studienarbeit.automat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutomatTest {

    @Test
    void testAutomat(){
        Automat automat = new Automat();

        assertEquals(Automat.State.WAITINGFORCLIENT, automat.getCurrentState());

        automat.nextState(Automat.Symbol.SENSORHELLO);
        assertEquals(Automat.State.WAITINGFORACKNOWLEDGEMENT, automat.getCurrentState());

        automat.nextState(Automat.Symbol.ACKNOWLEDGE);
        assertEquals(Automat.State.WAITINGFORMEASUREMENT, automat.getCurrentState());

        automat.nextState(Automat.Symbol.MEASUREMENT);
        assertEquals(Automat.State.WAITINGFORMEASUREMENT, automat.getCurrentState());

        automat.nextState(Automat.Symbol.SENSORHELLO);
        assertEquals(Automat.State.ERROR, automat.getCurrentState());

        automat.nextState(Automat.Symbol.SENSORHELLO);
        assertEquals(Automat.State.ERROR, automat.getCurrentState());
        automat.nextState(Automat.Symbol.TERMINATE);
        assertEquals(Automat.State.TERMINATED, automat.getCurrentState());

    }

}