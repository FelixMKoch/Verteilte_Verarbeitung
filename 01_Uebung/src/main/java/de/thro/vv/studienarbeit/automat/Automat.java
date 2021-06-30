package de.thro.vv.studienarbeit.automat;

import com.google.gson.annotations.SerializedName;

public class Automat {

    // Zustandsmenge Q
    public enum State {
        WAITINGFORCLIENT, WAITINGFORACKNOWLEDGEMENT,
        WAITINGFORMEASUREMENT, TERMINATED, ERROR}

    //Eingabealphabet Sigma
    public enum Symbol {
        @SerializedName("SensorHello")
        SENSORHELLO,
        @SerializedName("Terminate")
        TERMINATE,
        @SerializedName("Measurement")
        MEASUREMENT,
        @SerializedName("Acknowledge")
        ACKNOWLEDGE,
        @SerializedName("StationHello")
        STATIONHELLO,
        @SerializedName("StationReady")
        STATIONREADY,
        @SerializedName("Error")
        ERROR,
        @SerializedName("TerminateStation")
        TERMINATESTATION
    }

    /**
     * Transitions between the States.
     * Only works for the 4 Symbols the Client sends, otherwise this is out of bounds.
     */
    private State[][] transition = {
        // WAITINGFORCLIENT, WAITINGFORACK, WAITINGFORMEASUREMENT, TERMINATED, ERROR
        {State.WAITINGFORACKNOWLEDGEMENT, State.ERROR, State.ERROR, State.TERMINATED, State.ERROR},  //SENSORHELLO
        {State.ERROR, State.TERMINATED, State.TERMINATED, State.TERMINATED, State.TERMINATED}, //TERMINATE
        {State.ERROR, State.ERROR, State.WAITINGFORMEASUREMENT, State.TERMINATED, State.ERROR}, //MEASUREMENT
        {State.ERROR, State.WAITINGFORMEASUREMENT, State.ERROR, State.TERMINATED, State.ERROR},  //ACKNOWLEDGE
        {State.ERROR, State.ERROR, State.ERROR, State.ERROR, State.ERROR,}, //STATIONHELLO --> not nec.
        {State.ERROR, State.ERROR, State.ERROR, State.ERROR, State.ERROR,}, //STATIONHELLO --> not nec.
        {State.ERROR, State.ERROR, State.ERROR, State.ERROR, State.ERROR,}, //STATIONHELLO --> not nec.
        {State.ERROR, State.ERROR, State.ERROR, State.ERROR, State.ERROR,} //STATIONHELLO --> not nec.

    };

    private State currentState;

    public Automat() {
        this.currentState = State.WAITINGFORCLIENT;
    }

    private State nextState (State currentState, Symbol input) {
        return transition[input.ordinal()][currentState.ordinal()];
    }

    public Symbol nextState(Symbol input) {
        this.currentState = nextState(currentState, input);

        if(this.getCurrentState() == State.ERROR) return Symbol.ERROR;
        if(input == Symbol.SENSORHELLO) return Symbol.STATIONHELLO;
        if(input == Symbol.ACKNOWLEDGE || input == Symbol.MEASUREMENT) return Symbol.STATIONREADY;

        return Symbol.ERROR;
    }

    public State getCurrentState(){
        return this.currentState;
    }
}
