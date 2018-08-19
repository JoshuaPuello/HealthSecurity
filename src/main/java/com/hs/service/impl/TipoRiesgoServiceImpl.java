package com.hs.service.impl;

import com.hs.service.TipoRiesgoService;
import com.hs.domain.TipoRiesgo;
import com.hs.repository.TipoRiesgoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing TipoRiesgo.
 */
@Service
@Transactional
public class TipoRiesgoServiceImpl implements TipoRiesgoService {

    private final Logger log = LoggerFactory.getLogger(TipoRiesgoServiceImpl.class);

    private final TipoRiesgoRepository tipoRiesgoRepository;

    public TipoRiesgoServiceImpl(TipoRiesgoRepository tipoRiesgoRepository) {
        this.tipoRiesgoRepository = tipoRiesgoRepository;
    }

    /**
     * Save a tipoRiesgo.
     *
     * @param tipoRiesgo the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoRiesgo save(TipoRiesgo tipoRiesgo) {
        log.debug("Request to save TipoRiesgo : {}", tipoRiesgo);        return tipoRiesgoRepository.save(tipoRiesgo);
    }

    /**
     * Get all the tipoRiesgos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoRiesgo> findAll(Pageable pageable) {
        log.debug("Request to get all TipoRiesgos");
        return tipoRiesgoRepository.findAll(pageable);
    }


    /**
     * Get one tipoRiesgo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoRiesgo> findOne(Long id) {
        log.debug("Request to get TipoRiesgo : {}", id);
        return tipoRiesgoRepository.findById(id);
    }

    /**
     * Delete the tipoRiesgo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoRiesgo : {}", id);
        tipoRiesgoRepository.deleteById(id);
    }
}
