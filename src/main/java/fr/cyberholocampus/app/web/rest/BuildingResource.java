package fr.cyberholocampus.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.cyberholocampus.app.domain.Building;

import fr.cyberholocampus.app.repository.BuildingRepository;
import fr.cyberholocampus.app.repository.search.BuildingSearchRepository;
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
 * REST controller for managing Building.
 */
@RestController
@RequestMapping("/api")
public class BuildingResource {

    private final Logger log = LoggerFactory.getLogger(BuildingResource.class);

    private static final String ENTITY_NAME = "building";

    private final BuildingRepository buildingRepository;

    private final BuildingSearchRepository buildingSearchRepository;

    public BuildingResource(BuildingRepository buildingRepository, BuildingSearchRepository buildingSearchRepository) {
        this.buildingRepository = buildingRepository;
        this.buildingSearchRepository = buildingSearchRepository;
    }

    /**
     * POST  /buildings : Create a new building.
     *
     * @param building the building to create
     * @return the ResponseEntity with status 201 (Created) and with body the new building, or with status 400 (Bad Request) if the building has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/buildings")
    @Timed
    public ResponseEntity<Building> createBuilding(@Valid @RequestBody Building building) throws URISyntaxException {
        log.debug("REST request to save Building : {}", building);
        if (building.getId() != null) {
            throw new BadRequestAlertException("A new building cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Building result = buildingRepository.save(building);
        buildingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/buildings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /buildings : Updates an existing building.
     *
     * @param building the building to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated building,
     * or with status 400 (Bad Request) if the building is not valid,
     * or with status 500 (Internal Server Error) if the building couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/buildings")
    @Timed
    public ResponseEntity<Building> updateBuilding(@Valid @RequestBody Building building) throws URISyntaxException {
        log.debug("REST request to update Building : {}", building);
        if (building.getId() == null) {
            return createBuilding(building);
        }
        Building result = buildingRepository.save(building);
        buildingSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, building.getId().toString()))
            .body(result);
    }

    /**
     * GET  /buildings : get all the buildings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of buildings in body
     */
    @GetMapping("/buildings")
    @Timed
    public ResponseEntity<List<Building>> getAllBuildings(Pageable pageable) {
        log.debug("REST request to get a page of Buildings");
        Page<Building> page = buildingRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/buildings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /buildings/:id : get the "id" building.
     *
     * @param id the id of the building to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the building, or with status 404 (Not Found)
     */
    @GetMapping("/buildings/{id}")
    @Timed
    public ResponseEntity<Building> getBuilding(@PathVariable Long id) {
        log.debug("REST request to get Building : {}", id);
        Building building = buildingRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(building));
    }

    /**
     * DELETE  /buildings/:id : delete the "id" building.
     *
     * @param id the id of the building to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/buildings/{id}")
    @Timed
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
        log.debug("REST request to delete Building : {}", id);
        buildingRepository.delete(id);
        buildingSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/buildings?query=:query : search for the building corresponding
     * to the query.
     *
     * @param query the query of the building search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/buildings")
    @Timed
    public ResponseEntity<List<Building>> searchBuildings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Buildings for query {}", query);
        Page<Building> page = buildingSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/buildings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
