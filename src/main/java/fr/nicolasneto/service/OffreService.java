package fr.nicolasneto.service;

import fr.nicolasneto.service.dto.OffreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Offre.
 */
public interface OffreService {

    /**
     * Save a offre.
     *
     * @param offreDTO the entity to save
     * @return the persisted entity
     */
    OffreDTO save(OffreDTO offreDTO);

    /**
     * Get all the offres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OffreDTO> findAll(Pageable pageable);

    /**
     * Get the "id" offre.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OffreDTO findOne(Long id);

    /**
     * Delete the "id" offre.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the offre corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OffreDTO> search(String query, Pageable pageable);
}
