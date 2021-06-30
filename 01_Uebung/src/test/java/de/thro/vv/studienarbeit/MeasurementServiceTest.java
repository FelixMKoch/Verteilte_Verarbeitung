package de.thro.vv.studienarbeit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class MeasurementServiceTest {

    @Test
    @DisplayName("String add to queue")
    void addToQueue() {
        MeasurementService measurementService = MeasurementService.getMeasurementService();
        measurementService.addToQueue("Test");
        Assertions.assertEquals("Test", measurementService.getFromQueue());
    }

    @Test
    @DisplayName("Get String from queue")
    void getFromQueue() {
        MeasurementService measurementService = MeasurementService.getMeasurementService();
        //First with a String in queue
        measurementService.addToQueue("Test");
        Assertions.assertEquals("Test", measurementService.getFromQueue());

        //And then without a String in queue --> null should be returned
        Assertions.assertNull(measurementService.getFromQueue());
    }
    /*
    @Test
    @DisplayName("Check if the ExecutorService stops")
    void running() throws InterruptedException{
        MeasurementService measurementService = MeasurementService.getMeasurementService();
        if(!measurementService.isRunning()) measurementService.start();
        Assertions.assertTrue(measurementService.isRunning());
        measurementService.stop();
        Assertions.assertFalse(measurementService.isRunning());
    }
*/

    @Test
    @DisplayName("Test write a file")
    void writeFile(){
        MeasurementService measurementService = MeasurementService.getMeasurementService();
        Assertions.assertTrue(measurementService.writeMeasurement("{\"value\":42,\"unit\":\"celsius\",\"type\":\"temperature\",\"timestamp\":\"1970-01-01T00:00:00\"}"));
        Assertions.assertFalse(measurementService.writeMeasurement(null));
    }

}