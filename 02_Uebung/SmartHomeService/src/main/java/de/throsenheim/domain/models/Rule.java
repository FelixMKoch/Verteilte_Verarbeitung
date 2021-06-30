package de.throsenheim.domain.models;

import de.throsenheim.domain.models.exceptions.InvalidThreshholdException;
import lombok.Data;

import javax.persistence.*;

/**
 * Model Rule Entity
 */
@Data
@Entity
@Table(name = "Rules")
public class Rule {

    @GeneratedValue(strategy =  GenerationType.AUTO)
    @Id
    @Column (name = "id")
    private Integer id;

    @Column (name = "name")
    private String name;

    @Column (name = "sensorid")
    private Integer sensorid;

    @Column (name = "aktorid")
    private Integer aktorId;

    @Column (name = "threshhold")
    private Integer threshhold;

    public void setThreshhold(Integer threshhold){
        if(threshhold > 0 && threshhold < 30) this.threshhold = threshhold;
        else throw new InvalidThreshholdException();
    }

}
