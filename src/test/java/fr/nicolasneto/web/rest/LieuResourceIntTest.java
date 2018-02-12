package fr.nicolasneto.web.rest;

import fr.nicolasneto.MichelRecrutement2App;

import fr.nicolasneto.domain.Lieu;
import fr.nicolasneto.repository.LieuRepository;
import fr.nicolasneto.service.LieuService;
import fr.nicolasneto.repository.search.LieuSearchRepository;
import fr.nicolasneto.service.dto.LieuDTO;
import fr.nicolasneto.service.mapper.LieuMapper;
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
 * Test class for the LieuResource REST controller.
 *
 * @see LieuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MichelRecrutement2App.class)
public class LieuResourceIntTest {

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTEMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RUE = "AAAAAAAAAA";
    private static final String UPDATED_RUE = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMERO = 1L;
    private static final Long UPDATED_NUMERO = 2L;

    @Autowired
    private LieuRepository lieuRepository;

    @Autowired
    private LieuMapper lieuMapper;

    @Autowired
    private LieuService lieuService;

    @Autowired
    private LieuSearchRepository lieuSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLieuMockMvc;

    private Lieu lieu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LieuResource lieuResource = new LieuResource(lieuService);
        this.restLieuMockMvc = MockMvcBuilders.standaloneSetup(lieuResource)
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
    public static Lieu createEntity(EntityManager em) {
        Lieu lieu = new Lieu()
            .pays(DEFAULT_PAYS)
            .ville(DEFAULT_VILLE)
            .departement(DEFAULT_DEPARTEMENT)
            .zipCode(DEFAULT_ZIP_CODE)
            .rue(DEFAULT_RUE)
            .numero(DEFAULT_NUMERO);
        return lieu;
    }

    @Before
    public void initTest() {
        lieuSearchRepository.deleteAll();
        lieu = createEntity(em);
    }

