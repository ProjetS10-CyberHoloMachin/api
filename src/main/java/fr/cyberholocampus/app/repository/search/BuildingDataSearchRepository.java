package fr.cyberholocampus.app.repository.search;

import fr.cyberholocampus.app.domain.BuildingData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BuildingData entity.
 */
public interface BuildingDataSearchRepository extends ElasticsearchRepository<BuildingData, Long> {
}
