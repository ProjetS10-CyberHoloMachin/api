package fr.cyberholocampus.app.repository;

import fr.cyberholocampus.app.domain.InfoDefinition;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InfoDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoDefinitionRepository extends JpaRepository<InfoDefinition, Long> {

}
