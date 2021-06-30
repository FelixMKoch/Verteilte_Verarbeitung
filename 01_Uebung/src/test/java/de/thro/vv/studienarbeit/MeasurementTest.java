package de.thro.vv.studienarbeit;

import com.google.gson.Gson;
import de.thro.vv.studienarbeit.gson.GsonFactory;
import de.thro.vv.studienarbeit.model.Measurement;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class MeasurementTest {

    @Test
    @DisplayName("Convert Measurement class to JSON")
    void toJson() {
        Measurement measurement = new Measurement();
        Gson gson = GsonFactory.getMeasurementGson();

        measurement.setType(Measurement.Type.TEMPERATURE);
        measurement.setUnit(Measurement.Unit.CELSIUS);
        measurement.setValue(30);

        String json = measurement.toJson();

        Measurement newmeasurement = gson.fromJson(json, Measurement.class);

        Assertions.assertEquals(30, newmeasurement.getValue());
        Assertions.assertEquals(Measurement.Type.TEMPERATURE, newmeasurement.getType());
        Assertions.assertEquals(Measurement.Unit.CELSIUS, newmeasurement.getUnit());

    }

    @Test
    @DisplayName("Json to Measurement conversion")
    void fromJson() {
        Measurement measurement;
        measurement = Measurement.fromJson(
                "{\"value\":42,\"unit\":\"celsius\",\"type\":\"temperature\",\"timestamp\":\"1970-01-01T00:00:00\"}"
        );

        Assertions.assertEquals(42, measurement.getValue());
        Assertions.assertEquals(Measurement.Type.TEMPERATURE, measurement.getType());
        Assertions.assertEquals(Measurement.Unit.CELSIUS, measurement.getUnit());
    }

    @Test
    @DisplayName("ToString should give back the JSON-String")
    void measurementToString() {
        Measurement measurement = new Measurement();
        String json = measurement.toJson();

        Assertions.assertEquals(json, measurement.toString());
    }


    @Test
    @DisplayName("Equals")
    void measurementEquals() {
        Measurement measurement1 = new Measurement(40, Measurement.Unit.KELVIN, Measurement.Type.TEMPERATURE, LocalDateTime.now());
        LocalDateTime now = LocalDateTime.now();
        measurement1.setTimestamp(now);

        Measurement measurement2 = new Measurement(40, Measurement.Unit.KELVIN, Measurement.Type.TEMPERATURE, LocalDateTime.now());
        measurement2.setTimestamp(now);

        Measurement measurement3 = new Measurement(42, Measurement.Unit.CELSIUS, Measurement.Type.PRESSURE, LocalDateTime.now());

        Assertions.assertEquals(measurement1, measurement2);
        Assertions.assertNotEquals(measurement1, measurement3);
    }


    @Test
    @DisplayName("Hashcode")
    void measurementHashcode() {
        LocalDateTime now = LocalDateTime.now();
        int hash = new HashCodeBuilder(17, 37)
                .append(42)
                .append(Measurement.Unit.KELVIN)
                .append(Measurement.Type.TEMPERATURE)
                .append(now)
                .toHashCode();

        Measurement measurement = new Measurement(42, Measurement.Unit.KELVIN, Measurement.Type.TEMPERATURE, LocalDateTime.now());
        measurement.setTimestamp(now);

        Assertions.assertEquals(hash, measurement.hashCode());
    }

}