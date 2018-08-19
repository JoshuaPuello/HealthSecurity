package com.hs.service;

import com.hs.domain.Reporte;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Reporte.
 */
public interface ReporteService {

    /**
     * Save a reporte.
     *
     * @param reporte the entity to save
     * @return the persisted entity
     */
    Reporte save(Reporte reporte);

    /**
     * Get all the reportes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Reporte> findAll(Pageable pageable);


    /**
     * Get the "id" reporte.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Reporte> findOne(Long id);

    /**
     * Delete the "id" reporte.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
