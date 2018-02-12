package fr.nicolasneto.web.rest;

import fr.nicolasneto.MichelRecrutement2App;

import fr.nicolasneto.domain.Offre;
import fr.nicolasneto.domain.Entreprise;
import fr.nicolasneto.domain.CategorieOffre;
import fr.nicolasneto.domain.Competence;
import fr.nicolasneto.repository.OffreRepository;
import fr.nicolasneto.service.OffreService;
import fr.nicolasneto.repository.search.OffreSearchRepository;
import fr.nicolasneto.service.dto.OffreDTO;
import fr.nicolasneto.service.mapper.OffreMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static fr.nicolasneto.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.nicolasneto.domain.enumeration.TypeOffre;
/**
 * Test class for the OffreResource REST controller.
 *
 * @see OffreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MichelRecrutement2App.class)
public class OffreResourceIntTest {

    private static final String DEFAULT_NOM_OFFRE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_OFFRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_OFFRE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_OFFRE = "BBBBBBBBBB";

    private static final Long DEFAULT_SALAIRE_MIN = 1L;
    private static final Long UPDATED_SALAIRE_MIN = 2L;

    private static final Long DEFAULT_SALAIRE_MAX = 1L;
    private static final Long UPDATED_SALAIRE_MAX = 2L;

    private static final TypeOffre DEFAULT_TYPE_OFFRE = TypeOffre.CDI;
    private static final TypeOffre UPDATED_TYPE_OFFRE = TypeOffre.CDD;

    private static final LocalDate DEFAULT_DATE_OFFRES = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OFFRES = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private OffreMapper offreMapper;

    @Autowired
    private OffreService offreService;

    @Autowired
    private OffreSearchRepository offreSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOffreMockMvc;

    private Offre offre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OffreResource offreResource = new OffreResource(offreService);
        this.restOffreMockMvc = MockMvcBuilders.standaloneSetup(offreResource)
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
    public static Offre createEntity(EntityManager em) {
        Offre offre = new Offre()
            .nomOffre(DEFAULT_NOM_OFFRE)
            .descriptionOffre(DEFAULT_DESCRIPTION_OFFRE)
            .salaireMin(DEFAULT_SALAIRE_MIN)
            .salaireMax(DEFAULT_SALAIRE_MAX)
            .typeOffre(DEFAULT_TYPE_OFFRE)
            .dateOffres(DEFAULT_DATE_OFFRES);
        // Add required entity
        Entreprise entreprise = EntrepriseResourceIntTest.createEntity(em);
        em.persist(entreprise);
        em.flush();
        offre.setEntreprise(entreprise);
        // Add required entity
        CategorieOffre categorieOffre = CategorieOffreResourceIntTest.createEntity(em);
        em.persist(categorieOffre);
        em.flush();
        offre.setCategorieOffre(categorieOffre);
        // Add required entity
        Competence competence = CompetenceResourceIntTest.createEntity(em);
        em.persist(competence);
        em.flush();
        offre.getCompetences().add(competence);
        return offre;
    }

    @Before
    public void initTest() {
        offreSearchRepository.deleteAll();
        offre = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffre() throws Exception {
        int databaseSizeBeforeCreate = offreRepository.findAll().size();

        // Create the Offre
        OffreDTO offreDTO = offreMapper.toDto(offre);
        restOffreMockMvc.perform(post("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isCreated());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeCreate + 1);
        Offre testOffre = offreList.get(offreList.size() - 1);
        assertThat(testOffre.getNomOffre()).isEqualTo(DEFAULT_NOM_OFFRE);
        assertThat(testOffre.getDescriptionOffre()).isEqualTo(DEFAULT_DESCRIPTION_OFFRE);
        assertThat(testOffre.getSalaireMin()).isEqualTo(DEFAULT_SALAIRE_MIN);
        assertThat(testOffre.getSalaireMax()).isEqualTo(DEFAULT_SALAIRE_MAX);
        assertThat(testOffre.getTypeOffre()).isEqualTo(DEFAULT_TYPE_OFFRE);
        assertThat(testOffre.getDateOffres()).isEqualTo(DEFAULT_DATE_OFFRES);

        // Validate the Offre in Elasticsearch
        Offre offreEs = offreSearchRepository.findOne(testOffre.getId());
        assertThat(offreEs).isEqualToIgnoringGivenFields(testOffre);
    }

    @Test
    @Transactional
    public void createOffreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offreRepository.findAll().size();

        // Create the Offre with an existing ID
        offre.setId(1L);
        OffreDTO offreDTO = offreMapper.toDto(offre);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOffreMockMvc.perform(post("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomOffreIsRequired() throws Exception {
        int databaseSizeBeforeTest = offreRepository.findAll().size();
        // set the field null
        offre.setNomOffre(null);

        // Create the Offre, which fails.
        OffreDTO offreDTO = offreMapper.toDto(offre);

        restOffreMockMvc.perform(post("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isBadRequest());

        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionOffreIsRequired() throws Exception {
        int databaseSizeBeforeTest = offreRepository.findAll().size();
        // set the field null
        offre.setDescriptionOffre(null);

        // Create the Offre, which fails.
        OffreDTO offreDTO = offreMapper.toDto(offre);

        restOffreMockMvc.perform(post("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isBadRequest());

        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeOffreIsRequired() throws Exception {
        int databaseSizeBeforeTest = offreRepository.findAll().size();
        // set the field null
        offre.setTypeOffre(null);

        // Create the Offre, which fails.
        OffreDTO offreDTO = offreMapper.toDto(offre);

        restOffreMockMvc.perform(post("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isBadRequest());

        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOffresIsRequired() throws Exception {
        int databaseSizeBeforeTest = offreRepository.findAll().size();
        // set the field null
        offre.setDateOffres(null);

        // Create the Offre, which fails.
        OffreDTO offreDTO = offreMapper.toDto(offre);

        restOffreMockMvc.perform(post("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isBadRequest());

        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOffres() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList
        restOffreMockMvc.perform(get("/api/offres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offre.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomOffre").value(hasItem(DEFAULT_NOM_OFFRE.toString())))
            .andExpect(jsonPath("$.[*].descriptionOffre").value(hasItem(DEFAULT_DESCRIPTION_OFFRE.toString())))
            .andExpect(jsonPath("$.[*].salaireMin").value(hasItem(DEFAULT_SALAIRE_MIN.intValue())))
            .andExpect(jsonPath("$.[*].salaireMax").value(hasItem(DEFAULT_SALAIRE_MAX.intValue())))
            .andExpect(jsonPath("$.[*].typeOffre").value(hasItem(DEFAULT_TYPE_OFFRE.toString())))
            .andExpect(jsonPath("$.[*].dateOffres").value(hasItem(DEFAULT_DATE_OFFRES.toString())));
    }

    @Test
    @Transactional
    public void getOffre() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get the offre
        restOffreMockMvc.perform(get("/api/offres/{id}", offre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(offre.getId().intValue()))
            .andExpect(jsonPath("$.nomOffre").value(DEFAULT_NOM_OFFRE.toString()))
            .andExpect(jsonPath("$.descriptionOffre").value(DEFAULT_DESCRIPTION_OFFRE.toString()))
            .andExpect(jsonPath("$.salaireMin").value(DEFAULT_SALAIRE_MIN.intValue()))
            .andExpect(jsonPath("$.salaireMax").value(DEFAULT_SALAIRE_MAX.intValue()))
            .andExpect(jsonPath("$.typeOffre").value(DEFAULT_TYPE_OFFRE.toString()))
            .andExpect(jsonPath("$.dateOffres").value(DEFAULT_DATE_OFFRES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOffre() throws Exception {
        // Get the offre
        restOffreMockMvc.perform(get("/api/offres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffre() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);
        offreSearchRepository.save(offre);
        int databaseSizeBeforeUpdate = offreRepository.findAll().size();

        // Update the offre
        Offre updatedOffre = offreRepository.findOne(offre.getId());
        // Disconnect from session so that the updates on updatedOffre are not directly saved in db
        em.detach(updatedOffre);
        updatedOffre
            .nomOffre(UPDATED_NOM_OFFRE)
            .descriptionOffre(UPDATED_DESCRIPTION_OFFRE)
            .salaireMin(UPDATED_SALAIRE_MIN)
            .salaireMax(UPDATED_SALAIRE_MAX)
            .typeOffre(UPDATED_TYPE_OFFRE)
            .dateOffres(UPDATED_DATE_OFFRES);
        OffreDTO offreDTO = offreMapper.toDto(updatedOffre);

        restOffreMockMvc.perform(put("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isOk());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
        Offre testOffre = offreList.get(offreList.size() - 1);
        assertThat(testOffre.getNomOffre()).isEqualTo(UPDATED_NOM_OFFRE);
        assertThat(testOffre.getDescriptionOffre()).isEqualTo(UPDATED_DESCRIPTION_OFFRE);
        assertThat(testOffre.getSalaireMin()).isEqualTo(UPDATED_SALAIRE_MIN);
        assertThat(testOffre.getSalaireMax()).isEqualTo(UPDATED_SALAIRE_MAX);
        assertThat(testOffre.getTypeOffre()).isEqualTo(UPDATED_TYPE_OFFRE);
        assertThat(testOffre.getDateOffres()).isEqualTo(UPDATED_DATE_OFFRES);

        // Validate the Offre in Elasticsearch
        Offre offreEs = offreSearchRepository.findOne(testOffre.getId());
        assertThat(offreEs).isEqualToIgnoringGivenFields(testOffre);
    }

    @Test
    @Transactional
    public void updateNonExistingOffre() throws Exception {
        int databaseSizeBeforeUpdate = offreRepository.findAll().size();

        // Create the Offre
        OffreDTO offreDTO = offreMapper.toDto(offre);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOffreMockMvc.perform(put("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isCreated());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOffre() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);
        offreSearchRepository.save(offre);
        int databaseSizeBeforeDelete = offreRepository.findAll().size();

        // Get the offre
        restOffreMockMvc.perform(delete("/api/offres/{id}", offre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean offreExistsInEs = offreSearchRepository.exists(offre.getId());
        assertThat(offreExistsInEs).isFalse();

        // Validate the database is empty
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOffre() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);
        offreSearchRepository.save(offre);

        // Search the offre
        restOffreMockMvc.perform(get("/api/_search/offres?query=id:" + offre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offre.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomOffre").value(hasItem(DEFAULT_NOM_OFFRE.toString())))
            .andExpect(jsonPath("$.[*].descriptionOffre").value(hasItem(DEFAULT_DESCRIPTION_OFFRE.toString())))
            .andExpect(jsonPath("$.[*].salaireMin").value(hasItem(DEFAULT_SALAIRE_MIN.intValue())))
            .andExpect(jsonPath("$.[*].salaireMax").value(hasItem(DEFAULT_SALAIRE_MAX.intValue())))
            .andExpect(jsonPath("$.[*].typeOffre").value(hasItem(DEFAULT_TYPE_OFFRE.toString())))
            .andExpect(jsonPath("$.[*].dateOffres").value(hasItem(DEFAULT_DATE_OFFRES.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Offre.class);
        Offre offre1 = new Offre();
        offre1.setId(1L);
        Offre offre2 = new Offre();
        offre2.setId(offre1.getId());
        assertThat(offre1).isEqualTo(offre2);
        offre2.setId(2L);
        assertThat(offre1).isNotEqualTo(offre2);
        offre1.setId(null);
        assertThat(offre1).isNotEqualTo(offre2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OffreDTO.class);
        OffreDTO offreDTO1 = new OffreDTO();
        offreDTO1.setId(1L);
        OffreDTO offreDTO2 = new OffreDTO();
        assertThat(offreDTO1).isNotEqualTo(offreDTO2);
        offreDTO2.setId(offreDTO1.getId());
        assertThat(offreDTO1).isEqualTo(offreDTO2);
        offreDTO2.setId(2L);
        assertThat(offreDTO1).isNotEqualTo(offreDTO2);
        offreDTO1.setId(null);
        assertThat(offreDTO1).isNotEqualTo(offreDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(offreMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(offreMapper.fromId(null)).isNull();
    }
}
