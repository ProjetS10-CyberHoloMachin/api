package fr.cyberholocampus.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.cyberholocampus.app.domain.Building;

import fr.cyberholocampus.app.repository.BuildingRepository;
import fr.cyberholocampus.app.web.rest.errors.BadRequestAlertException;
import fr.cyberholocampus.app.web.rest.util.HeaderUtil;
import fr.cyberholocampus.app.web.rest.util.PaginationUtil;
import fr.cyberholocampus.app.service.dto.BuildingDTO;
import fr.cyberholocampus.app.service.mapper.BuildingMapper;
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

/**
 * REST controller for managing Building.
 */
@RestController
@RequestMapping("/api")
public class BuildingResource {

    private final Logger log = LoggerFactory.getLogger(BuildingResource.class);

    private static final String ENTITY_NAME = "building";

    private final BuildingRepository buildingRepository;

    private final BuildingMapper buildingMapper;

    public BuildingResource(BuildingRepository buildingRepository, BuildingMapper buildingMapper) {
        this.buildingRepository = buildingRepository;
        this.buildingMapper = buildingMapper;
    }

    /**
     * POST  /buildings : Create a new building.
     *
     * @param buildingDTO the buildingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new buildingDTO, or with status 400 (Bad Request) if the building has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/buildings")
    @Timed
    public ResponseEntity<BuildingDTO> createBuilding(@Valid @RequestBody BuildingDTO buildingDTO) throws URISyntaxException {
        log.debug("REST request to save Building : {}", buildingDTO);
        if (buildingDTO.getId() != null) {
            throw new BadRequestAlertException("A new building cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Building building = buildingMapper.toEntity(buildingDTO);
        building = buildingRepository.save(building);
        BuildingDTO result = buildingMapper.toDto(building);
        return ResponseEntity.created(new URI("/api/buildings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /buildings : Updates an existing building.
     *
     * @param buildingDTO the buildingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated buildingDTO,
     * or with status 400 (Bad Request) if the buildingDTO is not valid,
     * or with status 500 (Internal Server Error) if the buildingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/buildings")
    @Timed
    public ResponseEntity<BuildingDTO> updateBuilding(@Valid @RequestBody BuildingDTO buildingDTO) throws URISyntaxException {
        log.debug("REST request to update Building : {}", buildingDTO);
        if (buildingDTO.getId() == null) {
            return createBuilding(buildingDTO);
        }
        Building building = buildingMapper.toEntity(buildingDTO);
        building = buildingRepository.save(building);
        BuildingDTO result = buildingMapper.toDto(building);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, buildingDTO.getId().toString()))
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
    public ResponseEntity<List<BuildingDTO>> getAllBuildings(Pageable pageable) {
        log.debug("REST request to get a page of Buildings");
        Page<Building> page = buildingRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/buildings");
        return new ResponseEntity<>(buildingMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /buildings/:id : get the "id" building.
     *
     * @param id the id of the buildingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the buildingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/buildings/{id}")
    @Timed
    public ResponseEntity<BuildingDTO> getBuilding(@PathVariable Long id) {
        log.debug("REST request to get Building : {}", id);
        Building building = buildingRepository.findOne(id);
        BuildingDTO buildingDTO = buildingMapper.toDto(building);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(buildingDTO));
    }

    /**
     * DELETE  /buildings/:id : delete the "id" building.
     *
     * @param id the id of the buildingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/buildings/{id}")
    @Timed
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
        log.debug("REST request to delete Building : {}", id);
        buildingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
