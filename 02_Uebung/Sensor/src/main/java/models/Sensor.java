package models;

/**
 * Sensor that is going to be sent to the SmartHomeService. Imitates SensorDTO
 */
public class Sensor {

    private Integer sensorId;

    private String name;

    private String location;


    public Sensor(){

    }

    public Sensor(Integer sensorId, String name, String location){
        this.sensorId = sensorId;
        this.name = name;
        this.location = location;
    }


    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
