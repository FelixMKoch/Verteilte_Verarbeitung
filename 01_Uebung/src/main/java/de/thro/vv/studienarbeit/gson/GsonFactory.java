package de.thro.vv.studienarbeit.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDateTime;

public class GsonFactory {

    private GsonFactory(){

    }

    public static Gson getMeasurementGson(){
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new DateGsonAdapter())
                .create();
    }
}