    @Test
    @Transactional
    public void createLieu() throws Exception {
        int databaseSizeBeforeCreate = lieuRepository.findAll().size();

        // Create the Lieu
        LieuDTO lieuDTO = lieuMapper.toDto(lieu);
        restLieuMockMvc.perform(post("/api/lieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lieuDTO)))
            .andExpect(status().isCreated());

        // Validate the Lieu in the database
        List<Lieu> lieuList = lieuRepository.findAll();
        assertThat(lieuList).hasSize(databaseSizeBeforeCreate + 1);
        Lieu testLieu = lieuList.get(lieuList.size() - 1);
        assertThat(testLieu.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testLieu.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testLieu.getDepartement()).isEqualTo(DEFAULT_DEPARTEMENT);
        assertThat(testLieu.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testLieu.getRue()).isEqualTo(DEFAULT_RUE);
        assertThat(testLieu.getNumero()).isEqualTo(DEFAULT_NUMERO);

        // Validate the Lieu in Elasticsearch
        Lieu lieuEs = lieuSearchRepository.findOne(testLieu.getId());
        assertThat(lieuEs).isEqualToIgnoringGivenFields(testLieu);
    }

    @Test
    @Transactional
    public void createLieuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lieuRepository.findAll().size();

        // Create the Lieu with an existing ID
        lieu.setId(1L);
        LieuDTO lieuDTO = lieuMapper.toDto(lieu);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLieuMockMvc.perform(post("/api/lieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lieuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lieu in the database
        List<Lieu> lieuList = lieuRepository.findAll();
        assertThat(lieuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = lieuRepository.findAll().size();
        // set the field null
        lieu.setPays(null);

        // Create the Lieu, which fails.
        LieuDTO lieuDTO = lieuMapper.toDto(lieu);

        restLieuMockMvc.perform(post("/api/lieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lieuDTO)))
            .andExpect(status().isBadRequest());

        List<Lieu> lieuList = lieuRepository.findAll();
        assertThat(lieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = lieuRepository.findAll().size();
        // set the field null
        lieu.setVille(null);

        // Create the Lieu, which fails.
        LieuDTO lieuDTO = lieuMapper.toDto(lieu);

        restLieuMockMvc.perform(post("/api/lieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lieuDTO)))
            .andExpect(status().isBadRequest());

        List<Lieu> lieuList = lieuRepository.findAll();
        assertThat(lieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkZipCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lieuRepository.findAll().size();
        // set the field null
        lieu.setZipCode(null);

        // Create the Lieu, which fails.
        LieuDTO lieuDTO = lieuMapper.toDto(lieu);

        restLieuMockMvc.perform(post("/api/lieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lieuDTO)))
            .andExpect(status().isBadRequest());

        List<Lieu> lieuList = lieuRepository.findAll();
        assertThat(lieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRueIsRequired() throws Exception {
        int databaseSizeBeforeTest = lieuRepository.findAll().size();
        // set the field null
        lieu.setRue(null);

        // Create the Lieu, which fails.
        LieuDTO lieuDTO = lieuMapper.toDto(lieu);

        restLieuMockMvc.perform(post("/api/lieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lieuDTO)))
            .andExpect(status().isBadRequest());

        List<Lieu> lieuList = lieuRepository.findAll();
        assertThat(lieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = lieuRepository.findAll().size();
        // set the field null
        lieu.setNumero(null);

        // Create the Lieu, which fails.
        LieuDTO lieuDTO = lieuMapper.toDto(lieu);

        restLieuMockMvc.perform(post("/api/lieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lieuDTO)))
            .andExpect(status().isBadRequest());

        List<Lieu> lieuList = lieuRepository.findAll();
        assertThat(lieuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLieus() throws Exception {
        // Initialize the database
        lieuRepository.saveAndFlush(lieu);

        // Get all the lieuList
        restLieuMockMvc.perform(get("/api/lieus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lieu.getId().intValue())))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS.toString())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].departement").value(hasItem(DEFAULT_DEPARTEMENT.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())))
            .andExpect(jsonPath("$.[*].rue").value(hasItem(DEFAULT_RUE.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())));
    }

    @Test
    @Transactional
    public void getLieu() throws Exception {
        // Initialize the database
        lieuRepository.saveAndFlush(lieu);

        // Get the lieu
        restLieuMockMvc.perform(get("/api/lieus/{id}", lieu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lieu.getId().intValue()))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS.toString()))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.departement").value(DEFAULT_DEPARTEMENT.toString()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()))
            .andExpect(jsonPath("$.rue").value(DEFAULT_RUE.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLieu() throws Exception {
        // Get the lieu
        restLieuMockMvc.perform(get("/api/lieus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLieu() throws Exception {
        // Initialize the database
        lieuRepository.saveAndFlush(lieu);
        lieuSearchRepository.save(lieu);
        int databaseSizeBeforeUpdate = lieuRepository.findAll().size();

        // Update the lieu
        Lieu updatedLieu = lieuRepository.findOne(lieu.getId());
        // Disconnect from session so that the updates on updatedLieu are not directly saved in db
        em.detach(updatedLieu);
        updatedLieu
            .pays(UPDATED_PAYS)
            .ville(UPDATED_VILLE)
            .departement(UPDATED_DEPARTEMENT)
            .zipCode(UPDATED_ZIP_CODE)
            .rue(UPDATED_RUE)
            .numero(UPDATED_NUMERO);
        LieuDTO lieuDTO = lieuMapper.toDto(updatedLieu);

        restLieuMockMvc.perform(put("/api/lieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lieuDTO)))
            .andExpect(status().isOk());

        // Validate the Lieu in the database
        List<Lieu> lieuList = lieuRepository.findAll();
        assertThat(lieuList).hasSize(databaseSizeBeforeUpdate);
        Lieu testLieu = lieuList.get(lieuList.size() - 1);
        assertThat(testLieu.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testLieu.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testLieu.getDepartement()).isEqualTo(UPDATED_DEPARTEMENT);
        assertThat(testLieu.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testLieu.getRue()).isEqualTo(UPDATED_RUE);
        assertThat(testLieu.getNumero()).isEqualTo(UPDATED_NUMERO);

        // Validate the Lieu in Elasticsearch
        Lieu lieuEs = lieuSearchRepository.findOne(testLieu.getId());
        assertThat(lieuEs).isEqualToIgnoringGivenFields(testLieu);
    }

    @Test
    @Transactional
    public void updateNonExistingLieu() throws Exception {
        int databaseSizeBeforeUpdate = lieuRepository.findAll().size();

        // Create the Lieu
        LieuDTO lieuDTO = lieuMapper.toDto(lieu);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLieuMockMvc.perform(put("/api/lieus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lieuDTO)))
            .andExpect(status().isCreated());

        // Validate the Lieu in the database
        List<Lieu> lieuList = lieuRepository.findAll();
        assertThat(lieuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLieu() throws Exception {
        // Initialize the database
        lieuRepository.saveAndFlush(lieu);
        lieuSearchRepository.save(lieu);
        int databaseSizeBeforeDelete = lieuRepository.findAll().size();

        // Get the lieu
        restLieuMockMvc.perform(delete("/api/lieus/{id}", lieu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean lieuExistsInEs = lieuSearchRepository.exists(lieu.getId());
        assertThat(lieuExistsInEs).isFalse();

        // Validate the database is empty
        List<Lieu> lieuList = lieuRepository.findAll();
        assertThat(lieuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLieu() throws Exception {
        // Initialize the database
        lieuRepository.saveAndFlush(lieu);
        lieuSearchRepository.save(lieu);

        // Search the lieu
        restLieuMockMvc.perform(get("/api/_search/lieus?query=id:" + lieu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lieu.getId().intValue())))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS.toString())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].departement").value(hasItem(DEFAULT_DEPARTEMENT.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())))
            .andExpect(jsonPath("$.[*].rue").value(hasItem(DEFAULT_RUE.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lieu.class);
        Lieu lieu1 = new Lieu();
        lieu1.setId(1L);
        Lieu lieu2 = new Lieu();
        lieu2.setId(lieu1.getId());
        assertThat(lieu1).isEqualTo(lieu2);
        lieu2.setId(2L);
        assertThat(lieu1).isNotEqualTo(lieu2);
        lieu1.setId(null);
        assertThat(lieu1).isNotEqualTo(lieu2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LieuDTO.class);
        LieuDTO lieuDTO1 = new LieuDTO();
        lieuDTO1.setId(1L);
        LieuDTO lieuDTO2 = new LieuDTO();
        assertThat(lieuDTO1).isNotEqualTo(lieuDTO2);
        lieuDTO2.setId(lieuDTO1.getId());
        assertThat(lieuDTO1).isEqualTo(lieuDTO2);
        lieuDTO2.setId(2L);
        assertThat(lieuDTO1).isNotEqualTo(lieuDTO2);
        lieuDTO1.setId(null);
        assertThat(lieuDTO1).isNotEqualTo(lieuDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lieuMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lieuMapper.fromId(null)).isNull();
    }
}
