package de.throsenheim.infrastructure.forecast;

import de.throsenheim.application.forecast.interfaces.IForecastService;
import de.throsenheim.domain.models.Aktor;
import de.throsenheim.domain.models.Rule;
import de.throsenheim.domain.models.SensorData;
import de.throsenheim.persistence.repositories.AktorsRepository;
import de.throsenheim.persistence.repositories.RulesRepository;
import de.throsenheim.persistence.repositories.SensorDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Class only used for scheduling the check of the rules that are stored in the database.
 */
@Service
public class LogikRuleEngine {

    @Autowired
    private RulesRepository rulesRepository;

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private AktorsRepository aktorsRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(LogikRuleEngine.class);

    public static final String WEBSITE = "https://ss21vv-externalweatherservice.azurewebsites.net/";



    public void startEngine(boolean force){

        var rulesIter = rulesRepository.findAll();

        // Iterate through all the rules
        rulesIter.forEach((Rule r) -> {

            //If the weather is not sunny, there should be no changes
            if(!force && !isSunny()) return;

            Integer threshhold = r.getThreshhold();

            Integer sensorId = r.getSensorid();

            //Getting the Aktor that fits the current rule
            var aktorOpt = aktorsRepository.findById(r.getAktorId());

            Aktor aktor = aktorOpt.orElse(null);

            if(aktor == null) return;

            List<SensorData> sensorDataList = sensorDataRepository.getAllWithSensorId(sensorId);

            if(sensorDataList.isEmpty()) return;

            //Sorting the SensorData from the database according to their Date
            sensorDataList.sort((a, b) -> {
                if(a.getTimestamp().equals(b.getTimestamp())) return 0;
                return a.getTimestamp().after(b.getTimestamp()) ? -1 : 1;
            });

            SensorData latestSensorData = sensorDataList.get(0);

            if(latestSensorData.getCurrentValue() >= threshhold) sendToAktor(aktor, "Open");
            else sendToAktor(aktor, "Closed");

        } );

    }

    /**
     * Sends a certain message to a certain Aktor
     * @param aktor Aktor the status should be sent to
     * @param status THe status that is to be sent: either Open or Closed
     */
    private void sendToAktor(Aktor aktor, String status){

        try{
            /*
            Establish an URL connection with the URL of the Aktor
             */
            URL url = new URL(aktor.getServiceUrl() + "?status=" + status);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            String json = "<>";

            byte[] out = json.getBytes(StandardCharsets.UTF_8);
            int outlen = out.length;

            http.setFixedLengthStreamingMode(outlen);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            streamOutMessage(out, http);

        }
        catch (IOException e){
            LOGGER.info("Not able to send a status update to an Aktor!");
        }
    }

    /**
     * Checks if the weather is sunny. Everything else will be treated the same
     * @return Is the eather sunny?
     */
    public boolean isSunny(){

        IForecastService forecastService = new ForecastService(WEBSITE);

        String weather = forecastService.getWeather();

        return weather.equals("Sunny");

    }

    /**
     * Send a Json string to the destination declared in the constructor
     * @param out byte array to send
     * @param http connection, where the message should be sent to
     */
    private void streamOutMessage(byte[] out, HttpURLConnection http){
        try (OutputStream os = http.getOutputStream()){
            os.write(out);
        }
        catch (Exception e){
            LOGGER.info("Not able to establish an Stream to the receiver Aktor of the status update");
        }

    }
}
