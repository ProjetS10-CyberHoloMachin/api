package fr.cyberholocampus.app.repository.search;

import fr.cyberholocampus.app.domain.NotificationData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the NotificationData entity.
 */
public interface NotificationDataSearchRepository extends ElasticsearchRepository<NotificationData, Long> {
}
