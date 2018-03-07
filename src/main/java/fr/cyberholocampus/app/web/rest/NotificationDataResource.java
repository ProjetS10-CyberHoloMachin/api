package fr.cyberholocampus.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.cyberholocampus.app.domain.NotificationData;

import fr.cyberholocampus.app.repository.NotificationDataRepository;
import fr.cyberholocampus.app.repository.search.NotificationDataSearchRepository;
import fr.cyberholocampus.app.web.rest.errors.BadRequestAlertException;
import fr.cyberholocampus.app.web.rest.util.HeaderUtil;
import fr.cyberholocampus.app.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing NotificationData.
 */
@RestController
@RequestMapping("/api")
public class NotificationDataResource {

    private final Logger log = LoggerFactory.getLogger(NotificationDataResource.class);

    private static final String ENTITY_NAME = "notificationData";

    private final NotificationDataRepository notificationDataRepository;

    private final NotificationDataSearchRepository notificationDataSearchRepository;

    public NotificationDataResource(NotificationDataRepository notificationDataRepository, NotificationDataSearchRepository notificationDataSearchRepository) {
        this.notificationDataRepository = notificationDataRepository;
        this.notificationDataSearchRepository = notificationDataSearchRepository;
    }

    /**
     * POST  /notification-data : Create a new notificationData.
     *
     * @param notificationData the notificationData to create
     * @return the ResponseEntity with status 201 (Created) and with body the new notificationData, or with status 400 (Bad Request) if the notificationData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/notification-data")
    @Timed
    public ResponseEntity<NotificationData> createNotificationData(@Valid @RequestBody NotificationData notificationData) throws URISyntaxException {
        log.debug("REST request to save NotificationData : {}", notificationData);
        if (notificationData.getId() != null) {
            throw new BadRequestAlertException("A new notificationData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotificationData result = notificationDataRepository.save(notificationData);
        notificationDataSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/notification-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notification-data : Updates an existing notificationData.
     *
     * @param notificationData the notificationData to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated notificationData,
     * or with status 400 (Bad Request) if the notificationData is not valid,
     * or with status 500 (Internal Server Error) if the notificationData couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/notification-data")
    @Timed
    public ResponseEntity<NotificationData> updateNotificationData(@Valid @RequestBody NotificationData notificationData) throws URISyntaxException {
        log.debug("REST request to update NotificationData : {}", notificationData);
        if (notificationData.getId() == null) {
            return createNotificationData(notificationData);
        }
        NotificationData result = notificationDataRepository.save(notificationData);
        notificationDataSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, notificationData.getId().toString()))
            .body(result);
    }

    /**
     * GET  /notification-data : get all the notificationData.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of notificationData in body
     */
    @GetMapping("/notification-data")
    @Timed
    public ResponseEntity<List<NotificationData>> getAllNotificationData(Pageable pageable) {
        log.debug("REST request to get a page of NotificationData");
        Page<NotificationData> page = notificationDataRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notification-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /notification-data/:id : get the "id" notificationData.
     *
     * @param id the id of the notificationData to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notificationData, or with status 404 (Not Found)
     */
    @GetMapping("/notification-data/{id}")
    @Timed
    public ResponseEntity<NotificationData> getNotificationData(@PathVariable Long id) {
        log.debug("REST request to get NotificationData : {}", id);
        NotificationData notificationData = notificationDataRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notificationData));
    }

    /**
     * DELETE  /notification-data/:id : delete the "id" notificationData.
     *
     * @param id the id of the notificationData to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/notification-data/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotificationData(@PathVariable Long id) {
        log.debug("REST request to delete NotificationData : {}", id);
        notificationDataRepository.delete(id);
        notificationDataSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/notification-data?query=:query : search for the notificationData corresponding
     * to the query.
     *
     * @param query the query of the notificationData search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/notification-data")
    @Timed
    public ResponseEntity<List<NotificationData>> searchNotificationData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of NotificationData for query {}", query);
        Page<NotificationData> page = notificationDataSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/notification-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
