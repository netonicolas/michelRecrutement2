package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.CategorieOffre;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CategorieOffre entity.
 */
public interface CategorieOffreSearchRepository extends ElasticsearchRepository<CategorieOffre, Long> {
}
