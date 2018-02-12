package fr.nicolasneto.service;

import fr.nicolasneto.service.dto.EntrepriseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Entreprise.
 */
public interface EntrepriseService {

    /**
     * Save a entreprise.
     *
     * @param entrepriseDTO the entity to save
     * @return the persisted entity
     */
    EntrepriseDTO save(EntrepriseDTO entrepriseDTO);

    /**
     * Get all the entreprises.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EntrepriseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" entreprise.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EntrepriseDTO findOne(Long id);

    /**
     * Delete the "id" entreprise.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the entreprise corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EntrepriseDTO> search(String query, Pageable pageable);
}
