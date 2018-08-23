package com.hs.web.rest;

import com.hs.HealthSecurityWebApp;

import com.hs.domain.TipoRiesgo;
import com.hs.repository.TipoRiesgoRepository;
import com.hs.service.TipoRiesgoService;
import com.hs.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static com.hs.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TipoRiesgoResource REST controller.
 *
 * @see TipoRiesgoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthSecurityWebApp.class)
public class TipoRiesgoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private TipoRiesgoRepository tipoRiesgoRepository;

    

    @Autowired
    private TipoRiesgoService tipoRiesgoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoRiesgoMockMvc;

    private TipoRiesgo tipoRiesgo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoRiesgoResource tipoRiesgoResource = new TipoRiesgoResource(tipoRiesgoService);
        this.restTipoRiesgoMockMvc = MockMvcBuilders.standaloneSetup(tipoRiesgoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRiesgo createEntity(EntityManager em) {
        TipoRiesgo tipoRiesgo = new TipoRiesgo()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION);
        return tipoRiesgo;
    }

    @Before
    public void initTest() {
        tipoRiesgo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoRiesgo() throws Exception {
        int databaseSizeBeforeCreate = tipoRiesgoRepository.findAll().size();

        // Create the TipoRiesgo
        restTipoRiesgoMockMvc.perform(post("/api/tipo-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRiesgo)))
            .andExpect(status().isCreated());

        // Validate the TipoRiesgo in the database
        List<TipoRiesgo> tipoRiesgoList = tipoRiesgoRepository.findAll();
        assertThat(tipoRiesgoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoRiesgo testTipoRiesgo = tipoRiesgoList.get(tipoRiesgoList.size() - 1);
        assertThat(testTipoRiesgo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoRiesgo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createTipoRiesgoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoRiesgoRepository.findAll().size();

        // Create the TipoRiesgo with an existing ID
        tipoRiesgo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoRiesgoMockMvc.perform(post("/api/tipo-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRiesgo)))
            .andExpect(status().isBadRequest());

        // Validate the TipoRiesgo in the database
        List<TipoRiesgo> tipoRiesgoList = tipoRiesgoRepository.findAll();
        assertThat(tipoRiesgoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoRiesgoRepository.findAll().size();
        // set the field null
        tipoRiesgo.setNombre(null);

        // Create the TipoRiesgo, which fails.

        restTipoRiesgoMockMvc.perform(post("/api/tipo-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRiesgo)))
            .andExpect(status().isBadRequest());

        List<TipoRiesgo> tipoRiesgoList = tipoRiesgoRepository.findAll();
        assertThat(tipoRiesgoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoRiesgos() throws Exception {
        // Initialize the database
        tipoRiesgoRepository.saveAndFlush(tipoRiesgo);

        // Get all the tipoRiesgoList
        restTipoRiesgoMockMvc.perform(get("/api/tipo-riesgos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRiesgo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    

    @Test
    @Transactional
    public void getTipoRiesgo() throws Exception {
        // Initialize the database
        tipoRiesgoRepository.saveAndFlush(tipoRiesgo);

        // Get the tipoRiesgo
        restTipoRiesgoMockMvc.perform(get("/api/tipo-riesgos/{id}", tipoRiesgo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoRiesgo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTipoRiesgo() throws Exception {
        // Get the tipoRiesgo
        restTipoRiesgoMockMvc.perform(get("/api/tipo-riesgos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoRiesgo() throws Exception {
        // Initialize the database
        tipoRiesgoService.save(tipoRiesgo);

        int databaseSizeBeforeUpdate = tipoRiesgoRepository.findAll().size();

        // Update the tipoRiesgo
        TipoRiesgo updatedTipoRiesgo = tipoRiesgoRepository.findById(tipoRiesgo.getId()).get();
        // Disconnect from session so that the updates on updatedTipoRiesgo are not directly saved in db
        em.detach(updatedTipoRiesgo);
        updatedTipoRiesgo
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);

        restTipoRiesgoMockMvc.perform(put("/api/tipo-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoRiesgo)))
            .andExpect(status().isOk());

        // Validate the TipoRiesgo in the database
        List<TipoRiesgo> tipoRiesgoList = tipoRiesgoRepository.findAll();
        assertThat(tipoRiesgoList).hasSize(databaseSizeBeforeUpdate);
        TipoRiesgo testTipoRiesgo = tipoRiesgoList.get(tipoRiesgoList.size() - 1);
        assertThat(testTipoRiesgo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoRiesgo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoRiesgo() throws Exception {
        int databaseSizeBeforeUpdate = tipoRiesgoRepository.findAll().size();

        // Create the TipoRiesgo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoRiesgoMockMvc.perform(put("/api/tipo-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoRiesgo)))
            .andExpect(status().isBadRequest());

        // Validate the TipoRiesgo in the database
        List<TipoRiesgo> tipoRiesgoList = tipoRiesgoRepository.findAll();
        assertThat(tipoRiesgoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoRiesgo() throws Exception {
        // Initialize the database
        tipoRiesgoService.save(tipoRiesgo);

        int databaseSizeBeforeDelete = tipoRiesgoRepository.findAll().size();

        // Get the tipoRiesgo
        restTipoRiesgoMockMvc.perform(delete("/api/tipo-riesgos/{id}", tipoRiesgo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoRiesgo> tipoRiesgoList = tipoRiesgoRepository.findAll();
        assertThat(tipoRiesgoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoRiesgo.class);
        TipoRiesgo tipoRiesgo1 = new TipoRiesgo();
        tipoRiesgo1.setId(1L);
        TipoRiesgo tipoRiesgo2 = new TipoRiesgo();
        tipoRiesgo2.setId(tipoRiesgo1.getId());
        assertThat(tipoRiesgo1).isEqualTo(tipoRiesgo2);
        tipoRiesgo2.setId(2L);
        assertThat(tipoRiesgo1).isNotEqualTo(tipoRiesgo2);
        tipoRiesgo1.setId(null);
        assertThat(tipoRiesgo1).isNotEqualTo(tipoRiesgo2);
    }
}
