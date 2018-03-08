package fr.cyberholocampus.app.web.rest;

import fr.cyberholocampus.app.CyberholocampusApp;

import fr.cyberholocampus.app.domain.Affectation;
import fr.cyberholocampus.app.domain.User;
import fr.cyberholocampus.app.repository.AffectationRepository;
import fr.cyberholocampus.app.repository.search.AffectationSearchRepository;
import fr.cyberholocampus.app.web.rest.errors.ExceptionTranslator;

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

import static fr.cyberholocampus.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AffectationResource REST controller.
 *
 * @see AffectationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CyberholocampusApp.class)
public class AffectationResourceIntTest {

    @Autowired
    private AffectationRepository affectationRepository;

    @Autowired
    private AffectationSearchRepository affectationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAffectationMockMvc;

    private Affectation affectation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AffectationResource affectationResource = new AffectationResource(affectationRepository, affectationSearchRepository);
        this.restAffectationMockMvc = MockMvcBuilders.standaloneSetup(affectationResource)
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
    public static Affectation createEntity(EntityManager em) {
        Affectation affectation = new Affectation();
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        affectation.setUser(user);
        return affectation;
    }

    @Before
    public void initTest() {
        affectationSearchRepository.deleteAll();
        affectation = createEntity(em);
    }

    @Test
    @Transactional
    public void createAffectation() throws Exception {
        int databaseSizeBeforeCreate = affectationRepository.findAll().size();

        // Create the Affectation
        restAffectationMockMvc.perform(post("/api/affectations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affectation)))
            .andExpect(status().isCreated());

        // Validate the Affectation in the database
        List<Affectation> affectationList = affectationRepository.findAll();
        assertThat(affectationList).hasSize(databaseSizeBeforeCreate + 1);
        Affectation testAffectation = affectationList.get(affectationList.size() - 1);

        // Validate the Affectation in Elasticsearch
        Affectation affectationEs = affectationSearchRepository.findOne(testAffectation.getId());
        assertThat(affectationEs).isEqualToIgnoringGivenFields(testAffectation);
    }

    @Test
    @Transactional
    public void createAffectationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = affectationRepository.findAll().size();

        // Create the Affectation with an existing ID
        affectation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAffectationMockMvc.perform(post("/api/affectations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affectation)))
            .andExpect(status().isBadRequest());

        // Validate the Affectation in the database
        List<Affectation> affectationList = affectationRepository.findAll();
        assertThat(affectationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAffectations() throws Exception {
        // Initialize the database
        affectationRepository.saveAndFlush(affectation);

        // Get all the affectationList
        restAffectationMockMvc.perform(get("/api/affectations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affectation.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAffectation() throws Exception {
        // Initialize the database
        affectationRepository.saveAndFlush(affectation);

        // Get the affectation
        restAffectationMockMvc.perform(get("/api/affectations/{id}", affectation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(affectation.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAffectation() throws Exception {
        // Get the affectation
        restAffectationMockMvc.perform(get("/api/affectations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAffectation() throws Exception {
        // Initialize the database
        affectationRepository.saveAndFlush(affectation);
        affectationSearchRepository.save(affectation);
        int databaseSizeBeforeUpdate = affectationRepository.findAll().size();

        // Update the affectation
        Affectation updatedAffectation = affectationRepository.findOne(affectation.getId());
        // Disconnect from session so that the updates on updatedAffectation are not directly saved in db
        em.detach(updatedAffectation);

        restAffectationMockMvc.perform(put("/api/affectations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAffectation)))
            .andExpect(status().isOk());

        // Validate the Affectation in the database
        List<Affectation> affectationList = affectationRepository.findAll();
        assertThat(affectationList).hasSize(databaseSizeBeforeUpdate);
        Affectation testAffectation = affectationList.get(affectationList.size() - 1);

        // Validate the Affectation in Elasticsearch
        Affectation affectationEs = affectationSearchRepository.findOne(testAffectation.getId());
        assertThat(affectationEs).isEqualToIgnoringGivenFields(testAffectation);
    }

    @Test
    @Transactional
    public void updateNonExistingAffectation() throws Exception {
        int databaseSizeBeforeUpdate = affectationRepository.findAll().size();

        // Create the Affectation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAffectationMockMvc.perform(put("/api/affectations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affectation)))
            .andExpect(status().isCreated());

        // Validate the Affectation in the database
        List<Affectation> affectationList = affectationRepository.findAll();
        assertThat(affectationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAffectation() throws Exception {
        // Initialize the database
        affectationRepository.saveAndFlush(affectation);
        affectationSearchRepository.save(affectation);
        int databaseSizeBeforeDelete = affectationRepository.findAll().size();

        // Get the affectation
        restAffectationMockMvc.perform(delete("/api/affectations/{id}", affectation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean affectationExistsInEs = affectationSearchRepository.exists(affectation.getId());
        assertThat(affectationExistsInEs).isFalse();

        // Validate the database is empty
        List<Affectation> affectationList = affectationRepository.findAll();
        assertThat(affectationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAffectation() throws Exception {
        // Initialize the database
        affectationRepository.saveAndFlush(affectation);
        affectationSearchRepository.save(affectation);

        // Search the affectation
        restAffectationMockMvc.perform(get("/api/_search/affectations?query=id:" + affectation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affectation.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Affectation.class);
        Affectation affectation1 = new Affectation();
        affectation1.setId(1L);
        Affectation affectation2 = new Affectation();
        affectation2.setId(affectation1.getId());
        assertThat(affectation1).isEqualTo(affectation2);
        affectation2.setId(2L);
        assertThat(affectation1).isNotEqualTo(affectation2);
        affectation1.setId(null);
        assertThat(affectation1).isNotEqualTo(affectation2);
    }
}
