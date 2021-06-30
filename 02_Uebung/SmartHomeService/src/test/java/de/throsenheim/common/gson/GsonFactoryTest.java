package de.throsenheim.common.gson;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GsonFactoryTest {

    @Test
    @DisplayName("Get Gson Object which can serialize and deserialize")
    void getMeasurementGson(){
        Gson gson = GsonFactory.getGson();
        LocalDate now = LocalDate.now();

        //Convert the Measurement-Object into a Json String and back again
        String json = gson.toJson(now);
        LocalDate next = gson.fromJson(json, LocalDate.class);

        Assertions.assertEquals(now, next);

    }

}