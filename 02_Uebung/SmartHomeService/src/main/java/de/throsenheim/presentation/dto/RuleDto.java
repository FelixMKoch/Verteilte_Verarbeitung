package de.throsenheim.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data transfer object for the Rule entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleDto {

    private String name;

    private Integer sensorid;

    private Integer aktorId;

    private Integer threshhold;

}
