package de.throsenheim.init;

import com.google.gson.GsonBuilder;
import de.throsenheim.domain.models.Aktor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Class for helping this Aktor to register to the SmartHomeService
 */
public class Register {

    private Register(){}

    private static final Logger LOGGER = LoggerFactory.getLogger(Register.class);

    /**
     * Register this Aktor to the SmartHomeService
     */
    public static void registerToSmartHomeService(){

        String registartionUrl = System.getenv().getOrDefault(
                "SmartHomeServiceRegistrationURL","http://localhost:8080/aktors/"
        );

        sendRegistration(registartionUrl);

    }

    /**
     * Sending the registration-request to the SmartHomeServicee
     * @param destiny the URL of the SmartHomeService
     */
    private static void sendRegistration(String destiny){
        try{
            URL url = new URL(destiny);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            Aktor aktor = new Aktor("Schlafzimmer",
                    "Irgendwo",
                    "http://localhost:8090//api/v1/shutter",
                    Integer.parseInt(
                            System.getenv().getOrDefault(
                                    "AktorId",
                                    "2")));
            String json = new GsonBuilder().create().toJson(aktor);

            byte[] out = json.getBytes(StandardCharsets.UTF_8);
            int outlen = out.length;

            http.setFixedLengthStreamingMode(outlen);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();

            try (OutputStream os = http.getOutputStream()){
                os.write(out);
            }
            catch (Exception e){
                LOGGER.info("Not able to establish Output-stream");
            }

        }
        catch (IOException e){
            LOGGER.info("Couldn't send registration to SmartHomeService");
        }
    }
}
