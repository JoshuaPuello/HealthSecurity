package com.hs.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hs.domain.Reporte;
import com.hs.service.ReporteService;
import com.hs.web.rest.errors.BadRequestAlertException;
import com.hs.web.rest.util.HeaderUtil;
import com.hs.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Reporte.
 */
@RestController
@RequestMapping("/api")
public class ReporteResource {

    private final Logger log = LoggerFactory.getLogger(ReporteResource.class);

    private static final String ENTITY_NAME = "reporte";

    private final ReporteService reporteService;

    public ReporteResource(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    /**
     * POST  /reportes : Create a new reporte.
     *
     * @param reporte the reporte to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reporte, or with status 400 (Bad Request) if the reporte has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reportes")
    @Timed
    public ResponseEntity<Reporte> createReporte(@Valid @RequestBody Reporte reporte) throws URISyntaxException {
        log.debug("REST request to save Reporte : {}", reporte);
        if (reporte.getId() != null) {
            throw new BadRequestAlertException("A new reporte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reporte result = reporteService.save(reporte);
        return ResponseEntity.created(new URI("/api/reportes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reportes : Updates an existing reporte.
     *
     * @param reporte the reporte to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reporte,
     * or with status 400 (Bad Request) if the reporte is not valid,
     * or with status 500 (Internal Server Error) if the reporte couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reportes")
    @Timed
    public ResponseEntity<Reporte> updateReporte(@Valid @RequestBody Reporte reporte) throws URISyntaxException {
        log.debug("REST request to update Reporte : {}", reporte);
        if (reporte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reporte result = reporteService.save(reporte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reporte.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reportes : get all the reportes.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of reportes in body
     */
    @GetMapping("/reportes")
    @Timed
    public ResponseEntity<List<Reporte>> getAllReportes(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("respuesta-is-null".equals(filter)) {
            log.debug("REST request to get all Reportes where respuesta is null");
            return new ResponseEntity<>(reporteService.findAllWhereRespuestaIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Reportes");
        Page<Reporte> page = reporteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reportes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /reportes/:id : get the "id" reporte.
     *
     * @param id the id of the reporte to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reporte, or with status 404 (Not Found)
     */
    @GetMapping("/reportes/{id}")
    @Timed
    public ResponseEntity<Reporte> getReporte(@PathVariable Long id) {
        log.debug("REST request to get Reporte : {}", id);
        Optional<Reporte> reporte = reporteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reporte);
    }

    @GetMapping("/reportes/generate-pdf")
    @Timed
    public ResponseEntity<Object> generatePDF() {
        log.debug("REST request to generate PDF : {}");
        String bis = reporteService.generatePDF();
        File filePDF = new File(bis.toString());
        
        InputStreamResource isr = null;
        
		try {
			isr = new InputStreamResource(new FileInputStream(filePDF));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-disposition", "attachment;filename=reporte-general.pdf");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        
        ResponseEntity<Object> responseEntity = 
        		ResponseEntity.ok()
        		.headers(headers).contentLength(filePDF.length())
        		.contentType(MediaType.parseMediaType("application/pdf"))
        		.body(isr);
        
        return responseEntity;
        
    }

    /**
     * DELETE  /reportes/:id : delete the "id" reporte.
     *
     * @param id the id of the reporte to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reportes/{id}")
    @Timed
    public ResponseEntity<Void> deleteReporte(@PathVariable Long id) {
        log.debug("REST request to delete Reporte : {}", id);
        reporteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
