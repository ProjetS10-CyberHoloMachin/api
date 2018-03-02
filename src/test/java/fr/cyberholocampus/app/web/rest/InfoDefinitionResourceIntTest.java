package fr.cyberholocampus.app.web.rest;

import fr.cyberholocampus.app.MicroApp;

import fr.cyberholocampus.app.domain.InfoDefinition;
import fr.cyberholocampus.app.repository.InfoDefinitionRepository;
import fr.cyberholocampus.app.service.dto.InfoDefinitionDTO;
import fr.cyberholocampus.app.service.mapper.InfoDefinitionMapper;
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
 * Test class for the InfoDefinitionResource REST controller.
 *
 * @see InfoDefinitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MicroApp.class)
public class InfoDefinitionResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private InfoDefinitionRepository infoDefinitionRepository;

    @Autowired
    private InfoDefinitionMapper infoDefinitionMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInfoDefinitionMockMvc;

    private InfoDefinition infoDefinition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InfoDefinitionResource infoDefinitionResource = new InfoDefinitionResource(infoDefinitionRepository, infoDefinitionMapper);
        this.restInfoDefinitionMockMvc = MockMvcBuilders.standaloneSetup(infoDefinitionResource)
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
    public static InfoDefinition createEntity(EntityManager em) {
        InfoDefinition infoDefinition = new InfoDefinition()
            .label(DEFAULT_LABEL);
        return infoDefinition;
    }

    @Before
    public void initTest() {
        infoDefinition = createEntity(em);
    }

    @Test
    @Transactional
    public void createInfoDefinition() throws Exception {
        int databaseSizeBeforeCreate = infoDefinitionRepository.findAll().size();

        // Create the InfoDefinition
        InfoDefinitionDTO infoDefinitionDTO = infoDefinitionMapper.toDto(infoDefinition);
        restInfoDefinitionMockMvc.perform(post("/api/info-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoDefinitionDTO)))
            .andExpect(status().isCreated());

        // Validate the InfoDefinition in the database
        List<InfoDefinition> infoDefinitionList = infoDefinitionRepository.findAll();
        assertThat(infoDefinitionList).hasSize(databaseSizeBeforeCreate + 1);
        InfoDefinition testInfoDefinition = infoDefinitionList.get(infoDefinitionList.size() - 1);
        assertThat(testInfoDefinition.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createInfoDefinitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = infoDefinitionRepository.findAll().size();

        // Create the InfoDefinition with an existing ID
        infoDefinition.setId(1L);
        InfoDefinitionDTO infoDefinitionDTO = infoDefinitionMapper.toDto(infoDefinition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfoDefinitionMockMvc.perform(post("/api/info-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoDefinitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InfoDefinition in the database
        List<InfoDefinition> infoDefinitionList = infoDefinitionRepository.findAll();
        assertThat(infoDefinitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInfoDefinitions() throws Exception {
        // Initialize the database
        infoDefinitionRepository.saveAndFlush(infoDefinition);

        // Get all the infoDefinitionList
        restInfoDefinitionMockMvc.perform(get("/api/info-definitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infoDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getInfoDefinition() throws Exception {
        // Initialize the database
        infoDefinitionRepository.saveAndFlush(infoDefinition);

        // Get the infoDefinition
        restInfoDefinitionMockMvc.perform(get("/api/info-definitions/{id}", infoDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(infoDefinition.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInfoDefinition() throws Exception {
        // Get the infoDefinition
        restInfoDefinitionMockMvc.perform(get("/api/info-definitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInfoDefinition() throws Exception {
        // Initialize the database
        infoDefinitionRepository.saveAndFlush(infoDefinition);
        int databaseSizeBeforeUpdate = infoDefinitionRepository.findAll().size();

        // Update the infoDefinition
        InfoDefinition updatedInfoDefinition = infoDefinitionRepository.findOne(infoDefinition.getId());
        // Disconnect from session so that the updates on updatedInfoDefinition are not directly saved in db
        em.detach(updatedInfoDefinition);
        updatedInfoDefinition
            .label(UPDATED_LABEL);
        InfoDefinitionDTO infoDefinitionDTO = infoDefinitionMapper.toDto(updatedInfoDefinition);

        restInfoDefinitionMockMvc.perform(put("/api/info-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoDefinitionDTO)))
            .andExpect(status().isOk());

        // Validate the InfoDefinition in the database
        List<InfoDefinition> infoDefinitionList = infoDefinitionRepository.findAll();
        assertThat(infoDefinitionList).hasSize(databaseSizeBeforeUpdate);
        InfoDefinition testInfoDefinition = infoDefinitionList.get(infoDefinitionList.size() - 1);
        assertThat(testInfoDefinition.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingInfoDefinition() throws Exception {
        int databaseSizeBeforeUpdate = infoDefinitionRepository.findAll().size();

        // Create the InfoDefinition
        InfoDefinitionDTO infoDefinitionDTO = infoDefinitionMapper.toDto(infoDefinition);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInfoDefinitionMockMvc.perform(put("/api/info-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoDefinitionDTO)))
            .andExpect(status().isCreated());

        // Validate the InfoDefinition in the database
        List<InfoDefinition> infoDefinitionList = infoDefinitionRepository.findAll();
        assertThat(infoDefinitionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInfoDefinition() throws Exception {
        // Initialize the database
        infoDefinitionRepository.saveAndFlush(infoDefinition);
        int databaseSizeBeforeDelete = infoDefinitionRepository.findAll().size();

        // Get the infoDefinition
        restInfoDefinitionMockMvc.perform(delete("/api/info-definitions/{id}", infoDefinition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InfoDefinition> infoDefinitionList = infoDefinitionRepository.findAll();
        assertThat(infoDefinitionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoDefinition.class);
        InfoDefinition infoDefinition1 = new InfoDefinition();
        infoDefinition1.setId(1L);
        InfoDefinition infoDefinition2 = new InfoDefinition();
        infoDefinition2.setId(infoDefinition1.getId());
        assertThat(infoDefinition1).isEqualTo(infoDefinition2);
        infoDefinition2.setId(2L);
        assertThat(infoDefinition1).isNotEqualTo(infoDefinition2);
        infoDefinition1.setId(null);
        assertThat(infoDefinition1).isNotEqualTo(infoDefinition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoDefinitionDTO.class);
        InfoDefinitionDTO infoDefinitionDTO1 = new InfoDefinitionDTO();
        infoDefinitionDTO1.setId(1L);
        InfoDefinitionDTO infoDefinitionDTO2 = new InfoDefinitionDTO();
        assertThat(infoDefinitionDTO1).isNotEqualTo(infoDefinitionDTO2);
        infoDefinitionDTO2.setId(infoDefinitionDTO1.getId());
        assertThat(infoDefinitionDTO1).isEqualTo(infoDefinitionDTO2);
        infoDefinitionDTO2.setId(2L);
        assertThat(infoDefinitionDTO1).isNotEqualTo(infoDefinitionDTO2);
        infoDefinitionDTO1.setId(null);
        assertThat(infoDefinitionDTO1).isNotEqualTo(infoDefinitionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(infoDefinitionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(infoDefinitionMapper.fromId(null)).isNull();
    }
}
