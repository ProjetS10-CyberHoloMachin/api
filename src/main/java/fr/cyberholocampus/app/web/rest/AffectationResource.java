package fr.cyberholocampus.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.cyberholocampus.app.domain.Affectation;

import fr.cyberholocampus.app.repository.AffectationRepository;
import fr.cyberholocampus.app.repository.search.AffectationSearchRepository;
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
 * REST controller for managing Affectation.
 */
@RestController
@RequestMapping("/api")
public class AffectationResource {

    private final Logger log = LoggerFactory.getLogger(AffectationResource.class);

    private static final String ENTITY_NAME = "affectation";

    private final AffectationRepository affectationRepository;

    private final AffectationSearchRepository affectationSearchRepository;

    public AffectationResource(AffectationRepository affectationRepository, AffectationSearchRepository affectationSearchRepository) {
        this.affectationRepository = affectationRepository;
        this.affectationSearchRepository = affectationSearchRepository;
    }

    /**
     * POST  /affectations : Create a new affectation.
     *
     * @param affectation the affectation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new affectation, or with status 400 (Bad Request) if the affectation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/affectations")
    @Timed
    public ResponseEntity<Affectation> createAffectation(@Valid @RequestBody Affectation affectation) throws URISyntaxException {
        log.debug("REST request to save Affectation : {}", affectation);
        if (affectation.getId() != null) {
            throw new BadRequestAlertException("A new affectation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Affectation result = affectationRepository.save(affectation);
        affectationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/affectations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /affectations : Updates an existing affectation.
     *
     * @param affectation the affectation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated affectation,
     * or with status 400 (Bad Request) if the affectation is not valid,
     * or with status 500 (Internal Server Error) if the affectation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/affectations")
    @Timed
    public ResponseEntity<Affectation> updateAffectation(@Valid @RequestBody Affectation affectation) throws URISyntaxException {
        log.debug("REST request to update Affectation : {}", affectation);
        if (affectation.getId() == null) {
            return createAffectation(affectation);
        }
        Affectation result = affectationRepository.save(affectation);
        affectationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, affectation.getId().toString()))
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
    public ResponseEntity<List<Affectation>> getAllAffectations(Pageable pageable) {
        log.debug("REST request to get a page of Affectations");
        Page<Affectation> page = affectationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/affectations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /affectations/:id : get the "id" affectation.
     *
     * @param id the id of the affectation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the affectation, or with status 404 (Not Found)
     */
    @GetMapping("/affectations/{id}")
    @Timed
    public ResponseEntity<Affectation> getAffectation(@PathVariable Long id) {
        log.debug("REST request to get Affectation : {}", id);
        Affectation affectation = affectationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(affectation));
    }

    /**
     * DELETE  /affectations/:id : delete the "id" affectation.
     *
     * @param id the id of the affectation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/affectations/{id}")
    @Timed
    public ResponseEntity<Void> deleteAffectation(@PathVariable Long id) {
        log.debug("REST request to delete Affectation : {}", id);
        affectationRepository.delete(id);
        affectationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/affectations?query=:query : search for the affectation corresponding
     * to the query.
     *
     * @param query the query of the affectation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/affectations")
    @Timed
    public ResponseEntity<List<Affectation>> searchAffectations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Affectations for query {}", query);
        Page<Affectation> page = affectationSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/affectations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
