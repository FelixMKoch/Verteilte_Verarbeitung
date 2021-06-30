package de.throsenheim.infrastructure.forecast;

import com.google.gson.Gson;
import de.throsenheim.application.forecast.interfaces.IForecastService;
import de.throsenheim.common.gson.GsonFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

@Data
public class ForecastService implements IForecastService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForecastService.class);

    private String website;

    private String token;

    public ForecastService(String website){
        this.website = website;
        this.token = null;
    }

    /**
     * If the authentication-token is not set, get the token from the website.
     */
    public void getAuth(){
        if(token != null) return;

        RestTemplate restTemplate = new RestTemplate();
        JWSAuth jwsAuth = new JWSAuth(System.getenv().getOrDefault("WeatherServiceUsername", "kochfelix"),
                System.getenv().getOrDefault("WeatherServicePassword", "vvSS21"));

        HttpEntity<JWSAuth> request = new HttpEntity<>(jwsAuth);

        var response = restTemplate
                .exchange(getWebsite() + "api/v1/authenticate",HttpMethod.POST, request, String.class);

        this.token = response.getBody();


        if(this.token == null) LOGGER.info("Problem with getting the Auth token from website");

    }

    /**
     * Get the current weather String from the website. Needs to get Auth-Token before.
     * @return current weather
     */
    public String getWeather(){

        getAuth();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        var response = restTemplate
                .exchange(website + "api/WeatherForecast", HttpMethod.GET, entity, String.class);

        Gson gson = GsonFactory.getGson();

        ForecastModel model = gson.fromJson(response.getBody(), ForecastModel.class);

        return model.getSummary();
    }


    /**
     * Model class for getting the summary out of the response from the website.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ForecastModel{
        String date;
        String summary;
    }


    /**
     * A helper class for handling the authentication with JWT
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class JWSAuth{
        private String username;
        private String password;
    }
}
