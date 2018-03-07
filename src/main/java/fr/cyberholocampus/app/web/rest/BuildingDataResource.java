package fr.cyberholocampus.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.cyberholocampus.app.domain.BuildingData;

import fr.cyberholocampus.app.repository.BuildingDataRepository;
import fr.cyberholocampus.app.repository.search.BuildingDataSearchRepository;
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
 * REST controller for managing BuildingData.
 */
@RestController
@RequestMapping("/api")
public class BuildingDataResource {

    private final Logger log = LoggerFactory.getLogger(BuildingDataResource.class);

    private static final String ENTITY_NAME = "buildingData";

    private final BuildingDataRepository buildingDataRepository;

    private final BuildingDataSearchRepository buildingDataSearchRepository;

    public BuildingDataResource(BuildingDataRepository buildingDataRepository, BuildingDataSearchRepository buildingDataSearchRepository) {
        this.buildingDataRepository = buildingDataRepository;
        this.buildingDataSearchRepository = buildingDataSearchRepository;
    }

    /**
     * POST  /building-data : Create a new buildingData.
     *
     * @param buildingData the buildingData to create
     * @return the ResponseEntity with status 201 (Created) and with body the new buildingData, or with status 400 (Bad Request) if the buildingData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/building-data")
    @Timed
    public ResponseEntity<BuildingData> createBuildingData(@Valid @RequestBody BuildingData buildingData) throws URISyntaxException {
        log.debug("REST request to save BuildingData : {}", buildingData);
        if (buildingData.getId() != null) {
            throw new BadRequestAlertException("A new buildingData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuildingData result = buildingDataRepository.save(buildingData);
        buildingDataSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/building-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /building-data : Updates an existing buildingData.
     *
     * @param buildingData the buildingData to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated buildingData,
     * or with status 400 (Bad Request) if the buildingData is not valid,
     * or with status 500 (Internal Server Error) if the buildingData couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/building-data")
    @Timed
    public ResponseEntity<BuildingData> updateBuildingData(@Valid @RequestBody BuildingData buildingData) throws URISyntaxException {
        log.debug("REST request to update BuildingData : {}", buildingData);
        if (buildingData.getId() == null) {
            return createBuildingData(buildingData);
        }
        BuildingData result = buildingDataRepository.save(buildingData);
        buildingDataSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, buildingData.getId().toString()))
            .body(result);
    }

    /**
     * GET  /building-data : get all the buildingData.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of buildingData in body
     */
    @GetMapping("/building-data")
    @Timed
    public ResponseEntity<List<BuildingData>> getAllBuildingData(Pageable pageable) {
        log.debug("REST request to get a page of BuildingData");
        Page<BuildingData> page = buildingDataRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/building-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /building-data/:id : get the "id" buildingData.
     *
     * @param id the id of the buildingData to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the buildingData, or with status 404 (Not Found)
     */
    @GetMapping("/building-data/{id}")
    @Timed
    public ResponseEntity<BuildingData> getBuildingData(@PathVariable Long id) {
        log.debug("REST request to get BuildingData : {}", id);
        BuildingData buildingData = buildingDataRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(buildingData));
    }

    /**
     * DELETE  /building-data/:id : delete the "id" buildingData.
     *
     * @param id the id of the buildingData to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/building-data/{id}")
    @Timed
    public ResponseEntity<Void> deleteBuildingData(@PathVariable Long id) {
        log.debug("REST request to delete BuildingData : {}", id);
        buildingDataRepository.delete(id);
        buildingDataSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/building-data?query=:query : search for the buildingData corresponding
     * to the query.
     *
     * @param query the query of the buildingData search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/building-data")
    @Timed
    public ResponseEntity<List<BuildingData>> searchBuildingData(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BuildingData for query {}", query);
        Page<BuildingData> page = buildingDataSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/building-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
