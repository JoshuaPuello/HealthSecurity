package com.hs.service.impl;

import com.hs.service.AreaRiesgoService;
import com.hs.domain.AreaRiesgo;
import com.hs.repository.AreaRiesgoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing AreaRiesgo.
 */
@Service
@Transactional
public class AreaRiesgoServiceImpl implements AreaRiesgoService {

    private final Logger log = LoggerFactory.getLogger(AreaRiesgoServiceImpl.class);

    private final AreaRiesgoRepository areaRiesgoRepository;

    public AreaRiesgoServiceImpl(AreaRiesgoRepository areaRiesgoRepository) {
        this.areaRiesgoRepository = areaRiesgoRepository;
    }

    /**
     * Save a areaRiesgo.
     *
     * @param areaRiesgo the entity to save
     * @return the persisted entity
     */
    @Override
    public AreaRiesgo save(AreaRiesgo areaRiesgo) {
        log.debug("Request to save AreaRiesgo : {}", areaRiesgo);        return areaRiesgoRepository.save(areaRiesgo);
    }

    /**
     * Get all the areaRiesgos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AreaRiesgo> findAll(Pageable pageable) {
        log.debug("Request to get all AreaRiesgos");
        return areaRiesgoRepository.findAll(pageable);
    }


    /**
     * Get one areaRiesgo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AreaRiesgo> findOne(Long id) {
        log.debug("Request to get AreaRiesgo : {}", id);
        return areaRiesgoRepository.findById(id);
    }

    /**
     * Delete the areaRiesgo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AreaRiesgo : {}", id);
        areaRiesgoRepository.deleteById(id);
    }
}
