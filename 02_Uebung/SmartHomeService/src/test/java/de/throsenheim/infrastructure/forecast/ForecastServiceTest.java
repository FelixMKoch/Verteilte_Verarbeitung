package de.throsenheim.infrastructure.forecast;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForecastServiceTest {

    public static final String WEBSITE = "https://ss21vv-externalweatherservice.azurewebsites.net/";

    @Test
    void testRunThroughTheWeatherStationAndShouldReturnAWeatherAsString(){
        /**
         * This also triggers the getAuth process. This should just check if all this runs through the process
         */
        String result = new ForecastService(WEBSITE).getWeather();
        List<String> weatherList = new ArrayList<>();

        weatherList.add("Raining");
        weatherList.add("Sunny");
        weatherList.add("Cloudy");

        assertTrue(weatherList.contains(result));
    }

}