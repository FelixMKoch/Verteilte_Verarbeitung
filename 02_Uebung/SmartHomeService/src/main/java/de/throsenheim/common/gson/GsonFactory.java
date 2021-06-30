package de.throsenheim.common.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Facotry for different Gson-builder
 */
public class GsonFactory {

    private GsonFactory(){

    }

    /**
     * Get aGson-Builder ot convert Json with localDate in it
     * @return Gson Builder to convert Json
     */
    public static Gson getGson(){
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new DateGsonAdapter())
                .create();
    }

    /**
     * An adapted for localDate in the Json-String of the class to be serialized
     */
    private static class DateGsonAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.format(DateTimeFormatter.ISO_DATE_TIME));
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {

            String jsonString = json.getAsString();

            return LocalDateTime.parse(jsonString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }
}
