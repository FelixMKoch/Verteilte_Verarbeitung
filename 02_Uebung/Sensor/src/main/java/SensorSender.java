import com.google.gson.GsonBuilder;
import models.Sensor;
import models.SensorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * A helper class that receives the URL this sensor should register to, and where it should post the data to.
 */
public class SensorSender {

    private Sensor sensor;

    private String registrationUrl;

    private String publishUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorSender.class);

    public SensorSender(Integer sensorId, String registrationUrl, String publishUrl){
        this.sensor = new Sensor();
        this.sensor.setSensorId(sensorId);
        this.sensor.setLocation("Rosenheim");
        this.sensor.setName("TestSensor");

        this.registrationUrl = registrationUrl;

        this.publishUrl = publishUrl;
    }

    /**
     * Registers this sensor (but with the sensorId of this object) to the SmartHomeService.
     */
    public void registerSensor(){
        try {
            URL url = new URL(registrationUrl);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            String json = new GsonBuilder().create().toJson(sensor);

            byte[] out = json.getBytes(StandardCharsets.UTF_8);
            int outlen = out.length;

            http.setFixedLengthStreamingMode(outlen);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            try (OutputStream os = http.getOutputStream()){
                os.write(out);
            }
            catch (Exception e){
                LOGGER.info("Couldn't create OutputStream");
            }

        }
        catch (IOException e){
            LOGGER.info("Failed to register Sensor to the service");
        }

    }

    /**
     * Sens a random sensor value to the SmartHomeService (Url is an attribute of this class)
     */
    public void sendRandomValue(){
        try {
            URL url = new URL(publishUrl + "/" + sensor.getSensorId());
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            /*
            Create a Random Sensor-Value
             */
            SensorData sensorData = new SensorData();
            sensorData.setCurrentValue(new Random().nextInt(30));
            sensorData.setTemperatureUnit("Celsius");
            sensorData.setCurrentMilis(System.currentTimeMillis());

            String json = new GsonBuilder().create().toJson(sensorData);

            byte[] out = json.getBytes(StandardCharsets.UTF_8);
            int outlen = out.length;

            http.setFixedLengthStreamingMode(outlen);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            try (OutputStream os = http.getOutputStream()){
                os.write(out);
            }
            catch (Exception e){
                LOGGER.info("Couldn't create OutputStream");
            }

        }
        catch (IOException e){
            LOGGER.info("Couldn't send a random value to the destination");
        }

    }

    /**
     * Sends out a random data of this sensor every 60 seconds
     */
    public void sendValueEveryMinute() {
        while(!Thread.currentThread().isInterrupted()){
            sendRandomValue();
            try {
                Thread.sleep(60000);
            }
            catch(InterruptedException e){
                break;
            }
        }
    }


    // GETTER AND SETTER

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public String getRegistrationUrl() {
        return registrationUrl;
    }

    public void setRegistrationUrl(String registrationUrl) {
        this.registrationUrl = registrationUrl;
    }

    public String getPublishUrl() {
        return publishUrl;
    }

    public void setPublishUrl(String publishUrl) {
        this.publishUrl = publishUrl;
    }
}
