package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.Entreprise;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Entreprise entity.
 */
public interface EntrepriseSearchRepository extends ElasticsearchRepository<Entreprise, Long> {
}
