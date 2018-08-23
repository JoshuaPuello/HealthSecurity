package com.hs.service.impl;

import com.hs.service.TemaService;
import com.hs.domain.Tema;
import com.hs.repository.TemaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Tema.
 */
@Service
@Transactional
public class TemaServiceImpl implements TemaService {

    private final Logger log = LoggerFactory.getLogger(TemaServiceImpl.class);

    private final TemaRepository temaRepository;

    public TemaServiceImpl(TemaRepository temaRepository) {
        this.temaRepository = temaRepository;
    }

    /**
     * Save a tema.
     *
     * @param tema the entity to save
     * @return the persisted entity
     */
    @Override
    public Tema save(Tema tema) {
        log.debug("Request to save Tema : {}", tema);        return temaRepository.save(tema);
    }

    /**
     * Get all the temas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Tema> findAll(Pageable pageable) {
        log.debug("Request to get all Temas");
        return temaRepository.findAll(pageable);
    }


    /**
     * Get one tema by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Tema> findOne(Long id) {
        log.debug("Request to get Tema : {}", id);
        return temaRepository.findById(id);
    }

    /**
     * Delete the tema by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tema : {}", id);
        temaRepository.deleteById(id);
    }
}
