package models;

/**
 * SensorData to be sent to the SmarHomeService. Imitates SensorDataDTO
 */
public class SensorData {

    private Long currentMilis;

    private Integer currentValue;

    private String temperatureUnit;


    public SensorData(){

    }


    public SensorData(Long currentMilis, Integer currentValue, String temperatureUnit) {
        this.currentMilis = currentMilis;
        this.currentValue = currentValue;
        this.temperatureUnit = temperatureUnit;
    }


    // GETTER AND SETTER

    public Long getCurrentMilis() {
        return currentMilis;
    }

    public void setCurrentMilis(Long currentMilis) {
        this.currentMilis = currentMilis;
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }
}
