package fr.cyberholocampus.app.repository;

import fr.cyberholocampus.app.domain.Affectation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Affectation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AffectationRepository extends JpaRepository<Affectation, Long> {

    @Query("select affectation from Affectation affectation where affectation.user.login = ?#{principal.username}")
    List<Affectation> findByUserIsCurrentUser();

}
