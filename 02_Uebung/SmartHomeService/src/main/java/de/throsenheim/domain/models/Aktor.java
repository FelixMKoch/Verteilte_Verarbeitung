package de.throsenheim.domain.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Model Aktor
 */
@Data
@Entity
@Table(name = "Aktors")
public class Aktor {

    @Id
    @Column (name = "id")
    private Integer id;

    @Column (name = "name")
    private String name;

    @Column (name = "date")
    private Date date;

    @Column (name = "location")
    private String location;

    @Column (name = "serviceurl")
    private String serviceUrl;

    @Column (name = "status")
    private String status;

}
