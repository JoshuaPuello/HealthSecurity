package com.hs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hs.domain.Respuesta;
import com.hs.service.RespuestaService;
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
 * REST controller for managing Respuesta.
 */
@RestController
@RequestMapping("/api")
public class RespuestaResource {

    private final Logger log = LoggerFactory.getLogger(RespuestaResource.class);

    private static final String ENTITY_NAME = "respuesta";

    private final RespuestaService respuestaService;

    public RespuestaResource(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    /**
     * POST  /respuestas : Create a new respuesta.
     *
     * @param respuesta the respuesta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new respuesta, or with status 400 (Bad Request) if the respuesta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/respuestas")
    @Timed
    public ResponseEntity<Respuesta> createRespuesta(@Valid @RequestBody Respuesta respuesta) throws URISyntaxException {
        log.debug("REST request to save Respuesta : {}", respuesta);
        if (respuesta.getId() != null) {
            throw new BadRequestAlertException("A new respuesta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Respuesta result = respuestaService.save(respuesta);
        return ResponseEntity.created(new URI("/api/respuestas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /respuestas : Updates an existing respuesta.
     *
     * @param respuesta the respuesta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated respuesta,
     * or with status 400 (Bad Request) if the respuesta is not valid,
     * or with status 500 (Internal Server Error) if the respuesta couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/respuestas")
    @Timed
    public ResponseEntity<Respuesta> updateRespuesta(@Valid @RequestBody Respuesta respuesta) throws URISyntaxException {
        log.debug("REST request to update Respuesta : {}", respuesta);
        if (respuesta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Respuesta result = respuestaService.save(respuesta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, respuesta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /respuestas : get all the respuestas.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of respuestas in body
     */
    @GetMapping("/respuestas")
    @Timed
    public ResponseEntity<List<Respuesta>> getAllRespuestas(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Respuestas");
        Page<Respuesta> page;
        if (eagerload) {
            page = respuestaService.findAllWithEagerRelationships(pageable);
        } else {
            page = respuestaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/respuestas?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /respuestas/:id : get the "id" respuesta.
     *
     * @param id the id of the respuesta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the respuesta, or with status 404 (Not Found)
     */
    @GetMapping("/respuestas/{id}")
    @Timed
    public ResponseEntity<Respuesta> getRespuesta(@PathVariable Long id) {
        log.debug("REST request to get Respuesta : {}", id);
        Optional<Respuesta> respuesta = respuestaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(respuesta);
    }

    /**
     * DELETE  /respuestas/:id : delete the "id" respuesta.
     *
     * @param id the id of the respuesta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/respuestas/{id}")
    @Timed
    public ResponseEntity<Void> deleteRespuesta(@PathVariable Long id) {
        log.debug("REST request to delete Respuesta : {}", id);
        respuestaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
