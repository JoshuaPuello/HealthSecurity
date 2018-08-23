package com.hs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hs.domain.Wiki;
import com.hs.service.WikiService;
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
 * REST controller for managing Wiki.
 */
@RestController
@RequestMapping("/api")
public class WikiResource {

    private final Logger log = LoggerFactory.getLogger(WikiResource.class);

    private static final String ENTITY_NAME = "wiki";

    private final WikiService wikiService;

    public WikiResource(WikiService wikiService) {
        this.wikiService = wikiService;
    }

    /**
     * POST  /wikis : Create a new wiki.
     *
     * @param wiki the wiki to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wiki, or with status 400 (Bad Request) if the wiki has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wikis")
    @Timed
    public ResponseEntity<Wiki> createWiki(@Valid @RequestBody Wiki wiki) throws URISyntaxException {
        log.debug("REST request to save Wiki : {}", wiki);
        if (wiki.getId() != null) {
            throw new BadRequestAlertException("A new wiki cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Wiki result = wikiService.save(wiki);
        return ResponseEntity.created(new URI("/api/wikis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wikis : Updates an existing wiki.
     *
     * @param wiki the wiki to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wiki,
     * or with status 400 (Bad Request) if the wiki is not valid,
     * or with status 500 (Internal Server Error) if the wiki couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wikis")
    @Timed
    public ResponseEntity<Wiki> updateWiki(@Valid @RequestBody Wiki wiki) throws URISyntaxException {
        log.debug("REST request to update Wiki : {}", wiki);
        if (wiki.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Wiki result = wikiService.save(wiki);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wiki.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wikis : get all the wikis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wikis in body
     */
    @GetMapping("/wikis")
    @Timed
    public ResponseEntity<List<Wiki>> getAllWikis(Pageable pageable) {
        log.debug("REST request to get a page of Wikis");
        Page<Wiki> page = wikiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wikis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wikis/:id : get the "id" wiki.
     *
     * @param id the id of the wiki to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wiki, or with status 404 (Not Found)
     */
    @GetMapping("/wikis/{id}")
    @Timed
    public ResponseEntity<Wiki> getWiki(@PathVariable Long id) {
        log.debug("REST request to get Wiki : {}", id);
        Optional<Wiki> wiki = wikiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wiki);
    }

    /**
     * DELETE  /wikis/:id : delete the "id" wiki.
     *
     * @param id the id of the wiki to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wikis/{id}")
    @Timed
    public ResponseEntity<Void> deleteWiki(@PathVariable Long id) {
        log.debug("REST request to delete Wiki : {}", id);
        wikiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
