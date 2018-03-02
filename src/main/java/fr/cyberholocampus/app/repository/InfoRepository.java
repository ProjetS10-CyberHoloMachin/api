package fr.cyberholocampus.app.repository;

import fr.cyberholocampus.app.domain.Info;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Info entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoRepository extends JpaRepository<Info, Long> {

}
