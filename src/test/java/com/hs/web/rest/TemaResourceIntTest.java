package com.hs.web.rest;

import com.hs.HealthSecurityWebApp;

import com.hs.domain.Tema;
import com.hs.repository.TemaRepository;
import com.hs.service.TemaService;
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
 * Test class for the TemaResource REST controller.
 *
 * @see TemaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthSecurityWebApp.class)
public class TemaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private TemaRepository temaRepository;

    

    @Autowired
    private TemaService temaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTemaMockMvc;

    private Tema tema;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TemaResource temaResource = new TemaResource(temaService);
        this.restTemaMockMvc = MockMvcBuilders.standaloneSetup(temaResource)
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
    public static Tema createEntity(EntityManager em) {
        Tema tema = new Tema()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION);
        return tema;
    }

    @Before
    public void initTest() {
        tema = createEntity(em);
    }

    @Test
    @Transactional
    public void createTema() throws Exception {
        int databaseSizeBeforeCreate = temaRepository.findAll().size();

        // Create the Tema
        restTemaMockMvc.perform(post("/api/temas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tema)))
            .andExpect(status().isCreated());

        // Validate the Tema in the database
        List<Tema> temaList = temaRepository.findAll();
        assertThat(temaList).hasSize(databaseSizeBeforeCreate + 1);
        Tema testTema = temaList.get(temaList.size() - 1);
        assertThat(testTema.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTema.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createTemaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = temaRepository.findAll().size();

        // Create the Tema with an existing ID
        tema.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemaMockMvc.perform(post("/api/temas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tema)))
            .andExpect(status().isBadRequest());

        // Validate the Tema in the database
        List<Tema> temaList = temaRepository.findAll();
        assertThat(temaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = temaRepository.findAll().size();
        // set the field null
        tema.setNombre(null);

        // Create the Tema, which fails.

        restTemaMockMvc.perform(post("/api/temas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tema)))
            .andExpect(status().isBadRequest());

        List<Tema> temaList = temaRepository.findAll();
        assertThat(temaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTemas() throws Exception {
        // Initialize the database
        temaRepository.saveAndFlush(tema);

        // Get all the temaList
        restTemaMockMvc.perform(get("/api/temas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tema.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    

    @Test
    @Transactional
    public void getTema() throws Exception {
        // Initialize the database
        temaRepository.saveAndFlush(tema);

        // Get the tema
        restTemaMockMvc.perform(get("/api/temas/{id}", tema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tema.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTema() throws Exception {
        // Get the tema
        restTemaMockMvc.perform(get("/api/temas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTema() throws Exception {
        // Initialize the database
        temaService.save(tema);

        int databaseSizeBeforeUpdate = temaRepository.findAll().size();

        // Update the tema
        Tema updatedTema = temaRepository.findById(tema.getId()).get();
        // Disconnect from session so that the updates on updatedTema are not directly saved in db
        em.detach(updatedTema);
        updatedTema
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION);

        restTemaMockMvc.perform(put("/api/temas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTema)))
            .andExpect(status().isOk());

        // Validate the Tema in the database
        List<Tema> temaList = temaRepository.findAll();
        assertThat(temaList).hasSize(databaseSizeBeforeUpdate);
        Tema testTema = temaList.get(temaList.size() - 1);
        assertThat(testTema.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTema.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingTema() throws Exception {
        int databaseSizeBeforeUpdate = temaRepository.findAll().size();

        // Create the Tema

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTemaMockMvc.perform(put("/api/temas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tema)))
            .andExpect(status().isBadRequest());

        // Validate the Tema in the database
        List<Tema> temaList = temaRepository.findAll();
        assertThat(temaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTema() throws Exception {
        // Initialize the database
        temaService.save(tema);

        int databaseSizeBeforeDelete = temaRepository.findAll().size();

        // Get the tema
        restTemaMockMvc.perform(delete("/api/temas/{id}", tema.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tema> temaList = temaRepository.findAll();
        assertThat(temaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tema.class);
        Tema tema1 = new Tema();
        tema1.setId(1L);
        Tema tema2 = new Tema();
        tema2.setId(tema1.getId());
        assertThat(tema1).isEqualTo(tema2);
        tema2.setId(2L);
        assertThat(tema1).isNotEqualTo(tema2);
        tema1.setId(null);
        assertThat(tema1).isNotEqualTo(tema2);
    }
}
