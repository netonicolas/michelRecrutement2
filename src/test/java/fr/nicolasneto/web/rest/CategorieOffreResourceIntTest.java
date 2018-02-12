package fr.nicolasneto.web.rest;

import fr.nicolasneto.MichelRecrutement2App;

import fr.nicolasneto.domain.CategorieOffre;
import fr.nicolasneto.repository.CategorieOffreRepository;
import fr.nicolasneto.service.CategorieOffreService;
import fr.nicolasneto.repository.search.CategorieOffreSearchRepository;
import fr.nicolasneto.service.dto.CategorieOffreDTO;
import fr.nicolasneto.service.mapper.CategorieOffreMapper;
import fr.nicolasneto.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static fr.nicolasneto.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CategorieOffreResource REST controller.
 *
 * @see CategorieOffreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MichelRecrutement2App.class)
public class CategorieOffreResourceIntTest {

    private static final String DEFAULT_NOM_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CATEGORIE = "BBBBBBBBBB";

    @Autowired
    private CategorieOffreRepository categorieOffreRepository;

    @Autowired
    private CategorieOffreMapper categorieOffreMapper;

    @Autowired
    private CategorieOffreService categorieOffreService;

    @Autowired
    private CategorieOffreSearchRepository categorieOffreSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCategorieOffreMockMvc;

