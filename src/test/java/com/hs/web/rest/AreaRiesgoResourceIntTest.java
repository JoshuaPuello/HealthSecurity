package com.hs.web.rest;

import com.hs.HealthSecurityWebApp;

import com.hs.domain.AreaRiesgo;
import com.hs.repository.AreaRiesgoRepository;
import com.hs.service.AreaRiesgoService;
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
 * Test class for the AreaRiesgoResource REST controller.
 *
 * @see AreaRiesgoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthSecurityWebApp.class)
public class AreaRiesgoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private AreaRiesgoRepository areaRiesgoRepository;

    

    @Autowired
    private AreaRiesgoService areaRiesgoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAreaRiesgoMockMvc;

    private AreaRiesgo areaRiesgo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AreaRiesgoResource areaRiesgoResource = new AreaRiesgoResource(areaRiesgoService);
        this.restAreaRiesgoMockMvc = MockMvcBuilders.standaloneSetup(areaRiesgoResource)
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
    public static AreaRiesgo createEntity(EntityManager em) {
        AreaRiesgo areaRiesgo = new AreaRiesgo()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION);
        return areaRiesgo;
    }

    @Before
    public void initTest() {
        areaRiesgo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAreaRiesgo() throws Exception {
        int databaseSizeBeforeCreate = areaRiesgoRepository.findAll().size();

        // Create the AreaRiesgo
        restAreaRiesgoMockMvc.perform(post("/api/area-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaRiesgo)))
            .andExpect(status().isCreated());

        // Validate the AreaRiesgo in the database
        List<AreaRiesgo> areaRiesgoList = areaRiesgoRepository.findAll();
        assertThat(areaRiesgoList).hasSize(databaseSizeBeforeCreate + 1);
        AreaRiesgo testAreaRiesgo = areaRiesgoList.get(areaRiesgoList.size() - 1);
        assertThat(testAreaRiesgo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAreaRiesgo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createAreaRiesgoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = areaRiesgoRepository.findAll().size();

        // Create the AreaRiesgo with an existing ID
        areaRiesgo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaRiesgoMockMvc.perform(post("/api/area-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaRiesgo)))
            .andExpect(status().isBadRequest());

        // Validate the AreaRiesgo in the database
        List<AreaRiesgo> areaRiesgoList = areaRiesgoRepository.findAll();
        assertThat(areaRiesgoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = areaRiesgoRepository.findAll().size();
        // set the field null
        areaRiesgo.setNombre(null);

        // Create the AreaRiesgo, which fails.

        restAreaRiesgoMockMvc.perform(post("/api/area-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaRiesgo)))
            .andExpect(status().isBadRequest());

        List<AreaRiesgo> areaRiesgoList = areaRiesgoRepository.findAll();
        assertThat(areaRiesgoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAreaRiesgos() throws Exception {
        // Initialize the database
        areaRiesgoRepository.saveAndFlush(areaRiesgo);

        // Get all the areaRiesgoList
        restAreaRiesgoMockMvc.perform(get("/api/area-riesgos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaRiesgo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    

    @Test
    @Transactional
    public void getAreaRiesgo() throws Exception {
        // Initialize the database
        areaRiesgoRepository.saveAndFlush(areaRiesgo);

        // Get the areaRiesgo
        restAreaRiesgoMockMvc.perform(get("/api/area-riesgos/{id}", areaRiesgo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(areaRiesgo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAreaRiesgo() throws Exception {
        // Get the areaRiesgo
        restAreaRiesgoMockMvc.perform(get("/api/area-riesgos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAreaRiesgo() throws Exception {
        // Initialize the database
        areaRiesgoService.save(areaRiesgo);

        int databaseSizeBeforeUpdate = areaRiesgoRepository.findAll().size();

        // Update the areaRiesgo
        AreaRiesgo updatedAreaRiesgo = areaRiesgoRepository.findById(areaRiesgo.getId()).get();
        // Disconnect from session so that the updates on updatedAreaRiesgo are not directly saved in db
        em.detach(updatedAreaRiesgo);
        updatedAreaRiesgo
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);

        restAreaRiesgoMockMvc.perform(put("/api/area-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAreaRiesgo)))
            .andExpect(status().isOk());

        // Validate the AreaRiesgo in the database
        List<AreaRiesgo> areaRiesgoList = areaRiesgoRepository.findAll();
        assertThat(areaRiesgoList).hasSize(databaseSizeBeforeUpdate);
        AreaRiesgo testAreaRiesgo = areaRiesgoList.get(areaRiesgoList.size() - 1);
        assertThat(testAreaRiesgo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAreaRiesgo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingAreaRiesgo() throws Exception {
        int databaseSizeBeforeUpdate = areaRiesgoRepository.findAll().size();

        // Create the AreaRiesgo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAreaRiesgoMockMvc.perform(put("/api/area-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(areaRiesgo)))
            .andExpect(status().isBadRequest());

        // Validate the AreaRiesgo in the database
        List<AreaRiesgo> areaRiesgoList = areaRiesgoRepository.findAll();
        assertThat(areaRiesgoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAreaRiesgo() throws Exception {
        // Initialize the database
        areaRiesgoService.save(areaRiesgo);

        int databaseSizeBeforeDelete = areaRiesgoRepository.findAll().size();

        // Get the areaRiesgo
        restAreaRiesgoMockMvc.perform(delete("/api/area-riesgos/{id}", areaRiesgo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AreaRiesgo> areaRiesgoList = areaRiesgoRepository.findAll();
        assertThat(areaRiesgoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaRiesgo.class);
        AreaRiesgo areaRiesgo1 = new AreaRiesgo();
        areaRiesgo1.setId(1L);
        AreaRiesgo areaRiesgo2 = new AreaRiesgo();
        areaRiesgo2.setId(areaRiesgo1.getId());
        assertThat(areaRiesgo1).isEqualTo(areaRiesgo2);
        areaRiesgo2.setId(2L);
        assertThat(areaRiesgo1).isNotEqualTo(areaRiesgo2);
        areaRiesgo1.setId(null);
        assertThat(areaRiesgo1).isNotEqualTo(areaRiesgo2);
    }
}
