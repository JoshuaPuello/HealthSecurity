package com.hs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hs.domain.TipoRiesgo;
import com.hs.service.TipoRiesgoService;
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
 * REST controller for managing TipoRiesgo.
 */
@RestController
@RequestMapping("/api")
public class TipoRiesgoResource {

    private final Logger log = LoggerFactory.getLogger(TipoRiesgoResource.class);

    private static final String ENTITY_NAME = "tipoRiesgo";

    private final TipoRiesgoService tipoRiesgoService;

    public TipoRiesgoResource(TipoRiesgoService tipoRiesgoService) {
        this.tipoRiesgoService = tipoRiesgoService;
    }

    /**
     * POST  /tipo-riesgos : Create a new tipoRiesgo.
     *
     * @param tipoRiesgo the tipoRiesgo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoRiesgo, or with status 400 (Bad Request) if the tipoRiesgo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-riesgos")
    @Timed
    public ResponseEntity<TipoRiesgo> createTipoRiesgo(@Valid @RequestBody TipoRiesgo tipoRiesgo) throws URISyntaxException {
        log.debug("REST request to save TipoRiesgo : {}", tipoRiesgo);
        if (tipoRiesgo.getId() != null) {
            throw new BadRequestAlertException("A new tipoRiesgo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoRiesgo result = tipoRiesgoService.save(tipoRiesgo);
        return ResponseEntity.created(new URI("/api/tipo-riesgos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-riesgos : Updates an existing tipoRiesgo.
     *
     * @param tipoRiesgo the tipoRiesgo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoRiesgo,
     * or with status 400 (Bad Request) if the tipoRiesgo is not valid,
     * or with status 500 (Internal Server Error) if the tipoRiesgo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-riesgos")
    @Timed
    public ResponseEntity<TipoRiesgo> updateTipoRiesgo(@Valid @RequestBody TipoRiesgo tipoRiesgo) throws URISyntaxException {
        log.debug("REST request to update TipoRiesgo : {}", tipoRiesgo);
        if (tipoRiesgo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoRiesgo result = tipoRiesgoService.save(tipoRiesgo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoRiesgo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-riesgos : get all the tipoRiesgos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoRiesgos in body
     */
    @GetMapping("/tipo-riesgos")
    @Timed
    public ResponseEntity<List<TipoRiesgo>> getAllTipoRiesgos(Pageable pageable) {
        log.debug("REST request to get a page of TipoRiesgos");
        Page<TipoRiesgo> page = tipoRiesgoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-riesgos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipo-riesgos/:id : get the "id" tipoRiesgo.
     *
     * @param id the id of the tipoRiesgo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoRiesgo, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-riesgos/{id}")
    @Timed
    public ResponseEntity<TipoRiesgo> getTipoRiesgo(@PathVariable Long id) {
        log.debug("REST request to get TipoRiesgo : {}", id);
        Optional<TipoRiesgo> tipoRiesgo = tipoRiesgoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoRiesgo);
    }

    /**
     * DELETE  /tipo-riesgos/:id : delete the "id" tipoRiesgo.
     *
     * @param id the id of the tipoRiesgo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-riesgos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoRiesgo(@PathVariable Long id) {
        log.debug("REST request to delete TipoRiesgo : {}", id);
        tipoRiesgoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
