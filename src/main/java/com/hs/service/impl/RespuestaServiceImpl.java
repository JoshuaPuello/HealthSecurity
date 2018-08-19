package com.hs.service.impl;

import com.hs.service.RespuestaService;
import com.hs.domain.Respuesta;
import com.hs.repository.RespuestaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Respuesta.
 */
@Service
@Transactional
public class RespuestaServiceImpl implements RespuestaService {

    private final Logger log = LoggerFactory.getLogger(RespuestaServiceImpl.class);

    private final RespuestaRepository respuestaRepository;

    public RespuestaServiceImpl(RespuestaRepository respuestaRepository) {
        this.respuestaRepository = respuestaRepository;
    }

    /**
     * Save a respuesta.
     *
     * @param respuesta the entity to save
     * @return the persisted entity
     */
    @Override
    public Respuesta save(Respuesta respuesta) {
        log.debug("Request to save Respuesta : {}", respuesta);        return respuestaRepository.save(respuesta);
    }

    /**
     * Get all the respuestas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Respuesta> findAll(Pageable pageable) {
        log.debug("Request to get all Respuestas");
        return respuestaRepository.findAll(pageable);
    }

    /**
     * Get all the Respuesta with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Respuesta> findAllWithEagerRelationships(Pageable pageable) {
        return respuestaRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one respuesta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Respuesta> findOne(Long id) {
        log.debug("Request to get Respuesta : {}", id);
        return respuestaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the respuesta by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Respuesta : {}", id);
        respuestaRepository.deleteById(id);
    }
}
