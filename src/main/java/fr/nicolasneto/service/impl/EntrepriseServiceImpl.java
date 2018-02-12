package fr.nicolasneto.service.impl;

import fr.nicolasneto.service.EntrepriseService;
import fr.nicolasneto.domain.Entreprise;
import fr.nicolasneto.repository.EntrepriseRepository;
import fr.nicolasneto.repository.search.EntrepriseSearchRepository;
import fr.nicolasneto.service.dto.EntrepriseDTO;
import fr.nicolasneto.service.mapper.EntrepriseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Entreprise.
 */
@Service
@Transactional
public class EntrepriseServiceImpl implements EntrepriseService {

    private final Logger log = LoggerFactory.getLogger(EntrepriseServiceImpl.class);

    private final EntrepriseRepository entrepriseRepository;

    private final EntrepriseMapper entrepriseMapper;

    private final EntrepriseSearchRepository entrepriseSearchRepository;

    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository, EntrepriseMapper entrepriseMapper, EntrepriseSearchRepository entrepriseSearchRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.entrepriseMapper = entrepriseMapper;
        this.entrepriseSearchRepository = entrepriseSearchRepository;
    }

    /**
     * Save a entreprise.
     *
     * @param entrepriseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EntrepriseDTO save(EntrepriseDTO entrepriseDTO) {
        log.debug("Request to save Entreprise : {}", entrepriseDTO);
        Entreprise entreprise = entrepriseMapper.toEntity(entrepriseDTO);
        entreprise = entrepriseRepository.save(entreprise);
        EntrepriseDTO result = entrepriseMapper.toDto(entreprise);
        entrepriseSearchRepository.save(entreprise);
        return result;
    }

    /**
     * Get all the entreprises.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EntrepriseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entreprises");
        return entrepriseRepository.findAll(pageable)
            .map(entrepriseMapper::toDto);
    }

    /**
     * Get one entreprise by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EntrepriseDTO findOne(Long id) {
        log.debug("Request to get Entreprise : {}", id);
        Entreprise entreprise = entrepriseRepository.findOne(id);
        return entrepriseMapper.toDto(entreprise);
    }

    /**
     * Delete the entreprise by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entreprise : {}", id);
        entrepriseRepository.delete(id);
        entrepriseSearchRepository.delete(id);
    }

    /**
     * Search for the entreprise corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EntrepriseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Entreprises for query {}", query);
        Page<Entreprise> result = entrepriseSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(entrepriseMapper::toDto);
    }
}
