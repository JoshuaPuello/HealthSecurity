package com.hs.service;

import com.hs.domain.TipoRiesgo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TipoRiesgo.
 */
public interface TipoRiesgoService {

    /**
     * Save a tipoRiesgo.
     *
     * @param tipoRiesgo the entity to save
     * @return the persisted entity
     */
    TipoRiesgo save(TipoRiesgo tipoRiesgo);

    /**
     * Get all the tipoRiesgos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoRiesgo> findAll(Pageable pageable);


    /**
     * Get the "id" tipoRiesgo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TipoRiesgo> findOne(Long id);

    /**
     * Delete the "id" tipoRiesgo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
