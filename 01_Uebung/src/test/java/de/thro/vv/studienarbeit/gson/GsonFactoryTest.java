package de.thro.vv.studienarbeit.gson;

import com.google.gson.Gson;
import de.thro.vv.studienarbeit.model.Measurement;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

class GsonFactoryTest {

    @Test
    @DisplayName("Get Gson Object which can serialize and deserialize")
    void getMeasurementGson(){
        Gson gson = GsonFactory.getMeasurementGson();
        Measurement measurement = new Measurement();
        LocalDateTime dateTimeBefore = measurement.getTimestamp();

        //Convert the Measurement-Object into a Json String and back again
        String json = measurement.toJson();
        Measurement newmeasurement = gson.fromJson(json, Measurement.class);

        Assertions.assertEquals(dateTimeBefore, newmeasurement.getTimestamp());

    }

}