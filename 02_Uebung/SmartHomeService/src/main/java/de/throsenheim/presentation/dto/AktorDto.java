package de.throsenheim.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for the Aktor entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AktorDto {

    private String name;

    private String location;

    private String serviceUrl;

    private Integer id;

}
