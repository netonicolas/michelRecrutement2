package fr.nicolasneto.repository;

import fr.nicolasneto.domain.Competence;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Competence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {

}
