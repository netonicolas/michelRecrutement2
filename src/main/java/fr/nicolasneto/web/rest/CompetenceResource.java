package fr.nicolasneto.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.nicolasneto.service.CompetenceService;
import fr.nicolasneto.web.rest.errors.BadRequestAlertException;
import fr.nicolasneto.web.rest.util.HeaderUtil;
import fr.nicolasneto.service.dto.CompetenceDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Competence.
 */
@RestController
@RequestMapping("/api")
public class CompetenceResource {

    private final Logger log = LoggerFactory.getLogger(CompetenceResource.class);

    private static final String ENTITY_NAME = "competence";

    private final CompetenceService competenceService;

    public CompetenceResource(CompetenceService competenceService) {
        this.competenceService = competenceService;
    }

    /**
     * POST  /competences : Create a new competence.
     *
     * @param competenceDTO the competenceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new competenceDTO, or with status 400 (Bad Request) if the competence has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/competences")
    @Timed
    public ResponseEntity<CompetenceDTO> createCompetence(@Valid @RequestBody CompetenceDTO competenceDTO) throws URISyntaxException {
        log.debug("REST request to save Competence : {}", competenceDTO);
        if (competenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new competence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetenceDTO result = competenceService.save(competenceDTO);
        return ResponseEntity.created(new URI("/api/competences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /competences : Updates an existing competence.
     *
     * @param competenceDTO the competenceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated competenceDTO,
     * or with status 400 (Bad Request) if the competenceDTO is not valid,
     * or with status 500 (Internal Server Error) if the competenceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/competences")
    @Timed
    public ResponseEntity<CompetenceDTO> updateCompetence(@Valid @RequestBody CompetenceDTO competenceDTO) throws URISyntaxException {
        log.debug("REST request to update Competence : {}", competenceDTO);
        if (competenceDTO.getId() == null) {
            return createCompetence(competenceDTO);
        }
        CompetenceDTO result = competenceService.save(competenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, competenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /competences : get all the competences.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of competences in body
     */
    @GetMapping("/competences")
    @Timed
    public List<CompetenceDTO> getAllCompetences() {
        log.debug("REST request to get all Competences");
        return competenceService.findAll();
        }

    /**
     * GET  /competences/:id : get the "id" competence.
     *
     * @param id the id of the competenceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the competenceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/competences/{id}")
    @Timed
    public ResponseEntity<CompetenceDTO> getCompetence(@PathVariable Long id) {
        log.debug("REST request to get Competence : {}", id);
        CompetenceDTO competenceDTO = competenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(competenceDTO));
    }

    /**
     * DELETE  /competences/:id : delete the "id" competence.
     *
     * @param id the id of the competenceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/competences/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompetence(@PathVariable Long id) {
        log.debug("REST request to delete Competence : {}", id);
        competenceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/competences?query=:query : search for the competence corresponding
     * to the query.
     *
     * @param query the query of the competence search
     * @return the result of the search
     */
    @GetMapping("/_search/competences")
    @Timed
    public List<CompetenceDTO> searchCompetences(@RequestParam String query) {
        log.debug("REST request to search Competences for query {}", query);
        return competenceService.search(query);
    }

}