    private CategorieOffre categorieOffre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategorieOffreResource categorieOffreResource = new CategorieOffreResource(categorieOffreService);
        this.restCategorieOffreMockMvc = MockMvcBuilders.standaloneSetup(categorieOffreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieOffre createEntity(EntityManager em) {
        CategorieOffre categorieOffre = new CategorieOffre()
            .nomCategorie(DEFAULT_NOM_CATEGORIE);
        return categorieOffre;
    }

    @Before
    public void initTest() {
        categorieOffreSearchRepository.deleteAll();
        categorieOffre = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategorieOffre() throws Exception {
        int databaseSizeBeforeCreate = categorieOffreRepository.findAll().size();

        // Create the CategorieOffre
        CategorieOffreDTO categorieOffreDTO = categorieOffreMapper.toDto(categorieOffre);
        restCategorieOffreMockMvc.perform(post("/api/categorie-offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieOffreDTO)))
            .andExpect(status().isCreated());

        // Validate the CategorieOffre in the database
        List<CategorieOffre> categorieOffreList = categorieOffreRepository.findAll();
        assertThat(categorieOffreList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieOffre testCategorieOffre = categorieOffreList.get(categorieOffreList.size() - 1);
        assertThat(testCategorieOffre.getNomCategorie()).isEqualTo(DEFAULT_NOM_CATEGORIE);

        // Validate the CategorieOffre in Elasticsearch
        CategorieOffre categorieOffreEs = categorieOffreSearchRepository.findOne(testCategorieOffre.getId());
        assertThat(categorieOffreEs).isEqualToIgnoringGivenFields(testCategorieOffre);
    }

    @Test
    @Transactional
    public void createCategorieOffreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categorieOffreRepository.findAll().size();

        // Create the CategorieOffre with an existing ID
        categorieOffre.setId(1L);
        CategorieOffreDTO categorieOffreDTO = categorieOffreMapper.toDto(categorieOffre);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieOffreMockMvc.perform(post("/api/categorie-offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieOffreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategorieOffre in the database
        List<CategorieOffre> categorieOffreList = categorieOffreRepository.findAll();
        assertThat(categorieOffreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomCategorieIsRequired() throws Exception {
        int databaseSizeBeforeTest = categorieOffreRepository.findAll().size();
        // set the field null
        categorieOffre.setNomCategorie(null);

        // Create the CategorieOffre, which fails.
        CategorieOffreDTO categorieOffreDTO = categorieOffreMapper.toDto(categorieOffre);

        restCategorieOffreMockMvc.perform(post("/api/categorie-offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieOffreDTO)))
            .andExpect(status().isBadRequest());

        List<CategorieOffre> categorieOffreList = categorieOffreRepository.findAll();
        assertThat(categorieOffreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategorieOffres() throws Exception {
        // Initialize the database
        categorieOffreRepository.saveAndFlush(categorieOffre);

        // Get all the categorieOffreList
        restCategorieOffreMockMvc.perform(get("/api/categorie-offres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieOffre.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomCategorie").value(hasItem(DEFAULT_NOM_CATEGORIE.toString())));
    }

    @Test
    @Transactional
    public void getCategorieOffre() throws Exception {
        // Initialize the database
        categorieOffreRepository.saveAndFlush(categorieOffre);

        // Get the categorieOffre
        restCategorieOffreMockMvc.perform(get("/api/categorie-offres/{id}", categorieOffre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categorieOffre.getId().intValue()))
            .andExpect(jsonPath("$.nomCategorie").value(DEFAULT_NOM_CATEGORIE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategorieOffre() throws Exception {
        // Get the categorieOffre
        restCategorieOffreMockMvc.perform(get("/api/categorie-offres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategorieOffre() throws Exception {
        // Initialize the database
        categorieOffreRepository.saveAndFlush(categorieOffre);
        categorieOffreSearchRepository.save(categorieOffre);
        int databaseSizeBeforeUpdate = categorieOffreRepository.findAll().size();

        // Update the categorieOffre
        CategorieOffre updatedCategorieOffre = categorieOffreRepository.findOne(categorieOffre.getId());
        // Disconnect from session so that the updates on updatedCategorieOffre are not directly saved in db
        em.detach(updatedCategorieOffre);
        updatedCategorieOffre
            .nomCategorie(UPDATED_NOM_CATEGORIE);
        CategorieOffreDTO categorieOffreDTO = categorieOffreMapper.toDto(updatedCategorieOffre);

        restCategorieOffreMockMvc.perform(put("/api/categorie-offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieOffreDTO)))
            .andExpect(status().isOk());

        // Validate the CategorieOffre in the database
        List<CategorieOffre> categorieOffreList = categorieOffreRepository.findAll();
        assertThat(categorieOffreList).hasSize(databaseSizeBeforeUpdate);
        CategorieOffre testCategorieOffre = categorieOffreList.get(categorieOffreList.size() - 1);
        assertThat(testCategorieOffre.getNomCategorie()).isEqualTo(UPDATED_NOM_CATEGORIE);

        // Validate the CategorieOffre in Elasticsearch
        CategorieOffre categorieOffreEs = categorieOffreSearchRepository.findOne(testCategorieOffre.getId());
        assertThat(categorieOffreEs).isEqualToIgnoringGivenFields(testCategorieOffre);
    }

    @Test
    @Transactional
    public void updateNonExistingCategorieOffre() throws Exception {
        int databaseSizeBeforeUpdate = categorieOffreRepository.findAll().size();

        // Create the CategorieOffre
        CategorieOffreDTO categorieOffreDTO = categorieOffreMapper.toDto(categorieOffre);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCategorieOffreMockMvc.perform(put("/api/categorie-offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieOffreDTO)))
            .andExpect(status().isCreated());

        // Validate the CategorieOffre in the database
        List<CategorieOffre> categorieOffreList = categorieOffreRepository.findAll();
        assertThat(categorieOffreList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCategorieOffre() throws Exception {
        // Initialize the database
        categorieOffreRepository.saveAndFlush(categorieOffre);
        categorieOffreSearchRepository.save(categorieOffre);
        int databaseSizeBeforeDelete = categorieOffreRepository.findAll().size();

        // Get the categorieOffre
        restCategorieOffreMockMvc.perform(delete("/api/categorie-offres/{id}", categorieOffre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean categorieOffreExistsInEs = categorieOffreSearchRepository.exists(categorieOffre.getId());
        assertThat(categorieOffreExistsInEs).isFalse();

        // Validate the database is empty
        List<CategorieOffre> categorieOffreList = categorieOffreRepository.findAll();
        assertThat(categorieOffreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCategorieOffre() throws Exception {
        // Initialize the database
        categorieOffreRepository.saveAndFlush(categorieOffre);
        categorieOffreSearchRepository.save(categorieOffre);

        // Search the categorieOffre
        restCategorieOffreMockMvc.perform(get("/api/_search/categorie-offres?query=id:" + categorieOffre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieOffre.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomCategorie").value(hasItem(DEFAULT_NOM_CATEGORIE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieOffre.class);
        CategorieOffre categorieOffre1 = new CategorieOffre();
        categorieOffre1.setId(1L);
        CategorieOffre categorieOffre2 = new CategorieOffre();
        categorieOffre2.setId(categorieOffre1.getId());
        assertThat(categorieOffre1).isEqualTo(categorieOffre2);
        categorieOffre2.setId(2L);
        assertThat(categorieOffre1).isNotEqualTo(categorieOffre2);
        categorieOffre1.setId(null);
        assertThat(categorieOffre1).isNotEqualTo(categorieOffre2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieOffreDTO.class);
        CategorieOffreDTO categorieOffreDTO1 = new CategorieOffreDTO();
        categorieOffreDTO1.setId(1L);
        CategorieOffreDTO categorieOffreDTO2 = new CategorieOffreDTO();
        assertThat(categorieOffreDTO1).isNotEqualTo(categorieOffreDTO2);
        categorieOffreDTO2.setId(categorieOffreDTO1.getId());
        assertThat(categorieOffreDTO1).isEqualTo(categorieOffreDTO2);
        categorieOffreDTO2.setId(2L);
        assertThat(categorieOffreDTO1).isNotEqualTo(categorieOffreDTO2);
        categorieOffreDTO1.setId(null);
        assertThat(categorieOffreDTO1).isNotEqualTo(categorieOffreDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(categorieOffreMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(categorieOffreMapper.fromId(null)).isNull();
    }
}
