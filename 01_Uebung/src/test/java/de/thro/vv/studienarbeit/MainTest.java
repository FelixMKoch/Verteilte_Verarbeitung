package de.thro.vv.studienarbeit;

import de.thro.vv.studienarbeit.server.MultiServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    @DisplayName("Main starts")
    void mainTests() {
        MeasurementService measurementService = MeasurementService.getMeasurementService();

        MultiServer multiServer = new MultiServer();
        if(multiServer.isStarted()) return;

        Main.main(new String[1]);
        Assertions.assertTrue(multiServer.isStarted());
        Assertions.assertTrue(measurementService.isRunning());
        measurementService.stop();
        multiServer.stop();
    }
}