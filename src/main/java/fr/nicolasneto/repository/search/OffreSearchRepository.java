package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.Offre;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Offre entity.
 */
public interface OffreSearchRepository extends ElasticsearchRepository<Offre, Long> {
}
