package fr.cyberholocampus.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.cyberholocampus.app.domain.BuildingData;

import fr.cyberholocampus.app.repository.BuildingDataRepository;
import fr.cyberholocampus.app.web.rest.errors.BadRequestAlertException;
import fr.cyberholocampus.app.web.rest.util.HeaderUtil;
import fr.cyberholocampus.app.web.rest.util.PaginationUtil;
import fr.cyberholocampus.app.service.dto.BuildingDataDTO;
import fr.cyberholocampus.app.service.mapper.BuildingDataMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BuildingData.
 */
@RestController
@RequestMapping("/api")
public class BuildingDataResource {

    private final Logger log = LoggerFactory.getLogger(BuildingDataResource.class);

    private static final String ENTITY_NAME = "buildingData";

    private final BuildingDataRepository buildingDataRepository;

    private final BuildingDataMapper buildingDataMapper;

    public BuildingDataResource(BuildingDataRepository buildingDataRepository, BuildingDataMapper buildingDataMapper) {
        this.buildingDataRepository = buildingDataRepository;
        this.buildingDataMapper = buildingDataMapper;
    }

    /**
     * POST  /building-data : Create a new buildingData.
     *
     * @param buildingDataDTO the buildingDataDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new buildingDataDTO, or with status 400 (Bad Request) if the buildingData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/building-data")
    @Timed
    public ResponseEntity<BuildingDataDTO> createBuildingData(@RequestBody BuildingDataDTO buildingDataDTO) throws URISyntaxException {
        log.debug("REST request to save BuildingData : {}", buildingDataDTO);
        if (buildingDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new buildingData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuildingData buildingData = buildingDataMapper.toEntity(buildingDataDTO);
        buildingData = buildingDataRepository.save(buildingData);
        BuildingDataDTO result = buildingDataMapper.toDto(buildingData);
        return ResponseEntity.created(new URI("/api/building-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /building-data : Updates an existing buildingData.
     *
     * @param buildingDataDTO the buildingDataDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated buildingDataDTO,
     * or with status 400 (Bad Request) if the buildingDataDTO is not valid,
     * or with status 500 (Internal Server Error) if the buildingDataDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/building-data")
    @Timed
    public ResponseEntity<BuildingDataDTO> updateBuildingData(@RequestBody BuildingDataDTO buildingDataDTO) throws URISyntaxException {
        log.debug("REST request to update BuildingData : {}", buildingDataDTO);
        if (buildingDataDTO.getId() == null) {
            return createBuildingData(buildingDataDTO);
        }
        BuildingData buildingData = buildingDataMapper.toEntity(buildingDataDTO);
        buildingData = buildingDataRepository.save(buildingData);
        BuildingDataDTO result = buildingDataMapper.toDto(buildingData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, buildingDataDTO.getId().toString()))
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
    public ResponseEntity<List<BuildingDataDTO>> getAllBuildingData(Pageable pageable) {
        log.debug("REST request to get a page of BuildingData");
        Page<BuildingData> page = buildingDataRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/building-data");
        return new ResponseEntity<>(buildingDataMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /building-data/:id : get the "id" buildingData.
     *
     * @param id the id of the buildingDataDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the buildingDataDTO, or with status 404 (Not Found)
     */
    @GetMapping("/building-data/{id}")
    @Timed
    public ResponseEntity<BuildingDataDTO> getBuildingData(@PathVariable Long id) {
        log.debug("REST request to get BuildingData : {}", id);
        BuildingData buildingData = buildingDataRepository.findOne(id);
        BuildingDataDTO buildingDataDTO = buildingDataMapper.toDto(buildingData);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(buildingDataDTO));
    }

    /**
     * DELETE  /building-data/:id : delete the "id" buildingData.
     *
     * @param id the id of the buildingDataDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/building-data/{id}")
    @Timed
    public ResponseEntity<Void> deleteBuildingData(@PathVariable Long id) {
        log.debug("REST request to delete BuildingData : {}", id);
        buildingDataRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
