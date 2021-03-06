package fr.cyberholocampus.app.repository;

import fr.cyberholocampus.app.domain.Building;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Building entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {

        /* not working
        @Query("select mapping from Building where id = ?1")
        public Blob findMapping(Long id);
        */

}
