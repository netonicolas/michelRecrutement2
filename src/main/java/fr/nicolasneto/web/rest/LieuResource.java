package fr.nicolasneto.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.nicolasneto.service.LieuService;
import fr.nicolasneto.web.rest.errors.BadRequestAlertException;
import fr.nicolasneto.web.rest.util.HeaderUtil;
import fr.nicolasneto.service.dto.LieuDTO;
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
 * REST controller for managing Lieu.
 */
@RestController
@RequestMapping("/api")
public class LieuResource {

    private final Logger log = LoggerFactory.getLogger(LieuResource.class);

    private static final String ENTITY_NAME = "lieu";

    private final LieuService lieuService;

    public LieuResource(LieuService lieuService) {
        this.lieuService = lieuService;
    }

    /**
     * POST  /lieus : Create a new lieu.
     *
     * @param lieuDTO the lieuDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lieuDTO, or with status 400 (Bad Request) if the lieu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lieus")
    @Timed
    public ResponseEntity<LieuDTO> createLieu(@Valid @RequestBody LieuDTO lieuDTO) throws URISyntaxException {
        log.debug("REST request to save Lieu : {}", lieuDTO);
        if (lieuDTO.getId() != null) {
            throw new BadRequestAlertException("A new lieu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LieuDTO result = lieuService.save(lieuDTO);
        return ResponseEntity.created(new URI("/api/lieus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lieus : Updates an existing lieu.
     *
     * @param lieuDTO the lieuDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lieuDTO,
     * or with status 400 (Bad Request) if the lieuDTO is not valid,
     * or with status 500 (Internal Server Error) if the lieuDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lieus")
    @Timed
    public ResponseEntity<LieuDTO> updateLieu(@Valid @RequestBody LieuDTO lieuDTO) throws URISyntaxException {
        log.debug("REST request to update Lieu : {}", lieuDTO);
        if (lieuDTO.getId() == null) {
            return createLieu(lieuDTO);
        }
        LieuDTO result = lieuService.save(lieuDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lieuDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lieus : get all the lieus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lieus in body
     */
    @GetMapping("/lieus")
    @Timed
    public List<LieuDTO> getAllLieus() {
        log.debug("REST request to get all Lieus");
        return lieuService.findAll();
        }

    /**
     * GET  /lieus/:id : get the "id" lieu.
     *
     * @param id the id of the lieuDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lieuDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lieus/{id}")
    @Timed
    public ResponseEntity<LieuDTO> getLieu(@PathVariable Long id) {
        log.debug("REST request to get Lieu : {}", id);
        LieuDTO lieuDTO = lieuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lieuDTO));
    }

    /**
     * DELETE  /lieus/:id : delete the "id" lieu.
     *
     * @param id the id of the lieuDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lieus/{id}")
    @Timed
    public ResponseEntity<Void> deleteLieu(@PathVariable Long id) {
        log.debug("REST request to delete Lieu : {}", id);
        lieuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/lieus?query=:query : search for the lieu corresponding
     * to the query.
     *
     * @param query the query of the lieu search
     * @return the result of the search
     */
    @GetMapping("/_search/lieus")
    @Timed
    public List<LieuDTO> searchLieus(@RequestParam String query) {
        log.debug("REST request to search Lieus for query {}", query);
        return lieuService.search(query);
    }

}
