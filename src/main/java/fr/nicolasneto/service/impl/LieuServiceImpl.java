package fr.nicolasneto.service.impl;

import fr.nicolasneto.service.LieuService;
import fr.nicolasneto.domain.Lieu;
import fr.nicolasneto.repository.LieuRepository;
import fr.nicolasneto.repository.search.LieuSearchRepository;
import fr.nicolasneto.service.dto.LieuDTO;
import fr.nicolasneto.service.mapper.LieuMapper;
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
 * Service Implementation for managing Lieu.
 */
@Service
@Transactional
public class LieuServiceImpl implements LieuService {

    private final Logger log = LoggerFactory.getLogger(LieuServiceImpl.class);

    private final LieuRepository lieuRepository;

    private final LieuMapper lieuMapper;

    private final LieuSearchRepository lieuSearchRepository;

    public LieuServiceImpl(LieuRepository lieuRepository, LieuMapper lieuMapper, LieuSearchRepository lieuSearchRepository) {
        this.lieuRepository = lieuRepository;
        this.lieuMapper = lieuMapper;
        this.lieuSearchRepository = lieuSearchRepository;
    }

    /**
     * Save a lieu.
     *
     * @param lieuDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LieuDTO save(LieuDTO lieuDTO) {
        log.debug("Request to save Lieu : {}", lieuDTO);
        Lieu lieu = lieuMapper.toEntity(lieuDTO);
        lieu = lieuRepository.save(lieu);
        LieuDTO result = lieuMapper.toDto(lieu);
        lieuSearchRepository.save(lieu);
        return result;
    }

    /**
     * Get all the lieus.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LieuDTO> findAll() {
        log.debug("Request to get all Lieus");
        return lieuRepository.findAll().stream()
            .map(lieuMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one lieu by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LieuDTO findOne(Long id) {
        log.debug("Request to get Lieu : {}", id);
        Lieu lieu = lieuRepository.findOne(id);
        return lieuMapper.toDto(lieu);
    }

    /**
     * Delete the lieu by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lieu : {}", id);
        lieuRepository.delete(id);
        lieuSearchRepository.delete(id);
    }

    /**
     * Search for the lieu corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LieuDTO> search(String query) {
        log.debug("Request to search Lieus for query {}", query);
        return StreamSupport
            .stream(lieuSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(lieuMapper::toDto)
            .collect(Collectors.toList());
    }
}
