package fr.nicolasneto.service;

import fr.nicolasneto.service.dto.CategorieOffreDTO;
import java.util.List;

/**
 * Service Interface for managing CategorieOffre.
 */
public interface CategorieOffreService {

    /**
     * Save a categorieOffre.
     *
     * @param categorieOffreDTO the entity to save
     * @return the persisted entity
     */
    CategorieOffreDTO save(CategorieOffreDTO categorieOffreDTO);

    /**
     * Get all the categorieOffres.
     *
     * @return the list of entities
     */
    List<CategorieOffreDTO> findAll();

    /**
     * Get the "id" categorieOffre.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CategorieOffreDTO findOne(Long id);

    /**
     * Delete the "id" categorieOffre.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the categorieOffre corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<CategorieOffreDTO> search(String query);
}
