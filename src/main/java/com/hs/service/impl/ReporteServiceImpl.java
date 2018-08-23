package com.hs.service.impl;

import com.hs.service.ReporteService;
import com.hs.domain.Reporte;
import com.hs.repository.ReporteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
/**
 * Service Implementation for managing Reporte.
 */
@Service
@Transactional
public class ReporteServiceImpl implements ReporteService {

    private final Logger log = LoggerFactory.getLogger(ReporteServiceImpl.class);

    private final ReporteRepository reporteRepository;

    public ReporteServiceImpl(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    /**
     * Save a reporte.
     *
     * @param reporte the entity to save
     * @return the persisted entity
     */
    @Override
    public Reporte save(Reporte reporte) {
        log.debug("Request to save Reporte : {}", reporte);        return reporteRepository.save(reporte);
    }

    /**
     * Get all the reportes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Reporte> findAll(Pageable pageable) {
        log.debug("Request to get all Reportes");
        return reporteRepository.findAll(pageable);
    }



    /**
     *  get all the reportes where Respuesta is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Reporte> findAllWhereRespuestaIsNull() {
        log.debug("Request to get all reportes where Respuesta is null");
        return StreamSupport
            .stream(reporteRepository.findAll().spliterator(), false)
            .filter(reporte -> reporte.getRespuesta() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one reporte by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Reporte> findOne(Long id) {
        log.debug("Request to get Reporte : {}", id);
        return reporteRepository.findById(id);
    }

    /**
     * Delete the reporte by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reporte : {}", id);
        reporteRepository.deleteById(id);
    }
}
