package de.thro.vv.studienarbeit.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import de.thro.vv.studienarbeit.automat.Automat;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void testEquals() {
        Message message = new Message(Automat.Symbol.MEASUREMENT, "TEST");
        assertNotEquals(message, new Message(Automat.Symbol.ACKNOWLEDGE, "TEST"));
        assertEquals(message, new Message(Automat.Symbol.MEASUREMENT, "TEST"));

    }

    @Test
    void testHashCode() {
        Message message = new Message(Automat.Symbol.ACKNOWLEDGE, "TEST");
        int hash = new HashCodeBuilder(17, 37)
                .append(message.getType())
                .append(message.getPayload())
                .toHashCode();
        assertEquals(hash, message.hashCode());
    }

    @Test
    void testInit(){
        Measurement measurement = new Measurement(42, Measurement.Unit.KELVIN, Measurement.Type.TEMPERATURE, LocalDateTime.now());
        Message message = new Message(Automat.Symbol.ERROR, measurement.toJson());
        String s = message.toString();
        assertEquals(message, new Message(s));

        assertEquals(measurement, Measurement.fromJson(message.getPayload()));
    }
}