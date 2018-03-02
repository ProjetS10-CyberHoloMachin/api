package fr.cyberholocampus.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.cyberholocampus.app.domain.BuildingDataDefinition;

import fr.cyberholocampus.app.repository.BuildingDataDefinitionRepository;
import fr.cyberholocampus.app.web.rest.errors.BadRequestAlertException;
import fr.cyberholocampus.app.web.rest.util.HeaderUtil;
import fr.cyberholocampus.app.web.rest.util.PaginationUtil;
import fr.cyberholocampus.app.service.dto.BuildingDataDefinitionDTO;
import fr.cyberholocampus.app.service.mapper.BuildingDataDefinitionMapper;
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
 * REST controller for managing BuildingDataDefinition.
 */
@RestController
@RequestMapping("/api")
public class BuildingDataDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(BuildingDataDefinitionResource.class);

    private static final String ENTITY_NAME = "buildingDataDefinition";

    private final BuildingDataDefinitionRepository buildingDataDefinitionRepository;

    private final BuildingDataDefinitionMapper buildingDataDefinitionMapper;

    public BuildingDataDefinitionResource(BuildingDataDefinitionRepository buildingDataDefinitionRepository, BuildingDataDefinitionMapper buildingDataDefinitionMapper) {
        this.buildingDataDefinitionRepository = buildingDataDefinitionRepository;
        this.buildingDataDefinitionMapper = buildingDataDefinitionMapper;
    }

    /**
     * POST  /building-data-definitions : Create a new buildingDataDefinition.
     *
     * @param buildingDataDefinitionDTO the buildingDataDefinitionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new buildingDataDefinitionDTO, or with status 400 (Bad Request) if the buildingDataDefinition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/building-data-definitions")
    @Timed
    public ResponseEntity<BuildingDataDefinitionDTO> createBuildingDataDefinition(@Valid @RequestBody BuildingDataDefinitionDTO buildingDataDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to save BuildingDataDefinition : {}", buildingDataDefinitionDTO);
        if (buildingDataDefinitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new buildingDataDefinition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuildingDataDefinition buildingDataDefinition = buildingDataDefinitionMapper.toEntity(buildingDataDefinitionDTO);
        buildingDataDefinition = buildingDataDefinitionRepository.save(buildingDataDefinition);
        BuildingDataDefinitionDTO result = buildingDataDefinitionMapper.toDto(buildingDataDefinition);
        return ResponseEntity.created(new URI("/api/building-data-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /building-data-definitions : Updates an existing buildingDataDefinition.
     *
     * @param buildingDataDefinitionDTO the buildingDataDefinitionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated buildingDataDefinitionDTO,
     * or with status 400 (Bad Request) if the buildingDataDefinitionDTO is not valid,
     * or with status 500 (Internal Server Error) if the buildingDataDefinitionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/building-data-definitions")
    @Timed
    public ResponseEntity<BuildingDataDefinitionDTO> updateBuildingDataDefinition(@Valid @RequestBody BuildingDataDefinitionDTO buildingDataDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to update BuildingDataDefinition : {}", buildingDataDefinitionDTO);
        if (buildingDataDefinitionDTO.getId() == null) {
            return createBuildingDataDefinition(buildingDataDefinitionDTO);
        }
        BuildingDataDefinition buildingDataDefinition = buildingDataDefinitionMapper.toEntity(buildingDataDefinitionDTO);
        buildingDataDefinition = buildingDataDefinitionRepository.save(buildingDataDefinition);
        BuildingDataDefinitionDTO result = buildingDataDefinitionMapper.toDto(buildingDataDefinition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, buildingDataDefinitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /building-data-definitions : get all the buildingDataDefinitions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of buildingDataDefinitions in body
     */
    @GetMapping("/building-data-definitions")
    @Timed
    public ResponseEntity<List<BuildingDataDefinitionDTO>> getAllBuildingDataDefinitions(Pageable pageable) {
        log.debug("REST request to get a page of BuildingDataDefinitions");
        Page<BuildingDataDefinition> page = buildingDataDefinitionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/building-data-definitions");
        return new ResponseEntity<>(buildingDataDefinitionMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /building-data-definitions/:id : get the "id" buildingDataDefinition.
     *
     * @param id the id of the buildingDataDefinitionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the buildingDataDefinitionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/building-data-definitions/{id}")
    @Timed
    public ResponseEntity<BuildingDataDefinitionDTO> getBuildingDataDefinition(@PathVariable Long id) {
        log.debug("REST request to get BuildingDataDefinition : {}", id);
        BuildingDataDefinition buildingDataDefinition = buildingDataDefinitionRepository.findOne(id);
        BuildingDataDefinitionDTO buildingDataDefinitionDTO = buildingDataDefinitionMapper.toDto(buildingDataDefinition);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(buildingDataDefinitionDTO));
    }

    /**
     * DELETE  /building-data-definitions/:id : delete the "id" buildingDataDefinition.
     *
     * @param id the id of the buildingDataDefinitionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/building-data-definitions/{id}")
    @Timed
    public ResponseEntity<Void> deleteBuildingDataDefinition(@PathVariable Long id) {
        log.debug("REST request to delete BuildingDataDefinition : {}", id);
        buildingDataDefinitionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
