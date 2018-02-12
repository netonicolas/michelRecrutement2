package fr.nicolasneto.service.impl;

import fr.nicolasneto.service.CompetenceService;
import fr.nicolasneto.domain.Competence;
import fr.nicolasneto.repository.CompetenceRepository;
import fr.nicolasneto.repository.search.CompetenceSearchRepository;
import fr.nicolasneto.service.dto.CompetenceDTO;
import fr.nicolasneto.service.mapper.CompetenceMapper;
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
 * Service Implementation for managing Competence.
 */
@Service
@Transactional
public class CompetenceServiceImpl implements CompetenceService {

    private final Logger log = LoggerFactory.getLogger(CompetenceServiceImpl.class);

    private final CompetenceRepository competenceRepository;

    private final CompetenceMapper competenceMapper;

    private final CompetenceSearchRepository competenceSearchRepository;

    public CompetenceServiceImpl(CompetenceRepository competenceRepository, CompetenceMapper competenceMapper, CompetenceSearchRepository competenceSearchRepository) {
        this.competenceRepository = competenceRepository;
        this.competenceMapper = competenceMapper;
        this.competenceSearchRepository = competenceSearchRepository;
    }

    /**
     * Save a competence.
     *
     * @param competenceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompetenceDTO save(CompetenceDTO competenceDTO) {
        log.debug("Request to save Competence : {}", competenceDTO);
        Competence competence = competenceMapper.toEntity(competenceDTO);
        competence = competenceRepository.save(competence);
        CompetenceDTO result = competenceMapper.toDto(competence);
        competenceSearchRepository.save(competence);
        return result;
    }

    /**
     * Get all the competences.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CompetenceDTO> findAll() {
        log.debug("Request to get all Competences");
        return competenceRepository.findAll().stream()
            .map(competenceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one competence by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CompetenceDTO findOne(Long id) {
        log.debug("Request to get Competence : {}", id);
        Competence competence = competenceRepository.findOne(id);
        return competenceMapper.toDto(competence);
    }

    /**
     * Delete the competence by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Competence : {}", id);
        competenceRepository.delete(id);
        competenceSearchRepository.delete(id);
    }

    /**
     * Search for the competence corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CompetenceDTO> search(String query) {
        log.debug("Request to search Competences for query {}", query);
        return StreamSupport
            .stream(competenceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(competenceMapper::toDto)
            .collect(Collectors.toList());
    }
}
