package fr.nicolasneto.service.impl;

import fr.nicolasneto.service.CategorieOffreService;
import fr.nicolasneto.domain.CategorieOffre;
import fr.nicolasneto.repository.CategorieOffreRepository;
import fr.nicolasneto.repository.search.CategorieOffreSearchRepository;
import fr.nicolasneto.service.dto.CategorieOffreDTO;
import fr.nicolasneto.service.mapper.CategorieOffreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CategorieOffre.
 */
@Service
@Transactional
public class CategorieOffreServiceImpl implements CategorieOffreService {

    private final Logger log = LoggerFactory.getLogger(CategorieOffreServiceImpl.class);

    private final CategorieOffreRepository categorieOffreRepository;

    private final CategorieOffreMapper categorieOffreMapper;

    private final CategorieOffreSearchRepository categorieOffreSearchRepository;

    public CategorieOffreServiceImpl(CategorieOffreRepository categorieOffreRepository, CategorieOffreMapper categorieOffreMapper, CategorieOffreSearchRepository categorieOffreSearchRepository) {
        this.categorieOffreRepository = categorieOffreRepository;
        this.categorieOffreMapper = categorieOffreMapper;
        this.categorieOffreSearchRepository = categorieOffreSearchRepository;
    }

    /**
     * Save a categorieOffre.
     *
     * @param categorieOffreDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CategorieOffreDTO save(CategorieOffreDTO categorieOffreDTO) {
        log.debug("Request to save CategorieOffre : {}", categorieOffreDTO);
        CategorieOffre categorieOffre = categorieOffreMapper.toEntity(categorieOffreDTO);
        categorieOffre = categorieOffreRepository.save(categorieOffre);
        CategorieOffreDTO result = categorieOffreMapper.toDto(categorieOffre);
        categorieOffreSearchRepository.save(categorieOffre);
        return result;
    }

    /**
     * Get all the categorieOffres.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategorieOffreDTO> findAll() {
        log.debug("Request to get all CategorieOffres");
        return categorieOffreRepository.findAll().stream()
            .map(categorieOffreMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one categorieOffre by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CategorieOffreDTO findOne(Long id) {
        log.debug("Request to get CategorieOffre : {}", id);
        CategorieOffre categorieOffre = categorieOffreRepository.findOne(id);
        return categorieOffreMapper.toDto(categorieOffre);
    }

    /**
     * Delete the categorieOffre by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategorieOffre : {}", id);
        categorieOffreRepository.delete(id);
        categorieOffreSearchRepository.delete(id);
    }

    /**
     * Search for the categorieOffre corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategorieOffreDTO> search(String query) {
        log.debug("Request to search CategorieOffres for query {}", query);
        return StreamSupport
            .stream(categorieOffreSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(categorieOffreMapper::toDto)
            .collect(Collectors.toList());
    }
}
