package com.hs.service;

import com.hs.domain.Wiki;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Wiki.
 */
public interface WikiService {

    /**
     * Save a wiki.
     *
     * @param wiki the entity to save
     * @return the persisted entity
     */
    Wiki save(Wiki wiki);

    /**
     * Get all the wikis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Wiki> findAll(Pageable pageable);


    /**
     * Get the "id" wiki.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Wiki> findOne(Long id);

    /**
     * Delete the "id" wiki.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
