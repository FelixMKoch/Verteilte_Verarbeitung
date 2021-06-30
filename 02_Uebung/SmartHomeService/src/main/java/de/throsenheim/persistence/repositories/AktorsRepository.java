package de.throsenheim.persistence.repositories;

import de.throsenheim.domain.models.Aktor;
import org.springframework.data.repository.CrudRepository;

public interface AktorsRepository extends CrudRepository<Aktor, Integer> {

}
