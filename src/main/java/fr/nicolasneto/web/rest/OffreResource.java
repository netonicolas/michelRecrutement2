package fr.nicolasneto.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.nicolasneto.service.OffreService;
import fr.nicolasneto.web.rest.errors.BadRequestAlertException;
import fr.nicolasneto.web.rest.util.HeaderUtil;
import fr.nicolasneto.web.rest.util.PaginationUtil;
import fr.nicolasneto.service.dto.OffreDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing Offre.
 */
@RestController
@RequestMapping("/api")
public class OffreResource {

    private final Logger log = LoggerFactory.getLogger(OffreResource.class);

    private static final String ENTITY_NAME = "offre";

    private final OffreService offreService;

    public OffreResource(OffreService offreService) {
        this.offreService = offreService;
    }

    /**
     * POST  /offres : Create a new offre.
     *
     * @param offreDTO the offreDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new offreDTO, or with status 400 (Bad Request) if the offre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/offres")
    @Timed
    public ResponseEntity<OffreDTO> createOffre(@Valid @RequestBody OffreDTO offreDTO) throws URISyntaxException {
        log.debug("REST request to save Offre : {}", offreDTO);
        if (offreDTO.getId() != null) {
            throw new BadRequestAlertException("A new offre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OffreDTO result = offreService.save(offreDTO);
        return ResponseEntity.created(new URI("/api/offres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /offres : Updates an existing offre.
     *
     * @param offreDTO the offreDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated offreDTO,
     * or with status 400 (Bad Request) if the offreDTO is not valid,
     * or with status 500 (Internal Server Error) if the offreDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/offres")
    @Timed
    public ResponseEntity<OffreDTO> updateOffre(@Valid @RequestBody OffreDTO offreDTO) throws URISyntaxException {
        log.debug("REST request to update Offre : {}", offreDTO);
        if (offreDTO.getId() == null) {
            return createOffre(offreDTO);
        }
        OffreDTO result = offreService.save(offreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, offreDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /offres : get all the offres.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of offres in body
     */
    @GetMapping("/offres")
    @Timed
    public ResponseEntity<List<OffreDTO>> getAllOffres(Pageable pageable) {
        log.debug("REST request to get a page of Offres");
        Page<OffreDTO> page = offreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/offres");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /offres/:id : get the "id" offre.
     *
     * @param id the id of the offreDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the offreDTO, or with status 404 (Not Found)
     */
    @GetMapping("/offres/{id}")
    @Timed
    public ResponseEntity<OffreDTO> getOffre(@PathVariable Long id) {
        log.debug("REST request to get Offre : {}", id);
        OffreDTO offreDTO = offreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(offreDTO));
    }

    /**
     * DELETE  /offres/:id : delete the "id" offre.
     *
     * @param id the id of the offreDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/offres/{id}")
    @Timed
    public ResponseEntity<Void> deleteOffre(@PathVariable Long id) {
        log.debug("REST request to delete Offre : {}", id);
        offreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/offres?query=:query : search for the offre corresponding
     * to the query.
     *
     * @param query the query of the offre search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/offres")
    @Timed
    public ResponseEntity<List<OffreDTO>> searchOffres(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Offres for query {}", query);
        Page<OffreDTO> page = offreService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/offres");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
