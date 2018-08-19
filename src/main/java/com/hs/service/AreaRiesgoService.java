package com.hs.service;

import com.hs.domain.AreaRiesgo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AreaRiesgo.
 */
public interface AreaRiesgoService {

    /**
     * Save a areaRiesgo.
     *
     * @param areaRiesgo the entity to save
     * @return the persisted entity
     */
    AreaRiesgo save(AreaRiesgo areaRiesgo);

    /**
     * Get all the areaRiesgos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AreaRiesgo> findAll(Pageable pageable);


    /**
     * Get the "id" areaRiesgo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AreaRiesgo> findOne(Long id);

    /**
     * Delete the "id" areaRiesgo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
