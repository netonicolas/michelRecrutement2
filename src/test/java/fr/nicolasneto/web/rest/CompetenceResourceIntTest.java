package fr.nicolasneto.web.rest;

import fr.nicolasneto.MichelRecrutement2App;

import fr.nicolasneto.domain.Competence;
import fr.nicolasneto.repository.CompetenceRepository;
import fr.nicolasneto.service.CompetenceService;
import fr.nicolasneto.repository.search.CompetenceSearchRepository;
import fr.nicolasneto.service.dto.CompetenceDTO;
import fr.nicolasneto.service.mapper.CompetenceMapper;
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
 * Test class for the CompetenceResource REST controller.
 *
 * @see CompetenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MichelRecrutement2App.class)
public class CompetenceResourceIntTest {

    private static final String DEFAULT_NOM_COMPETENCE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COMPETENCE = "BBBBBBBBBB";

    @Autowired
    private CompetenceRepository competenceRepository;

    @Autowired
    private CompetenceMapper competenceMapper;

    @Autowired
    private CompetenceService competenceService;

    @Autowired
    private CompetenceSearchRepository competenceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompetenceMockMvc;

    private Competence competence;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompetenceResource competenceResource = new CompetenceResource(competenceService);
        this.restCompetenceMockMvc = MockMvcBuilders.standaloneSetup(competenceResource)
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
    public static Competence createEntity(EntityManager em) {
        Competence competence = new Competence()
            .nomCompetence(DEFAULT_NOM_COMPETENCE);
        return competence;
    }

    @Before
    public void initTest() {
        competenceSearchRepository.deleteAll();
        competence = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetence() throws Exception {
        int databaseSizeBeforeCreate = competenceRepository.findAll().size();

        // Create the Competence
        CompetenceDTO competenceDTO = competenceMapper.toDto(competence);
        restCompetenceMockMvc.perform(post("/api/competences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeCreate + 1);
        Competence testCompetence = competenceList.get(competenceList.size() - 1);
        assertThat(testCompetence.getNomCompetence()).isEqualTo(DEFAULT_NOM_COMPETENCE);

        // Validate the Competence in Elasticsearch
        Competence competenceEs = competenceSearchRepository.findOne(testCompetence.getId());
        assertThat(competenceEs).isEqualToIgnoringGivenFields(testCompetence);
    }

    @Test
    @Transactional
    public void createCompetenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competenceRepository.findAll().size();

        // Create the Competence with an existing ID
        competence.setId(1L);
        CompetenceDTO competenceDTO = competenceMapper.toDto(competence);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetenceMockMvc.perform(post("/api/competences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomCompetenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = competenceRepository.findAll().size();
        // set the field null
        competence.setNomCompetence(null);

        // Create the Competence, which fails.
        CompetenceDTO competenceDTO = competenceMapper.toDto(competence);

        restCompetenceMockMvc.perform(post("/api/competences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competenceDTO)))
            .andExpect(status().isBadRequest());

        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompetences() throws Exception {
        // Initialize the database
        competenceRepository.saveAndFlush(competence);

        // Get all the competenceList
        restCompetenceMockMvc.perform(get("/api/competences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competence.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomCompetence").value(hasItem(DEFAULT_NOM_COMPETENCE.toString())));
    }

    @Test
    @Transactional
    public void getCompetence() throws Exception {
        // Initialize the database
        competenceRepository.saveAndFlush(competence);

        // Get the competence
        restCompetenceMockMvc.perform(get("/api/competences/{id}", competence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competence.getId().intValue()))
            .andExpect(jsonPath("$.nomCompetence").value(DEFAULT_NOM_COMPETENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompetence() throws Exception {
        // Get the competence
        restCompetenceMockMvc.perform(get("/api/competences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetence() throws Exception {
        // Initialize the database
        competenceRepository.saveAndFlush(competence);
        competenceSearchRepository.save(competence);
        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();

        // Update the competence
        Competence updatedCompetence = competenceRepository.findOne(competence.getId());
        // Disconnect from session so that the updates on updatedCompetence are not directly saved in db
        em.detach(updatedCompetence);
        updatedCompetence
            .nomCompetence(UPDATED_NOM_COMPETENCE);
        CompetenceDTO competenceDTO = competenceMapper.toDto(updatedCompetence);

        restCompetenceMockMvc.perform(put("/api/competences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competenceDTO)))
            .andExpect(status().isOk());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
        Competence testCompetence = competenceList.get(competenceList.size() - 1);
        assertThat(testCompetence.getNomCompetence()).isEqualTo(UPDATED_NOM_COMPETENCE);

        // Validate the Competence in Elasticsearch
        Competence competenceEs = competenceSearchRepository.findOne(testCompetence.getId());
        assertThat(competenceEs).isEqualToIgnoringGivenFields(testCompetence);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetence() throws Exception {
        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();

        // Create the Competence
        CompetenceDTO competenceDTO = competenceMapper.toDto(competence);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompetenceMockMvc.perform(put("/api/competences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompetence() throws Exception {
        // Initialize the database
        competenceRepository.saveAndFlush(competence);
        competenceSearchRepository.save(competence);
        int databaseSizeBeforeDelete = competenceRepository.findAll().size();

        // Get the competence
        restCompetenceMockMvc.perform(delete("/api/competences/{id}", competence.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean competenceExistsInEs = competenceSearchRepository.exists(competence.getId());
        assertThat(competenceExistsInEs).isFalse();

        // Validate the database is empty
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCompetence() throws Exception {
        // Initialize the database
        competenceRepository.saveAndFlush(competence);
        competenceSearchRepository.save(competence);

        // Search the competence
        restCompetenceMockMvc.perform(get("/api/_search/competences?query=id:" + competence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competence.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomCompetence").value(hasItem(DEFAULT_NOM_COMPETENCE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Competence.class);
        Competence competence1 = new Competence();
        competence1.setId(1L);
        Competence competence2 = new Competence();
        competence2.setId(competence1.getId());
        assertThat(competence1).isEqualTo(competence2);
        competence2.setId(2L);
        assertThat(competence1).isNotEqualTo(competence2);
        competence1.setId(null);
        assertThat(competence1).isNotEqualTo(competence2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetenceDTO.class);
        CompetenceDTO competenceDTO1 = new CompetenceDTO();
        competenceDTO1.setId(1L);
        CompetenceDTO competenceDTO2 = new CompetenceDTO();
        assertThat(competenceDTO1).isNotEqualTo(competenceDTO2);
        competenceDTO2.setId(competenceDTO1.getId());
        assertThat(competenceDTO1).isEqualTo(competenceDTO2);
        competenceDTO2.setId(2L);
        assertThat(competenceDTO1).isNotEqualTo(competenceDTO2);
        competenceDTO1.setId(null);
        assertThat(competenceDTO1).isNotEqualTo(competenceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(competenceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(competenceMapper.fromId(null)).isNull();
    }
}
