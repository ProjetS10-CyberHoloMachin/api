package fr.cyberholocampus.app.repository.search;

import fr.cyberholocampus.app.domain.Affectation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Affectation entity.
 */
public interface AffectationSearchRepository extends ElasticsearchRepository<Affectation, Long> {
}
