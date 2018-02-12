package fr.nicolasneto.service.impl;

import fr.nicolasneto.service.OffreService;
import fr.nicolasneto.domain.Offre;
import fr.nicolasneto.repository.OffreRepository;
import fr.nicolasneto.repository.search.OffreSearchRepository;
import fr.nicolasneto.service.dto.OffreDTO;
import fr.nicolasneto.service.mapper.OffreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Offre.
 */
@Service
@Transactional
public class OffreServiceImpl implements OffreService {

    private final Logger log = LoggerFactory.getLogger(OffreServiceImpl.class);

    private final OffreRepository offreRepository;

    private final OffreMapper offreMapper;

    private final OffreSearchRepository offreSearchRepository;

    public OffreServiceImpl(OffreRepository offreRepository, OffreMapper offreMapper, OffreSearchRepository offreSearchRepository) {
        this.offreRepository = offreRepository;
        this.offreMapper = offreMapper;
        this.offreSearchRepository = offreSearchRepository;
    }

    /**
     * Save a offre.
     *
     * @param offreDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OffreDTO save(OffreDTO offreDTO) {
        log.debug("Request to save Offre : {}", offreDTO);
        Offre offre = offreMapper.toEntity(offreDTO);
        offre = offreRepository.save(offre);
        OffreDTO result = offreMapper.toDto(offre);
        offreSearchRepository.save(offre);
        return result;
    }

    /**
     * Get all the offres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OffreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Offres");
        return offreRepository.findAll(pageable)
            .map(offreMapper::toDto);
    }

    /**
     * Get one offre by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OffreDTO findOne(Long id) {
        log.debug("Request to get Offre : {}", id);
        Offre offre = offreRepository.findOneWithEagerRelationships(id);
        return offreMapper.toDto(offre);
    }

    /**
     * Delete the offre by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Offre : {}", id);
        offreRepository.delete(id);
        offreSearchRepository.delete(id);
    }

    /**
     * Search for the offre corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OffreDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Offres for query {}", query);
        Page<Offre> result = offreSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(offreMapper::toDto);
    }
}
