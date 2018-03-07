package fr.cyberholocampus.app.web.rest;

import fr.cyberholocampus.app.CyberholocampusApp;

import fr.cyberholocampus.app.domain.NotificationData;
import fr.cyberholocampus.app.repository.NotificationDataRepository;
import fr.cyberholocampus.app.repository.search.NotificationDataSearchRepository;
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
 * Test class for the NotificationDataResource REST controller.
 *
 * @see NotificationDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CyberholocampusApp.class)
public class NotificationDataResourceIntTest {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private NotificationDataRepository notificationDataRepository;

    @Autowired
    private NotificationDataSearchRepository notificationDataSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNotificationDataMockMvc;

    private NotificationData notificationData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NotificationDataResource notificationDataResource = new NotificationDataResource(notificationDataRepository, notificationDataSearchRepository);
        this.restNotificationDataMockMvc = MockMvcBuilders.standaloneSetup(notificationDataResource)
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
    public static NotificationData createEntity(EntityManager em) {
        NotificationData notificationData = new NotificationData()
            .label(DEFAULT_LABEL)
            .description(DEFAULT_DESCRIPTION);
        return notificationData;
    }

    @Before
    public void initTest() {
        notificationDataSearchRepository.deleteAll();
        notificationData = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotificationData() throws Exception {
        int databaseSizeBeforeCreate = notificationDataRepository.findAll().size();

        // Create the NotificationData
        restNotificationDataMockMvc.perform(post("/api/notification-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationData)))
            .andExpect(status().isCreated());

        // Validate the NotificationData in the database
        List<NotificationData> notificationDataList = notificationDataRepository.findAll();
        assertThat(notificationDataList).hasSize(databaseSizeBeforeCreate + 1);
        NotificationData testNotificationData = notificationDataList.get(notificationDataList.size() - 1);
        assertThat(testNotificationData.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testNotificationData.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the NotificationData in Elasticsearch
        NotificationData notificationDataEs = notificationDataSearchRepository.findOne(testNotificationData.getId());
        assertThat(notificationDataEs).isEqualToIgnoringGivenFields(testNotificationData);
    }

    @Test
    @Transactional
    public void createNotificationDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificationDataRepository.findAll().size();

        // Create the NotificationData with an existing ID
        notificationData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationDataMockMvc.perform(post("/api/notification-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationData)))
            .andExpect(status().isBadRequest());

        // Validate the NotificationData in the database
        List<NotificationData> notificationDataList = notificationDataRepository.findAll();
        assertThat(notificationDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationDataRepository.findAll().size();
        // set the field null
        notificationData.setLabel(null);

        // Create the NotificationData, which fails.

        restNotificationDataMockMvc.perform(post("/api/notification-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationData)))
            .andExpect(status().isBadRequest());

        List<NotificationData> notificationDataList = notificationDataRepository.findAll();
        assertThat(notificationDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationDataRepository.findAll().size();
        // set the field null
        notificationData.setDescription(null);

        // Create the NotificationData, which fails.

        restNotificationDataMockMvc.perform(post("/api/notification-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationData)))
            .andExpect(status().isBadRequest());

        List<NotificationData> notificationDataList = notificationDataRepository.findAll();
        assertThat(notificationDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotificationData() throws Exception {
        // Initialize the database
        notificationDataRepository.saveAndFlush(notificationData);

        // Get all the notificationDataList
        restNotificationDataMockMvc.perform(get("/api/notification-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationData.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getNotificationData() throws Exception {
        // Initialize the database
        notificationDataRepository.saveAndFlush(notificationData);

        // Get the notificationData
        restNotificationDataMockMvc.perform(get("/api/notification-data/{id}", notificationData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(notificationData.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNotificationData() throws Exception {
        // Get the notificationData
        restNotificationDataMockMvc.perform(get("/api/notification-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotificationData() throws Exception {
        // Initialize the database
        notificationDataRepository.saveAndFlush(notificationData);
        notificationDataSearchRepository.save(notificationData);
        int databaseSizeBeforeUpdate = notificationDataRepository.findAll().size();

        // Update the notificationData
        NotificationData updatedNotificationData = notificationDataRepository.findOne(notificationData.getId());
        // Disconnect from session so that the updates on updatedNotificationData are not directly saved in db
        em.detach(updatedNotificationData);
        updatedNotificationData
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION);

        restNotificationDataMockMvc.perform(put("/api/notification-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNotificationData)))
            .andExpect(status().isOk());

        // Validate the NotificationData in the database
        List<NotificationData> notificationDataList = notificationDataRepository.findAll();
        assertThat(notificationDataList).hasSize(databaseSizeBeforeUpdate);
        NotificationData testNotificationData = notificationDataList.get(notificationDataList.size() - 1);
        assertThat(testNotificationData.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testNotificationData.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the NotificationData in Elasticsearch
        NotificationData notificationDataEs = notificationDataSearchRepository.findOne(testNotificationData.getId());
        assertThat(notificationDataEs).isEqualToIgnoringGivenFields(testNotificationData);
    }

    @Test
    @Transactional
    public void updateNonExistingNotificationData() throws Exception {
        int databaseSizeBeforeUpdate = notificationDataRepository.findAll().size();

        // Create the NotificationData

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNotificationDataMockMvc.perform(put("/api/notification-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificationData)))
            .andExpect(status().isCreated());

        // Validate the NotificationData in the database
        List<NotificationData> notificationDataList = notificationDataRepository.findAll();
        assertThat(notificationDataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNotificationData() throws Exception {
        // Initialize the database
        notificationDataRepository.saveAndFlush(notificationData);
        notificationDataSearchRepository.save(notificationData);
        int databaseSizeBeforeDelete = notificationDataRepository.findAll().size();

        // Get the notificationData
        restNotificationDataMockMvc.perform(delete("/api/notification-data/{id}", notificationData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean notificationDataExistsInEs = notificationDataSearchRepository.exists(notificationData.getId());
        assertThat(notificationDataExistsInEs).isFalse();

        // Validate the database is empty
        List<NotificationData> notificationDataList = notificationDataRepository.findAll();
        assertThat(notificationDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchNotificationData() throws Exception {
        // Initialize the database
        notificationDataRepository.saveAndFlush(notificationData);
        notificationDataSearchRepository.save(notificationData);

        // Search the notificationData
        restNotificationDataMockMvc.perform(get("/api/_search/notification-data?query=id:" + notificationData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationData.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationData.class);
        NotificationData notificationData1 = new NotificationData();
        notificationData1.setId(1L);
        NotificationData notificationData2 = new NotificationData();
        notificationData2.setId(notificationData1.getId());
        assertThat(notificationData1).isEqualTo(notificationData2);
        notificationData2.setId(2L);
        assertThat(notificationData1).isNotEqualTo(notificationData2);
        notificationData1.setId(null);
        assertThat(notificationData1).isNotEqualTo(notificationData2);
    }
}
