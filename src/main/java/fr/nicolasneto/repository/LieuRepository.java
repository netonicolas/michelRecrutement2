package fr.nicolasneto.repository;

import fr.nicolasneto.domain.Lieu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Lieu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LieuRepository extends JpaRepository<Lieu, Long> {

}
