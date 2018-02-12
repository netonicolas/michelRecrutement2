package fr.nicolasneto.repository;

import fr.nicolasneto.domain.CategorieOffre;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CategorieOffre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieOffreRepository extends JpaRepository<CategorieOffre, Long> {

}
