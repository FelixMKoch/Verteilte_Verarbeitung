package de.throsenheim.persistence.repositories;

import de.throsenheim.domain.models.Rule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RulesRepository extends CrudRepository<Rule, Integer> {

    /**
     * Search for a Rule by name
     * @param name name of the Rule that is to be searched for
     * @return the Rule with the certain name
     */
    @Query (value = "SELECT * from Rules where Rules.name = :name",
    nativeQuery = true)
    Rule getByName(@Param("name")String name);
}

