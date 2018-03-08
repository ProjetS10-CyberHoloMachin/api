package fr.cyberholocampus.app.repository;

import fr.cyberholocampus.app.domain.NotificationData;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NotificationData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationDataRepository extends JpaRepository<NotificationData, Long> {

}
