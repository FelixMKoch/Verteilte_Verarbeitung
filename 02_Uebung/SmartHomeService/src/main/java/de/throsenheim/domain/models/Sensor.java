package de.throsenheim.domain.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Model für einen Sensor. Wird gebraucht, damit dieser auch gepostet oder in Datenbank gespeichert werden kann
 */
@Entity
@Table (name = "sensors")
@Data
public class Sensor {


    @Id
    @GeneratedValue
    @Column (name = "id")
    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Integer id;

    @Column (name = "sensorid")
    private Integer sensorId;

    @Column (name = "name")
    private String name;

    @Column (name = "date")
    private Date date;

    @Column (name = "location")
    private String location;

    @Column (name = "removaldate")
    private Date removalDate;

}
