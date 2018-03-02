package fr.cyberholocampus.app.repository;

import fr.cyberholocampus.app.domain.BuildingData;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BuildingData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildingDataRepository extends JpaRepository<BuildingData, Long> {

}
