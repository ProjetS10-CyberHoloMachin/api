package fr.cyberholocampus.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.cyberholocampus.app.domain.InfoDefinition;

import fr.cyberholocampus.app.repository.InfoDefinitionRepository;
import fr.cyberholocampus.app.web.rest.errors.BadRequestAlertException;
import fr.cyberholocampus.app.web.rest.util.HeaderUtil;
import fr.cyberholocampus.app.web.rest.util.PaginationUtil;
import fr.cyberholocampus.app.service.dto.InfoDefinitionDTO;
import fr.cyberholocampus.app.service.mapper.InfoDefinitionMapper;
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
 * REST controller for managing InfoDefinition.
 */
@RestController
@RequestMapping("/api")
public class InfoDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(InfoDefinitionResource.class);

    private static final String ENTITY_NAME = "infoDefinition";

    private final InfoDefinitionRepository infoDefinitionRepository;

    private final InfoDefinitionMapper infoDefinitionMapper;

    public InfoDefinitionResource(InfoDefinitionRepository infoDefinitionRepository, InfoDefinitionMapper infoDefinitionMapper) {
        this.infoDefinitionRepository = infoDefinitionRepository;
        this.infoDefinitionMapper = infoDefinitionMapper;
    }

    /**
     * POST  /info-definitions : Create a new infoDefinition.
     *
     * @param infoDefinitionDTO the infoDefinitionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new infoDefinitionDTO, or with status 400 (Bad Request) if the infoDefinition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/info-definitions")
    @Timed
    public ResponseEntity<InfoDefinitionDTO> createInfoDefinition(@RequestBody InfoDefinitionDTO infoDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to save InfoDefinition : {}", infoDefinitionDTO);
        if (infoDefinitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new infoDefinition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InfoDefinition infoDefinition = infoDefinitionMapper.toEntity(infoDefinitionDTO);
        infoDefinition = infoDefinitionRepository.save(infoDefinition);
        InfoDefinitionDTO result = infoDefinitionMapper.toDto(infoDefinition);
        return ResponseEntity.created(new URI("/api/info-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /info-definitions : Updates an existing infoDefinition.
     *
     * @param infoDefinitionDTO the infoDefinitionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated infoDefinitionDTO,
     * or with status 400 (Bad Request) if the infoDefinitionDTO is not valid,
     * or with status 500 (Internal Server Error) if the infoDefinitionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/info-definitions")
    @Timed
    public ResponseEntity<InfoDefinitionDTO> updateInfoDefinition(@RequestBody InfoDefinitionDTO infoDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to update InfoDefinition : {}", infoDefinitionDTO);
        if (infoDefinitionDTO.getId() == null) {
            return createInfoDefinition(infoDefinitionDTO);
        }
        InfoDefinition infoDefinition = infoDefinitionMapper.toEntity(infoDefinitionDTO);
        infoDefinition = infoDefinitionRepository.save(infoDefinition);
        InfoDefinitionDTO result = infoDefinitionMapper.toDto(infoDefinition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, infoDefinitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /info-definitions : get all the infoDefinitions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of infoDefinitions in body
     */
    @GetMapping("/info-definitions")
    @Timed
    public ResponseEntity<List<InfoDefinitionDTO>> getAllInfoDefinitions(Pageable pageable) {
        log.debug("REST request to get a page of InfoDefinitions");
        Page<InfoDefinition> page = infoDefinitionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/info-definitions");
        return new ResponseEntity<>(infoDefinitionMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /info-definitions/:id : get the "id" infoDefinition.
     *
     * @param id the id of the infoDefinitionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the infoDefinitionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/info-definitions/{id}")
    @Timed
    public ResponseEntity<InfoDefinitionDTO> getInfoDefinition(@PathVariable Long id) {
        log.debug("REST request to get InfoDefinition : {}", id);
        InfoDefinition infoDefinition = infoDefinitionRepository.findOne(id);
        InfoDefinitionDTO infoDefinitionDTO = infoDefinitionMapper.toDto(infoDefinition);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(infoDefinitionDTO));
    }

    /**
     * DELETE  /info-definitions/:id : delete the "id" infoDefinition.
     *
     * @param id the id of the infoDefinitionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/info-definitions/{id}")
    @Timed
    public ResponseEntity<Void> deleteInfoDefinition(@PathVariable Long id) {
        log.debug("REST request to delete InfoDefinition : {}", id);
        infoDefinitionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
