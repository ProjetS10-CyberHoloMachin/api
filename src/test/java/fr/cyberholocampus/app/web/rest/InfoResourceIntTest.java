package fr.cyberholocampus.app.web.rest;

import fr.cyberholocampus.app.MicroApp;

import fr.cyberholocampus.app.domain.Info;
import fr.cyberholocampus.app.repository.InfoRepository;
import fr.cyberholocampus.app.service.dto.InfoDTO;
import fr.cyberholocampus.app.service.mapper.InfoMapper;
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
 * Test class for the InfoResource REST controller.
 *
 * @see InfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MicroApp.class)
public class InfoResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private InfoRepository infoRepository;

    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInfoMockMvc;

    private Info info;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InfoResource infoResource = new InfoResource(infoRepository, infoMapper);
        this.restInfoMockMvc = MockMvcBuilders.standaloneSetup(infoResource)
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
    public static Info createEntity(EntityManager em) {
        Info info = new Info()
            .description(DEFAULT_DESCRIPTION);
        return info;
    }

    @Before
    public void initTest() {
        info = createEntity(em);
    }

    @Test
    @Transactional
    public void createInfo() throws Exception {
        int databaseSizeBeforeCreate = infoRepository.findAll().size();

        // Create the Info
        InfoDTO infoDTO = infoMapper.toDto(info);
        restInfoMockMvc.perform(post("/api/infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoDTO)))
            .andExpect(status().isCreated());

        // Validate the Info in the database
        List<Info> infoList = infoRepository.findAll();
        assertThat(infoList).hasSize(databaseSizeBeforeCreate + 1);
        Info testInfo = infoList.get(infoList.size() - 1);
        assertThat(testInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = infoRepository.findAll().size();

        // Create the Info with an existing ID
        info.setId(1L);
        InfoDTO infoDTO = infoMapper.toDto(info);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfoMockMvc.perform(post("/api/infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Info in the database
        List<Info> infoList = infoRepository.findAll();
        assertThat(infoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInfos() throws Exception {
        // Initialize the database
        infoRepository.saveAndFlush(info);

        // Get all the infoList
        restInfoMockMvc.perform(get("/api/infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(info.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getInfo() throws Exception {
        // Initialize the database
        infoRepository.saveAndFlush(info);

        // Get the info
        restInfoMockMvc.perform(get("/api/infos/{id}", info.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(info.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInfo() throws Exception {
        // Get the info
        restInfoMockMvc.perform(get("/api/infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInfo() throws Exception {
        // Initialize the database
        infoRepository.saveAndFlush(info);
        int databaseSizeBeforeUpdate = infoRepository.findAll().size();

        // Update the info
        Info updatedInfo = infoRepository.findOne(info.getId());
        // Disconnect from session so that the updates on updatedInfo are not directly saved in db
        em.detach(updatedInfo);
        updatedInfo
            .description(UPDATED_DESCRIPTION);
        InfoDTO infoDTO = infoMapper.toDto(updatedInfo);

        restInfoMockMvc.perform(put("/api/infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoDTO)))
            .andExpect(status().isOk());

        // Validate the Info in the database
        List<Info> infoList = infoRepository.findAll();
        assertThat(infoList).hasSize(databaseSizeBeforeUpdate);
        Info testInfo = infoList.get(infoList.size() - 1);
        assertThat(testInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingInfo() throws Exception {
        int databaseSizeBeforeUpdate = infoRepository.findAll().size();

        // Create the Info
        InfoDTO infoDTO = infoMapper.toDto(info);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInfoMockMvc.perform(put("/api/infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoDTO)))
            .andExpect(status().isCreated());

        // Validate the Info in the database
        List<Info> infoList = infoRepository.findAll();
        assertThat(infoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInfo() throws Exception {
        // Initialize the database
        infoRepository.saveAndFlush(info);
        int databaseSizeBeforeDelete = infoRepository.findAll().size();

        // Get the info
        restInfoMockMvc.perform(delete("/api/infos/{id}", info.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Info> infoList = infoRepository.findAll();
        assertThat(infoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Info.class);
        Info info1 = new Info();
        info1.setId(1L);
        Info info2 = new Info();
        info2.setId(info1.getId());
        assertThat(info1).isEqualTo(info2);
        info2.setId(2L);
        assertThat(info1).isNotEqualTo(info2);
        info1.setId(null);
        assertThat(info1).isNotEqualTo(info2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoDTO.class);
        InfoDTO infoDTO1 = new InfoDTO();
        infoDTO1.setId(1L);
        InfoDTO infoDTO2 = new InfoDTO();
        assertThat(infoDTO1).isNotEqualTo(infoDTO2);
        infoDTO2.setId(infoDTO1.getId());
        assertThat(infoDTO1).isEqualTo(infoDTO2);
        infoDTO2.setId(2L);
        assertThat(infoDTO1).isNotEqualTo(infoDTO2);
        infoDTO1.setId(null);
        assertThat(infoDTO1).isNotEqualTo(infoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(infoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(infoMapper.fromId(null)).isNull();
    }
}
