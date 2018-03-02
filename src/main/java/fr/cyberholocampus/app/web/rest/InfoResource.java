package fr.cyberholocampus.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.cyberholocampus.app.domain.Info;

import fr.cyberholocampus.app.repository.InfoRepository;
import fr.cyberholocampus.app.web.rest.errors.BadRequestAlertException;
import fr.cyberholocampus.app.web.rest.util.HeaderUtil;
import fr.cyberholocampus.app.web.rest.util.PaginationUtil;
import fr.cyberholocampus.app.service.dto.InfoDTO;
import fr.cyberholocampus.app.service.mapper.InfoMapper;
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
 * REST controller for managing Info.
 */
@RestController
@RequestMapping("/api")
public class InfoResource {

    private final Logger log = LoggerFactory.getLogger(InfoResource.class);

    private static final String ENTITY_NAME = "info";

    private final InfoRepository infoRepository;

    private final InfoMapper infoMapper;

    public InfoResource(InfoRepository infoRepository, InfoMapper infoMapper) {
        this.infoRepository = infoRepository;
        this.infoMapper = infoMapper;
    }

    /**
     * POST  /infos : Create a new info.
     *
     * @param infoDTO the infoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new infoDTO, or with status 400 (Bad Request) if the info has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/infos")
    @Timed
    public ResponseEntity<InfoDTO> createInfo(@RequestBody InfoDTO infoDTO) throws URISyntaxException {
        log.debug("REST request to save Info : {}", infoDTO);
        if (infoDTO.getId() != null) {
            throw new BadRequestAlertException("A new info cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Info info = infoMapper.toEntity(infoDTO);
        info = infoRepository.save(info);
        InfoDTO result = infoMapper.toDto(info);
        return ResponseEntity.created(new URI("/api/infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /infos : Updates an existing info.
     *
     * @param infoDTO the infoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated infoDTO,
     * or with status 400 (Bad Request) if the infoDTO is not valid,
     * or with status 500 (Internal Server Error) if the infoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/infos")
    @Timed
    public ResponseEntity<InfoDTO> updateInfo(@RequestBody InfoDTO infoDTO) throws URISyntaxException {
        log.debug("REST request to update Info : {}", infoDTO);
        if (infoDTO.getId() == null) {
            return createInfo(infoDTO);
        }
        Info info = infoMapper.toEntity(infoDTO);
        info = infoRepository.save(info);
        InfoDTO result = infoMapper.toDto(info);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, infoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /infos : get all the infos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of infos in body
     */
    @GetMapping("/infos")
    @Timed
    public ResponseEntity<List<InfoDTO>> getAllInfos(Pageable pageable) {
        log.debug("REST request to get a page of Infos");
        Page<Info> page = infoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/infos");
        return new ResponseEntity<>(infoMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /infos/:id : get the "id" info.
     *
     * @param id the id of the infoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the infoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/infos/{id}")
    @Timed
    public ResponseEntity<InfoDTO> getInfo(@PathVariable Long id) {
        log.debug("REST request to get Info : {}", id);
        Info info = infoRepository.findOne(id);
        InfoDTO infoDTO = infoMapper.toDto(info);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(infoDTO));
    }

    /**
     * DELETE  /infos/:id : delete the "id" info.
     *
     * @param id the id of the infoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteInfo(@PathVariable Long id) {
        log.debug("REST request to delete Info : {}", id);
        infoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
