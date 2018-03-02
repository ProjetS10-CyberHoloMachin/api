package fr.cyberholocampus.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.cyberholocampus.app.domain.Affectation;

import fr.cyberholocampus.app.repository.AffectationRepository;
import fr.cyberholocampus.app.web.rest.errors.BadRequestAlertException;
import fr.cyberholocampus.app.web.rest.util.HeaderUtil;
import fr.cyberholocampus.app.web.rest.util.PaginationUtil;
import fr.cyberholocampus.app.service.dto.AffectationDTO;
import fr.cyberholocampus.app.service.mapper.AffectationMapper;
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
 * REST controller for managing Affectation.
 */
@RestController
@RequestMapping("/api")
public class AffectationResource {

    private final Logger log = LoggerFactory.getLogger(AffectationResource.class);

    private static final String ENTITY_NAME = "affectation";

    private final AffectationRepository affectationRepository;

    private final AffectationMapper affectationMapper;

    public AffectationResource(AffectationRepository affectationRepository, AffectationMapper affectationMapper) {
        this.affectationRepository = affectationRepository;
        this.affectationMapper = affectationMapper;
    }

    /**
     * POST  /affectations : Create a new affectation.
     *
     * @param affectationDTO the affectationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new affectationDTO, or with status 400 (Bad Request) if the affectation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/affectations")
    @Timed
    public ResponseEntity<AffectationDTO> createAffectation(@Valid @RequestBody AffectationDTO affectationDTO) throws URISyntaxException {
        log.debug("REST request to save Affectation : {}", affectationDTO);
        if (affectationDTO.getId() != null) {
            throw new BadRequestAlertException("A new affectation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Affectation affectation = affectationMapper.toEntity(affectationDTO);
        affectation = affectationRepository.save(affectation);
        AffectationDTO result = affectationMapper.toDto(affectation);
        return ResponseEntity.created(new URI("/api/affectations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /affectations : Updates an existing affectation.
     *
     * @param affectationDTO the affectationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated affectationDTO,
     * or with status 400 (Bad Request) if the affectationDTO is not valid,
     * or with status 500 (Internal Server Error) if the affectationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/affectations")
    @Timed
    public ResponseEntity<AffectationDTO> updateAffectation(@Valid @RequestBody AffectationDTO affectationDTO) throws URISyntaxException {
        log.debug("REST request to update Affectation : {}", affectationDTO);
        if (affectationDTO.getId() == null) {
            return createAffectation(affectationDTO);
        }
        Affectation affectation = affectationMapper.toEntity(affectationDTO);
        affectation = affectationRepository.save(affectation);
        AffectationDTO result = affectationMapper.toDto(affectation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, affectationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /affectations : get all the affectations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of affectations in body
     */
    @GetMapping("/affectations")
    @Timed
    public ResponseEntity<List<AffectationDTO>> getAllAffectations(Pageable pageable) {
        log.debug("REST request to get a page of Affectations");
        Page<Affectation> page = affectationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/affectations");
        return new ResponseEntity<>(affectationMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /affectations/:id : get the "id" affectation.
     *
     * @param id the id of the affectationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the affectationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/affectations/{id}")
    @Timed
    public ResponseEntity<AffectationDTO> getAffectation(@PathVariable Long id) {
        log.debug("REST request to get Affectation : {}", id);
        Affectation affectation = affectationRepository.findOne(id);
        AffectationDTO affectationDTO = affectationMapper.toDto(affectation);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(affectationDTO));
    }

    /**
     * DELETE  /affectations/:id : delete the "id" affectation.
     *
     * @param id the id of the affectationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/affectations/{id}")
    @Timed
    public ResponseEntity<Void> deleteAffectation(@PathVariable Long id) {
        log.debug("REST request to delete Affectation : {}", id);
        affectationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
