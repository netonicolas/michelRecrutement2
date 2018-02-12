package fr.nicolasneto.service;

import fr.nicolasneto.service.dto.LieuDTO;
import java.util.List;

/**
 * Service Interface for managing Lieu.
 */
public interface LieuService {

    /**
     * Save a lieu.
     *
     * @param lieuDTO the entity to save
     * @return the persisted entity
     */
    LieuDTO save(LieuDTO lieuDTO);

    /**
     * Get all the lieus.
     *
     * @return the list of entities
     */
    List<LieuDTO> findAll();

    /**
     * Get the "id" lieu.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LieuDTO findOne(Long id);

    /**
     * Delete the "id" lieu.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the lieu corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<LieuDTO> search(String query);
}
