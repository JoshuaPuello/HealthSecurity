package com.hs.service.impl;

import com.hs.service.WikiService;
import com.hs.domain.Wiki;
import com.hs.repository.WikiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Wiki.
 */
@Service
@Transactional
public class WikiServiceImpl implements WikiService {

    private final Logger log = LoggerFactory.getLogger(WikiServiceImpl.class);

    private final WikiRepository wikiRepository;

    public WikiServiceImpl(WikiRepository wikiRepository) {
        this.wikiRepository = wikiRepository;
    }

    /**
     * Save a wiki.
     *
     * @param wiki the entity to save
     * @return the persisted entity
     */
    @Override
    public Wiki save(Wiki wiki) {
        log.debug("Request to save Wiki : {}", wiki);        return wikiRepository.save(wiki);
    }

    /**
     * Get all the wikis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Wiki> findAll(Pageable pageable) {
        log.debug("Request to get all Wikis");
        return wikiRepository.findAll(pageable);
    }


    /**
     * Get one wiki by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Wiki> findOne(Long id) {
        log.debug("Request to get Wiki : {}", id);
        return wikiRepository.findById(id);
    }

    /**
     * Delete the wiki by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Wiki : {}", id);
        wikiRepository.deleteById(id);
    }
}
