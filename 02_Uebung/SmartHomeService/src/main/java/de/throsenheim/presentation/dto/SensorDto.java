package de.throsenheim.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for the Sensor entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorDto {

    private Integer sensorId;

    private String name;

    private String location;

}
