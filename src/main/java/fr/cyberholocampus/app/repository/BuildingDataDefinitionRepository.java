package fr.cyberholocampus.app.repository;

import fr.cyberholocampus.app.domain.BuildingDataDefinition;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BuildingDataDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildingDataDefinitionRepository extends JpaRepository<BuildingDataDefinition, Long> {

}
