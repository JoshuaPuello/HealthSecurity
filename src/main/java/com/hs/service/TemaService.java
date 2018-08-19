package com.hs.service;

import com.hs.domain.Tema;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Tema.
 */
public interface TemaService {

    /**
     * Save a tema.
     *
     * @param tema the entity to save
     * @return the persisted entity
     */
    Tema save(Tema tema);

    /**
     * Get all the temas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Tema> findAll(Pageable pageable);


    /**
     * Get the "id" tema.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Tema> findOne(Long id);

    /**
     * Delete the "id" tema.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
