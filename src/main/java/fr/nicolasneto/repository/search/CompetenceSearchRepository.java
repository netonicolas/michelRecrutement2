package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.Competence;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Competence entity.
 */
public interface CompetenceSearchRepository extends ElasticsearchRepository<Competence, Long> {
}
