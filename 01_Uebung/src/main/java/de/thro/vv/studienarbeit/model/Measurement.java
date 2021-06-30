package de.thro.vv.studienarbeit.model;

import java.time.LocalDateTime;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import de.thro.vv.studienarbeit.gson.GsonFactory;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Measurement {

    private Integer value;
    @SerializedName("unit")
    private Unit unit;
    @SerializedName("type")
    private Type type;
    private LocalDateTime timestamp;



    public enum Type {
        @SerializedName("temperature")
        TEMPERATURE,
        @SerializedName("pressure")
        PRESSURE,
        @SerializedName("count")
        COUNT,
        @SerializedName("flow_rate")
        FLOW_RATE,
        @SerializedName("energy")
        ENERGY
    }

    public enum Unit {
        @SerializedName("celsius")
        CELSIUS,
        @SerializedName("kelvin")
        KELVIN,
        @SerializedName("percent")
        PERCENT,
        @SerializedName("units")
        UNITS,
        @SerializedName("cms2")
        CMS2,
        @SerializedName("kwh3")
        KWH3
    }

    public Measurement(Integer value, Unit unit, Type type, LocalDateTime timestamp) {
        this.value = value;
        this.unit = unit;
        this.type = type;
        this.timestamp = timestamp;
    }


    public Measurement(){

    }

    /**
     * This function uses the object it is cast on, and gives back it JSON-String.
     *
     * @return JSON-String of this Object
     */
    public String toJson(){
        Gson gson = GsonFactory.getMeasurementGson();

        return gson.toJson(this);
    }


    /**
     * This function takes a JSON-String as an input and gives back a corresponding Measurement object.
     *
     * @param json  A JSON-String which will be converted to a Measurement object
     * @return The newly created object from the input JSON-String
     */
    public static Measurement fromJson(String json){
        Gson gson = GsonFactory.getMeasurementGson();

        return gson.fromJson(json, Measurement.class);
    }


    // GETTERS AND SETTERS

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime localDateTime) { this.timestamp = localDateTime; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Measurement that = (Measurement) o;

        return new EqualsBuilder()
                .append(value, that.value)
                .append(unit, that.unit)
                .append(type, that.type)
                .append(timestamp, that.timestamp)
                .isEquals();
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .append(unit)
                .append(type)
                .append(timestamp)
                .toHashCode();
    }


    @Override
    public String toString() {
        return toJson();
    }
}

