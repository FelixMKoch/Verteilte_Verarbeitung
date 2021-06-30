package de.thro.vv.studienarbeit.server;

import de.thro.vv.studienarbeit.MeasurementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultiServerTest {

    @Test
    @DisplayName("Multiserver start stop")
    void running() throws InterruptedException {
        MultiServer multiServer = new MultiServer();
        if(!multiServer.isStarted()) multiServer.start();
        assertTrue(multiServer.isStarted());
        Thread.sleep(2000);

        multiServer.stop();
        assertFalse(multiServer.isStarted());
        MeasurementService measurementService = MeasurementService.getMeasurementService();
        measurementService.stop();
    }
}