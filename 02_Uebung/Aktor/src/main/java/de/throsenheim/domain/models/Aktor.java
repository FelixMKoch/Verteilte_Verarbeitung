package de.throsenheim.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Model that imitates the AktorDTO of the SmartHomeService
 */
@Data
@AllArgsConstructor
public class Aktor {

    private String name;

    private String location;

    private String serviceUrl;

    private Integer id;
}
