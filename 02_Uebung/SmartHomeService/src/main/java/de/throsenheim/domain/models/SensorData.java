package de.throsenheim.domain.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Entität SensorData
 */
@Data
@Entity
@Table(name = "sensordata")
public class SensorData {

    @GeneratedValue
    @Id
    @Column (name = "id")
    private Integer id;

    @Column(name = "sensorid")
    private Integer sensorid;

    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "currentvalue")
    private Integer currentValue;

    @Column(name = "temperatureunit")
    private String temperatureUnit;
}
