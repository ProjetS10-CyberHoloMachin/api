package fr.cyberholocampus.app.web.rest;

import fr.cyberholocampus.app.MicroApp;

import fr.cyberholocampus.app.domain.BuildingDataDefinition;
import fr.cyberholocampus.app.repository.BuildingDataDefinitionRepository;
import fr.cyberholocampus.app.service.dto.BuildingDataDefinitionDTO;
import fr.cyberholocampus.app.service.mapper.BuildingDataDefinitionMapper;
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
 * Test class for the BuildingDataDefinitionResource REST controller.
 *
 * @see BuildingDataDefinitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MicroApp.class)
public class BuildingDataDefinitionResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private BuildingDataDefinitionRepository buildingDataDefinitionRepository;

    @Autowired
    private BuildingDataDefinitionMapper buildingDataDefinitionMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBuildingDataDefinitionMockMvc;

    private BuildingDataDefinition buildingDataDefinition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BuildingDataDefinitionResource buildingDataDefinitionResource = new BuildingDataDefinitionResource(buildingDataDefinitionRepository, buildingDataDefinitionMapper);
        this.restBuildingDataDefinitionMockMvc = MockMvcBuilders.standaloneSetup(buildingDataDefinitionResource)
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
    public static BuildingDataDefinition createEntity(EntityManager em) {
        BuildingDataDefinition buildingDataDefinition = new BuildingDataDefinition()
            .label(DEFAULT_LABEL);
        return buildingDataDefinition;
    }

    @Before
    public void initTest() {
        buildingDataDefinition = createEntity(em);
    }

    @Test
    @Transactional
    public void createBuildingDataDefinition() throws Exception {
        int databaseSizeBeforeCreate = buildingDataDefinitionRepository.findAll().size();

        // Create the BuildingDataDefinition
        BuildingDataDefinitionDTO buildingDataDefinitionDTO = buildingDataDefinitionMapper.toDto(buildingDataDefinition);
        restBuildingDataDefinitionMockMvc.perform(post("/api/building-data-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDataDefinitionDTO)))
            .andExpect(status().isCreated());

        // Validate the BuildingDataDefinition in the database
        List<BuildingDataDefinition> buildingDataDefinitionList = buildingDataDefinitionRepository.findAll();
        assertThat(buildingDataDefinitionList).hasSize(databaseSizeBeforeCreate + 1);
        BuildingDataDefinition testBuildingDataDefinition = buildingDataDefinitionList.get(buildingDataDefinitionList.size() - 1);
        assertThat(testBuildingDataDefinition.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createBuildingDataDefinitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = buildingDataDefinitionRepository.findAll().size();

        // Create the BuildingDataDefinition with an existing ID
        buildingDataDefinition.setId(1L);
        BuildingDataDefinitionDTO buildingDataDefinitionDTO = buildingDataDefinitionMapper.toDto(buildingDataDefinition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuildingDataDefinitionMockMvc.perform(post("/api/building-data-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDataDefinitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BuildingDataDefinition in the database
        List<BuildingDataDefinition> buildingDataDefinitionList = buildingDataDefinitionRepository.findAll();
        assertThat(buildingDataDefinitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = buildingDataDefinitionRepository.findAll().size();
        // set the field null
        buildingDataDefinition.setLabel(null);

        // Create the BuildingDataDefinition, which fails.
        BuildingDataDefinitionDTO buildingDataDefinitionDTO = buildingDataDefinitionMapper.toDto(buildingDataDefinition);

        restBuildingDataDefinitionMockMvc.perform(post("/api/building-data-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDataDefinitionDTO)))
            .andExpect(status().isBadRequest());

        List<BuildingDataDefinition> buildingDataDefinitionList = buildingDataDefinitionRepository.findAll();
        assertThat(buildingDataDefinitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBuildingDataDefinitions() throws Exception {
        // Initialize the database
        buildingDataDefinitionRepository.saveAndFlush(buildingDataDefinition);

        // Get all the buildingDataDefinitionList
        restBuildingDataDefinitionMockMvc.perform(get("/api/building-data-definitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buildingDataDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }

    @Test
    @Transactional
    public void getBuildingDataDefinition() throws Exception {
        // Initialize the database
        buildingDataDefinitionRepository.saveAndFlush(buildingDataDefinition);

        // Get the buildingDataDefinition
        restBuildingDataDefinitionMockMvc.perform(get("/api/building-data-definitions/{id}", buildingDataDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(buildingDataDefinition.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBuildingDataDefinition() throws Exception {
        // Get the buildingDataDefinition
        restBuildingDataDefinitionMockMvc.perform(get("/api/building-data-definitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuildingDataDefinition() throws Exception {
        // Initialize the database
        buildingDataDefinitionRepository.saveAndFlush(buildingDataDefinition);
        int databaseSizeBeforeUpdate = buildingDataDefinitionRepository.findAll().size();

        // Update the buildingDataDefinition
        BuildingDataDefinition updatedBuildingDataDefinition = buildingDataDefinitionRepository.findOne(buildingDataDefinition.getId());
        // Disconnect from session so that the updates on updatedBuildingDataDefinition are not directly saved in db
        em.detach(updatedBuildingDataDefinition);
        updatedBuildingDataDefinition
            .label(UPDATED_LABEL);
        BuildingDataDefinitionDTO buildingDataDefinitionDTO = buildingDataDefinitionMapper.toDto(updatedBuildingDataDefinition);

        restBuildingDataDefinitionMockMvc.perform(put("/api/building-data-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDataDefinitionDTO)))
            .andExpect(status().isOk());

        // Validate the BuildingDataDefinition in the database
        List<BuildingDataDefinition> buildingDataDefinitionList = buildingDataDefinitionRepository.findAll();
        assertThat(buildingDataDefinitionList).hasSize(databaseSizeBeforeUpdate);
        BuildingDataDefinition testBuildingDataDefinition = buildingDataDefinitionList.get(buildingDataDefinitionList.size() - 1);
        assertThat(testBuildingDataDefinition.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingBuildingDataDefinition() throws Exception {
        int databaseSizeBeforeUpdate = buildingDataDefinitionRepository.findAll().size();

        // Create the BuildingDataDefinition
        BuildingDataDefinitionDTO buildingDataDefinitionDTO = buildingDataDefinitionMapper.toDto(buildingDataDefinition);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBuildingDataDefinitionMockMvc.perform(put("/api/building-data-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDataDefinitionDTO)))
            .andExpect(status().isCreated());

        // Validate the BuildingDataDefinition in the database
        List<BuildingDataDefinition> buildingDataDefinitionList = buildingDataDefinitionRepository.findAll();
        assertThat(buildingDataDefinitionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBuildingDataDefinition() throws Exception {
        // Initialize the database
        buildingDataDefinitionRepository.saveAndFlush(buildingDataDefinition);
        int databaseSizeBeforeDelete = buildingDataDefinitionRepository.findAll().size();

        // Get the buildingDataDefinition
        restBuildingDataDefinitionMockMvc.perform(delete("/api/building-data-definitions/{id}", buildingDataDefinition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BuildingDataDefinition> buildingDataDefinitionList = buildingDataDefinitionRepository.findAll();
        assertThat(buildingDataDefinitionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildingDataDefinition.class);
        BuildingDataDefinition buildingDataDefinition1 = new BuildingDataDefinition();
        buildingDataDefinition1.setId(1L);
        BuildingDataDefinition buildingDataDefinition2 = new BuildingDataDefinition();
        buildingDataDefinition2.setId(buildingDataDefinition1.getId());
        assertThat(buildingDataDefinition1).isEqualTo(buildingDataDefinition2);
        buildingDataDefinition2.setId(2L);
        assertThat(buildingDataDefinition1).isNotEqualTo(buildingDataDefinition2);
        buildingDataDefinition1.setId(null);
        assertThat(buildingDataDefinition1).isNotEqualTo(buildingDataDefinition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildingDataDefinitionDTO.class);
        BuildingDataDefinitionDTO buildingDataDefinitionDTO1 = new BuildingDataDefinitionDTO();
        buildingDataDefinitionDTO1.setId(1L);
        BuildingDataDefinitionDTO buildingDataDefinitionDTO2 = new BuildingDataDefinitionDTO();
        assertThat(buildingDataDefinitionDTO1).isNotEqualTo(buildingDataDefinitionDTO2);
        buildingDataDefinitionDTO2.setId(buildingDataDefinitionDTO1.getId());
        assertThat(buildingDataDefinitionDTO1).isEqualTo(buildingDataDefinitionDTO2);
        buildingDataDefinitionDTO2.setId(2L);
        assertThat(buildingDataDefinitionDTO1).isNotEqualTo(buildingDataDefinitionDTO2);
        buildingDataDefinitionDTO1.setId(null);
        assertThat(buildingDataDefinitionDTO1).isNotEqualTo(buildingDataDefinitionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(buildingDataDefinitionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(buildingDataDefinitionMapper.fromId(null)).isNull();
    }
}
