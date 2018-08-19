package com.hs.service;

import com.hs.domain.Respuesta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Respuesta.
 */
public interface RespuestaService {

    /**
     * Save a respuesta.
     *
     * @param respuesta the entity to save
     * @return the persisted entity
     */
    Respuesta save(Respuesta respuesta);

    /**
     * Get all the respuestas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Respuesta> findAll(Pageable pageable);


    /**
     * Get the "id" respuesta.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Respuesta> findOne(Long id);

    /**
     * Delete the "id" respuesta.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
