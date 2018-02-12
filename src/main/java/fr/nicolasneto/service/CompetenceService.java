package fr.nicolasneto.service;

import fr.nicolasneto.service.dto.CompetenceDTO;
import java.util.List;

/**
 * Service Interface for managing Competence.
 */
public interface CompetenceService {

    /**
     * Save a competence.
     *
     * @param competenceDTO the entity to save
     * @return the persisted entity
     */
    CompetenceDTO save(CompetenceDTO competenceDTO);

    /**
     * Get all the competences.
     *
     * @return the list of entities
     */
    List<CompetenceDTO> findAll();

    /**
     * Get the "id" competence.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CompetenceDTO findOne(Long id);

    /**
     * Delete the "id" competence.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the competence corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<CompetenceDTO> search(String query);
}
