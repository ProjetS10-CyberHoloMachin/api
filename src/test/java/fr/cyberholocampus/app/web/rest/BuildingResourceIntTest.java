package fr.cyberholocampus.app.web.rest;

import fr.cyberholocampus.app.CyberholocampusApp;

import fr.cyberholocampus.app.domain.Building;
import fr.cyberholocampus.app.repository.BuildingRepository;
import fr.cyberholocampus.app.repository.search.BuildingSearchRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static fr.cyberholocampus.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BuildingResource REST controller.
 *
 * @see BuildingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CyberholocampusApp.class)
public class BuildingResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_MAPPING = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MAPPING = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_MAPPING_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MAPPING_CONTENT_TYPE = "image/png";

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingSearchRepository buildingSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBuildingMockMvc;

    private Building building;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BuildingResource buildingResource = new BuildingResource(buildingRepository, buildingSearchRepository);
        this.restBuildingMockMvc = MockMvcBuilders.standaloneSetup(buildingResource)
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
    public static Building createEntity(EntityManager em) {
        Building building = new Building()
            .name(DEFAULT_NAME)
            .mapping(DEFAULT_MAPPING)
            .mappingContentType(DEFAULT_MAPPING_CONTENT_TYPE);
        return building;
    }

    @Before
    public void initTest() {
        buildingSearchRepository.deleteAll();
        building = createEntity(em);
    }

    @Test
    @Transactional
    public void createBuilding() throws Exception {
        int databaseSizeBeforeCreate = buildingRepository.findAll().size();

        // Create the Building
        restBuildingMockMvc.perform(post("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(building)))
            .andExpect(status().isCreated());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeCreate + 1);
        Building testBuilding = buildingList.get(buildingList.size() - 1);
        assertThat(testBuilding.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBuilding.getMapping()).isEqualTo(DEFAULT_MAPPING);
        assertThat(testBuilding.getMappingContentType()).isEqualTo(DEFAULT_MAPPING_CONTENT_TYPE);

        // Validate the Building in Elasticsearch
        Building buildingEs = buildingSearchRepository.findOne(testBuilding.getId());
        assertThat(buildingEs).isEqualToIgnoringGivenFields(testBuilding);
    }

    @Test
    @Transactional
    public void createBuildingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = buildingRepository.findAll().size();

        // Create the Building with an existing ID
        building.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuildingMockMvc.perform(post("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(building)))
            .andExpect(status().isBadRequest());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = buildingRepository.findAll().size();
        // set the field null
        building.setName(null);

        // Create the Building, which fails.

        restBuildingMockMvc.perform(post("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(building)))
            .andExpect(status().isBadRequest());

        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBuildings() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList
        restBuildingMockMvc.perform(get("/api/buildings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(building.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].mappingContentType").value(hasItem(DEFAULT_MAPPING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].mapping").value(hasItem(Base64Utils.encodeToString(DEFAULT_MAPPING))));
    }

    @Test
    @Transactional
    public void getBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get the building
        restBuildingMockMvc.perform(get("/api/buildings/{id}", building.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(building.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.mappingContentType").value(DEFAULT_MAPPING_CONTENT_TYPE))
            .andExpect(jsonPath("$.mapping").value(Base64Utils.encodeToString(DEFAULT_MAPPING)));
    }

    @Test
    @Transactional
    public void getNonExistingBuilding() throws Exception {
        // Get the building
        restBuildingMockMvc.perform(get("/api/buildings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);
        buildingSearchRepository.save(building);
        int databaseSizeBeforeUpdate = buildingRepository.findAll().size();

        // Update the building
        Building updatedBuilding = buildingRepository.findOne(building.getId());
        // Disconnect from session so that the updates on updatedBuilding are not directly saved in db
        em.detach(updatedBuilding);
        updatedBuilding
            .name(UPDATED_NAME)
            .mapping(UPDATED_MAPPING)
            .mappingContentType(UPDATED_MAPPING_CONTENT_TYPE);

        restBuildingMockMvc.perform(put("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBuilding)))
            .andExpect(status().isOk());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeUpdate);
        Building testBuilding = buildingList.get(buildingList.size() - 1);
        assertThat(testBuilding.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBuilding.getMapping()).isEqualTo(UPDATED_MAPPING);
        assertThat(testBuilding.getMappingContentType()).isEqualTo(UPDATED_MAPPING_CONTENT_TYPE);

        // Validate the Building in Elasticsearch
        Building buildingEs = buildingSearchRepository.findOne(testBuilding.getId());
        assertThat(buildingEs).isEqualToIgnoringGivenFields(testBuilding);
    }

    @Test
    @Transactional
    public void updateNonExistingBuilding() throws Exception {
        int databaseSizeBeforeUpdate = buildingRepository.findAll().size();

        // Create the Building

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBuildingMockMvc.perform(put("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(building)))
            .andExpect(status().isCreated());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);
        buildingSearchRepository.save(building);
        int databaseSizeBeforeDelete = buildingRepository.findAll().size();

        // Get the building
        restBuildingMockMvc.perform(delete("/api/buildings/{id}", building.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean buildingExistsInEs = buildingSearchRepository.exists(building.getId());
        assertThat(buildingExistsInEs).isFalse();

        // Validate the database is empty
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);
        buildingSearchRepository.save(building);

        // Search the building
        restBuildingMockMvc.perform(get("/api/_search/buildings?query=id:" + building.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(building.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].mappingContentType").value(hasItem(DEFAULT_MAPPING_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].mapping").value(hasItem(Base64Utils.encodeToString(DEFAULT_MAPPING))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Building.class);
        Building building1 = new Building();
        building1.setId(1L);
        Building building2 = new Building();
        building2.setId(building1.getId());
        assertThat(building1).isEqualTo(building2);
        building2.setId(2L);
        assertThat(building1).isNotEqualTo(building2);
        building1.setId(null);
        assertThat(building1).isNotEqualTo(building2);
    }
}
