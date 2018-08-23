package com.hs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hs.domain.Tema;
import com.hs.service.TemaService;
import com.hs.web.rest.errors.BadRequestAlertException;
import com.hs.web.rest.util.HeaderUtil;
import com.hs.web.rest.util.PaginationUtil;
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
 * REST controller for managing Tema.
 */
@RestController
@RequestMapping("/api")
public class TemaResource {

    private final Logger log = LoggerFactory.getLogger(TemaResource.class);

    private static final String ENTITY_NAME = "tema";

    private final TemaService temaService;

    public TemaResource(TemaService temaService) {
        this.temaService = temaService;
    }

    /**
     * POST  /temas : Create a new tema.
     *
     * @param tema the tema to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tema, or with status 400 (Bad Request) if the tema has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/temas")
    @Timed
    public ResponseEntity<Tema> createTema(@Valid @RequestBody Tema tema) throws URISyntaxException {
        log.debug("REST request to save Tema : {}", tema);
        if (tema.getId() != null) {
            throw new BadRequestAlertException("A new tema cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tema result = temaService.save(tema);
        return ResponseEntity.created(new URI("/api/temas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /temas : Updates an existing tema.
     *
     * @param tema the tema to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tema,
     * or with status 400 (Bad Request) if the tema is not valid,
     * or with status 500 (Internal Server Error) if the tema couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/temas")
    @Timed
    public ResponseEntity<Tema> updateTema(@Valid @RequestBody Tema tema) throws URISyntaxException {
        log.debug("REST request to update Tema : {}", tema);
        if (tema.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tema result = temaService.save(tema);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tema.getId().toString()))
            .body(result);
    }

    /**
     * GET  /temas : get all the temas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of temas in body
     */
    @GetMapping("/temas")
    @Timed
    public ResponseEntity<List<Tema>> getAllTemas(Pageable pageable) {
        log.debug("REST request to get a page of Temas");
        Page<Tema> page = temaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/temas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /temas/:id : get the "id" tema.
     *
     * @param id the id of the tema to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tema, or with status 404 (Not Found)
     */
    @GetMapping("/temas/{id}")
    @Timed
    public ResponseEntity<Tema> getTema(@PathVariable Long id) {
        log.debug("REST request to get Tema : {}", id);
        Optional<Tema> tema = temaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tema);
    }

    /**
     * DELETE  /temas/:id : delete the "id" tema.
     *
     * @param id the id of the tema to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/temas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTema(@PathVariable Long id) {
        log.debug("REST request to delete Tema : {}", id);
        temaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
