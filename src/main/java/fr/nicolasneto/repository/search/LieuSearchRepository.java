package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.Lieu;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Lieu entity.
 */
public interface LieuSearchRepository extends ElasticsearchRepository<Lieu, Long> {
}
