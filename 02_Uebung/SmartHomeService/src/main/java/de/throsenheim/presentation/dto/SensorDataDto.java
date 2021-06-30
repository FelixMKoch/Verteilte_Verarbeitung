package de.throsenheim.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for the SensorData entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorDataDto {

    private Long currentMilis;

    private Integer currentValue;

    private String temperatureUnit;

}
