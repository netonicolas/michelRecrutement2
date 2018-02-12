package fr.nicolasneto.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.nicolasneto.service.CategorieOffreService;
import fr.nicolasneto.web.rest.errors.BadRequestAlertException;
import fr.nicolasneto.web.rest.util.HeaderUtil;
import fr.nicolasneto.service.dto.CategorieOffreDTO;
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
 * REST controller for managing CategorieOffre.
 */
@RestController
@RequestMapping("/api")
public class CategorieOffreResource {

    private final Logger log = LoggerFactory.getLogger(CategorieOffreResource.class);

    private static final String ENTITY_NAME = "categorieOffre";

    private final CategorieOffreService categorieOffreService;

    public CategorieOffreResource(CategorieOffreService categorieOffreService) {
        this.categorieOffreService = categorieOffreService;
    }

    /**
     * POST  /categorie-offres : Create a new categorieOffre.
     *
     * @param categorieOffreDTO the categorieOffreDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categorieOffreDTO, or with status 400 (Bad Request) if the categorieOffre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/categorie-offres")
    @Timed
    public ResponseEntity<CategorieOffreDTO> createCategorieOffre(@Valid @RequestBody CategorieOffreDTO categorieOffreDTO) throws URISyntaxException {
        log.debug("REST request to save CategorieOffre : {}", categorieOffreDTO);
        if (categorieOffreDTO.getId() != null) {
            throw new BadRequestAlertException("A new categorieOffre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieOffreDTO result = categorieOffreService.save(categorieOffreDTO);
        return ResponseEntity.created(new URI("/api/categorie-offres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /categorie-offres : Updates an existing categorieOffre.
     *
     * @param categorieOffreDTO the categorieOffreDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categorieOffreDTO,
     * or with status 400 (Bad Request) if the categorieOffreDTO is not valid,
     * or with status 500 (Internal Server Error) if the categorieOffreDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/categorie-offres")
    @Timed
    public ResponseEntity<CategorieOffreDTO> updateCategorieOffre(@Valid @RequestBody CategorieOffreDTO categorieOffreDTO) throws URISyntaxException {
        log.debug("REST request to update CategorieOffre : {}", categorieOffreDTO);
        if (categorieOffreDTO.getId() == null) {
            return createCategorieOffre(categorieOffreDTO);
        }
        CategorieOffreDTO result = categorieOffreService.save(categorieOffreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categorieOffreDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /categorie-offres : get all the categorieOffres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of categorieOffres in body
     */
    @GetMapping("/categorie-offres")
    @Timed
    public List<CategorieOffreDTO> getAllCategorieOffres() {
        log.debug("REST request to get all CategorieOffres");
        return categorieOffreService.findAll();
        }

    /**
     * GET  /categorie-offres/:id : get the "id" categorieOffre.
     *
     * @param id the id of the categorieOffreDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categorieOffreDTO, or with status 404 (Not Found)
     */
    @GetMapping("/categorie-offres/{id}")
    @Timed
    public ResponseEntity<CategorieOffreDTO> getCategorieOffre(@PathVariable Long id) {
        log.debug("REST request to get CategorieOffre : {}", id);
        CategorieOffreDTO categorieOffreDTO = categorieOffreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(categorieOffreDTO));
    }

    /**
     * DELETE  /categorie-offres/:id : delete the "id" categorieOffre.
     *
     * @param id the id of the categorieOffreDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/categorie-offres/{id}")
    @Timed
    public ResponseEntity<Void> deleteCategorieOffre(@PathVariable Long id) {
        log.debug("REST request to delete CategorieOffre : {}", id);
        categorieOffreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/categorie-offres?query=:query : search for the categorieOffre corresponding
     * to the query.
     *
     * @param query the query of the categorieOffre search
     * @return the result of the search
     */
    @GetMapping("/_search/categorie-offres")
    @Timed
    public List<CategorieOffreDTO> searchCategorieOffres(@RequestParam String query) {
        log.debug("REST request to search CategorieOffres for query {}", query);
        return categorieOffreService.search(query);
    }

}
