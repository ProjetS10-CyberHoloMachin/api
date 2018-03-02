package fr.cyberholocampus.app.web.rest;

import fr.cyberholocampus.app.MicroApp;

import fr.cyberholocampus.app.domain.BuildingData;
import fr.cyberholocampus.app.repository.BuildingDataRepository;
import fr.cyberholocampus.app.service.dto.BuildingDataDTO;
import fr.cyberholocampus.app.service.mapper.BuildingDataMapper;
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
 * Test class for the BuildingDataResource REST controller.
 *
 * @see BuildingDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MicroApp.class)
public class BuildingDataResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private BuildingDataRepository buildingDataRepository;

    @Autowired
    private BuildingDataMapper buildingDataMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBuildingDataMockMvc;

    private BuildingData buildingData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BuildingDataResource buildingDataResource = new BuildingDataResource(buildingDataRepository, buildingDataMapper);
        this.restBuildingDataMockMvc = MockMvcBuilders.standaloneSetup(buildingDataResource)
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
    public static BuildingData createEntity(EntityManager em) {
        BuildingData buildingData = new BuildingData()
            .description(DEFAULT_DESCRIPTION);
        return buildingData;
    }

    @Before
    public void initTest() {
        buildingData = createEntity(em);
    }

    @Test
    @Transactional
    public void createBuildingData() throws Exception {
        int databaseSizeBeforeCreate = buildingDataRepository.findAll().size();

        // Create the BuildingData
        BuildingDataDTO buildingDataDTO = buildingDataMapper.toDto(buildingData);
        restBuildingDataMockMvc.perform(post("/api/building-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDataDTO)))
            .andExpect(status().isCreated());

        // Validate the BuildingData in the database
        List<BuildingData> buildingDataList = buildingDataRepository.findAll();
        assertThat(buildingDataList).hasSize(databaseSizeBeforeCreate + 1);
        BuildingData testBuildingData = buildingDataList.get(buildingDataList.size() - 1);
        assertThat(testBuildingData.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createBuildingDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = buildingDataRepository.findAll().size();

        // Create the BuildingData with an existing ID
        buildingData.setId(1L);
        BuildingDataDTO buildingDataDTO = buildingDataMapper.toDto(buildingData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuildingDataMockMvc.perform(post("/api/building-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BuildingData in the database
        List<BuildingData> buildingDataList = buildingDataRepository.findAll();
        assertThat(buildingDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBuildingData() throws Exception {
        // Initialize the database
        buildingDataRepository.saveAndFlush(buildingData);

        // Get all the buildingDataList
        restBuildingDataMockMvc.perform(get("/api/building-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buildingData.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getBuildingData() throws Exception {
        // Initialize the database
        buildingDataRepository.saveAndFlush(buildingData);

        // Get the buildingData
        restBuildingDataMockMvc.perform(get("/api/building-data/{id}", buildingData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(buildingData.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBuildingData() throws Exception {
        // Get the buildingData
        restBuildingDataMockMvc.perform(get("/api/building-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuildingData() throws Exception {
        // Initialize the database
        buildingDataRepository.saveAndFlush(buildingData);
        int databaseSizeBeforeUpdate = buildingDataRepository.findAll().size();

        // Update the buildingData
        BuildingData updatedBuildingData = buildingDataRepository.findOne(buildingData.getId());
        // Disconnect from session so that the updates on updatedBuildingData are not directly saved in db
        em.detach(updatedBuildingData);
        updatedBuildingData
            .description(UPDATED_DESCRIPTION);
        BuildingDataDTO buildingDataDTO = buildingDataMapper.toDto(updatedBuildingData);

        restBuildingDataMockMvc.perform(put("/api/building-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDataDTO)))
            .andExpect(status().isOk());

        // Validate the BuildingData in the database
        List<BuildingData> buildingDataList = buildingDataRepository.findAll();
        assertThat(buildingDataList).hasSize(databaseSizeBeforeUpdate);
        BuildingData testBuildingData = buildingDataList.get(buildingDataList.size() - 1);
        assertThat(testBuildingData.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingBuildingData() throws Exception {
        int databaseSizeBeforeUpdate = buildingDataRepository.findAll().size();

        // Create the BuildingData
        BuildingDataDTO buildingDataDTO = buildingDataMapper.toDto(buildingData);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBuildingDataMockMvc.perform(put("/api/building-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingDataDTO)))
            .andExpect(status().isCreated());

        // Validate the BuildingData in the database
        List<BuildingData> buildingDataList = buildingDataRepository.findAll();
        assertThat(buildingDataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBuildingData() throws Exception {
        // Initialize the database
        buildingDataRepository.saveAndFlush(buildingData);
        int databaseSizeBeforeDelete = buildingDataRepository.findAll().size();

        // Get the buildingData
        restBuildingDataMockMvc.perform(delete("/api/building-data/{id}", buildingData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BuildingData> buildingDataList = buildingDataRepository.findAll();
        assertThat(buildingDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildingData.class);
        BuildingData buildingData1 = new BuildingData();
        buildingData1.setId(1L);
        BuildingData buildingData2 = new BuildingData();
        buildingData2.setId(buildingData1.getId());
        assertThat(buildingData1).isEqualTo(buildingData2);
        buildingData2.setId(2L);
        assertThat(buildingData1).isNotEqualTo(buildingData2);
        buildingData1.setId(null);
        assertThat(buildingData1).isNotEqualTo(buildingData2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildingDataDTO.class);
        BuildingDataDTO buildingDataDTO1 = new BuildingDataDTO();
        buildingDataDTO1.setId(1L);
        BuildingDataDTO buildingDataDTO2 = new BuildingDataDTO();
        assertThat(buildingDataDTO1).isNotEqualTo(buildingDataDTO2);
        buildingDataDTO2.setId(buildingDataDTO1.getId());
        assertThat(buildingDataDTO1).isEqualTo(buildingDataDTO2);
        buildingDataDTO2.setId(2L);
        assertThat(buildingDataDTO1).isNotEqualTo(buildingDataDTO2);
        buildingDataDTO1.setId(null);
        assertThat(buildingDataDTO1).isNotEqualTo(buildingDataDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(buildingDataMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(buildingDataMapper.fromId(null)).isNull();
    }
}
