/**
 * Main class to start the sending process of the Sensor
 */
public class SensorStart {
    public static void main(String[] args) {

        String registrationUrl = System.getenv().getOrDefault("SmartHomeServiceRegistrationURL", "http://localhost:8080/sensors/");
        String publishUrl = System.getenv().getOrDefault("SmartHomeServicePublishURL", "http://localhost:8080/sensors/");
        String sensorId = System.getenv().getOrDefault("SensorId", "1");


        SensorSender sensorSender = new SensorSender(Integer.parseInt(sensorId), registrationUrl, publishUrl);

        sensorSender.registerSensor();

        sensorSender.sendValueEveryMinute();

    }
}
