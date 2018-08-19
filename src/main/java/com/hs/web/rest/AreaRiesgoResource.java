package com.hs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hs.domain.AreaRiesgo;
import com.hs.service.AreaRiesgoService;
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
 * REST controller for managing AreaRiesgo.
 */
@RestController
@RequestMapping("/api")
public class AreaRiesgoResource {

    private final Logger log = LoggerFactory.getLogger(AreaRiesgoResource.class);

    private static final String ENTITY_NAME = "areaRiesgo";

    private final AreaRiesgoService areaRiesgoService;

    public AreaRiesgoResource(AreaRiesgoService areaRiesgoService) {
        this.areaRiesgoService = areaRiesgoService;
    }

    /**
     * POST  /area-riesgos : Create a new areaRiesgo.
     *
     * @param areaRiesgo the areaRiesgo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new areaRiesgo, or with status 400 (Bad Request) if the areaRiesgo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/area-riesgos")
    @Timed
    public ResponseEntity<AreaRiesgo> createAreaRiesgo(@Valid @RequestBody AreaRiesgo areaRiesgo) throws URISyntaxException {
        log.debug("REST request to save AreaRiesgo : {}", areaRiesgo);
        if (areaRiesgo.getId() != null) {
            throw new BadRequestAlertException("A new areaRiesgo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AreaRiesgo result = areaRiesgoService.save(areaRiesgo);
        return ResponseEntity.created(new URI("/api/area-riesgos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /area-riesgos : Updates an existing areaRiesgo.
     *
     * @param areaRiesgo the areaRiesgo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated areaRiesgo,
     * or with status 400 (Bad Request) if the areaRiesgo is not valid,
     * or with status 500 (Internal Server Error) if the areaRiesgo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/area-riesgos")
    @Timed
    public ResponseEntity<AreaRiesgo> updateAreaRiesgo(@Valid @RequestBody AreaRiesgo areaRiesgo) throws URISyntaxException {
        log.debug("REST request to update AreaRiesgo : {}", areaRiesgo);
        if (areaRiesgo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AreaRiesgo result = areaRiesgoService.save(areaRiesgo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, areaRiesgo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /area-riesgos : get all the areaRiesgos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of areaRiesgos in body
     */
    @GetMapping("/area-riesgos")
    @Timed
    public ResponseEntity<List<AreaRiesgo>> getAllAreaRiesgos(Pageable pageable) {
        log.debug("REST request to get a page of AreaRiesgos");
        Page<AreaRiesgo> page = areaRiesgoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/area-riesgos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /area-riesgos/:id : get the "id" areaRiesgo.
     *
     * @param id the id of the areaRiesgo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the areaRiesgo, or with status 404 (Not Found)
     */
    @GetMapping("/area-riesgos/{id}")
    @Timed
    public ResponseEntity<AreaRiesgo> getAreaRiesgo(@PathVariable Long id) {
        log.debug("REST request to get AreaRiesgo : {}", id);
        Optional<AreaRiesgo> areaRiesgo = areaRiesgoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(areaRiesgo);
    }

    /**
     * DELETE  /area-riesgos/:id : delete the "id" areaRiesgo.
     *
     * @param id the id of the areaRiesgo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/area-riesgos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAreaRiesgo(@PathVariable Long id) {
        log.debug("REST request to delete AreaRiesgo : {}", id);
        areaRiesgoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
