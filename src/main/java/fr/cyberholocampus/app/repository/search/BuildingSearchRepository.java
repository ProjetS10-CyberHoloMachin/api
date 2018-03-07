package fr.cyberholocampus.app.repository.search;

import fr.cyberholocampus.app.domain.Building;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Building entity.
 */
public interface BuildingSearchRepository extends ElasticsearchRepository<Building, Long> {
}
