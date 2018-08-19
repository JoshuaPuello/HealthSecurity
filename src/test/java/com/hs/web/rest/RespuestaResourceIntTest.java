package com.hs.web.rest;

import com.hs.HealthSecurityApp;

import com.hs.domain.Respuesta;
import com.hs.repository.RespuestaRepository;
import com.hs.service.RespuestaService;
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

import com.hs.domain.enumeration.ValoracionEnum;
import com.hs.domain.enumeration.EstadoEnum;
/**
 * Test class for the RespuestaResource REST controller.
 *
 * @see RespuestaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthSecurityApp.class)
public class RespuestaResourceIntTest {

    private static final ValoracionEnum DEFAULT_VALORACION = ValoracionEnum.LEVE;
    private static final ValoracionEnum UPDATED_VALORACION = ValoracionEnum.MODERADO;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final EstadoEnum DEFAULT_ESTADO = EstadoEnum.PENDIENTE;
    private static final EstadoEnum UPDATED_ESTADO = EstadoEnum.RECHAZADO;

    @Autowired
    private RespuestaRepository respuestaRepository;

    

    @Autowired
    private RespuestaService respuestaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRespuestaMockMvc;

    private Respuesta respuesta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RespuestaResource respuestaResource = new RespuestaResource(respuestaService);
        this.restRespuestaMockMvc = MockMvcBuilders.standaloneSetup(respuestaResource)
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
    public static Respuesta createEntity(EntityManager em) {
        Respuesta respuesta = new Respuesta()
            .valoracion(DEFAULT_VALORACION)
            .descripcion(DEFAULT_DESCRIPCION)
            .estado(DEFAULT_ESTADO);
        return respuesta;
    }

    @Before
    public void initTest() {
        respuesta = createEntity(em);
    }

    @Test
    @Transactional
    public void createRespuesta() throws Exception {
        int databaseSizeBeforeCreate = respuestaRepository.findAll().size();

        // Create the Respuesta
        restRespuestaMockMvc.perform(post("/api/respuestas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respuesta)))
            .andExpect(status().isCreated());

        // Validate the Respuesta in the database
        List<Respuesta> respuestaList = respuestaRepository.findAll();
        assertThat(respuestaList).hasSize(databaseSizeBeforeCreate + 1);
        Respuesta testRespuesta = respuestaList.get(respuestaList.size() - 1);
        assertThat(testRespuesta.getValoracion()).isEqualTo(DEFAULT_VALORACION);
        assertThat(testRespuesta.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testRespuesta.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createRespuestaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = respuestaRepository.findAll().size();

        // Create the Respuesta with an existing ID
        respuesta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRespuestaMockMvc.perform(post("/api/respuestas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respuesta)))
            .andExpect(status().isBadRequest());

        // Validate the Respuesta in the database
        List<Respuesta> respuestaList = respuestaRepository.findAll();
        assertThat(respuestaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValoracionIsRequired() throws Exception {
        int databaseSizeBeforeTest = respuestaRepository.findAll().size();
        // set the field null
        respuesta.setValoracion(null);

        // Create the Respuesta, which fails.

        restRespuestaMockMvc.perform(post("/api/respuestas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respuesta)))
            .andExpect(status().isBadRequest());

        List<Respuesta> respuestaList = respuestaRepository.findAll();
        assertThat(respuestaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = respuestaRepository.findAll().size();
        // set the field null
        respuesta.setEstado(null);

        // Create the Respuesta, which fails.

        restRespuestaMockMvc.perform(post("/api/respuestas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respuesta)))
            .andExpect(status().isBadRequest());

        List<Respuesta> respuestaList = respuestaRepository.findAll();
        assertThat(respuestaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRespuestas() throws Exception {
        // Initialize the database
        respuestaRepository.saveAndFlush(respuesta);

        // Get all the respuestaList
        restRespuestaMockMvc.perform(get("/api/respuestas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(respuesta.getId().intValue())))
            .andExpect(jsonPath("$.[*].valoracion").value(hasItem(DEFAULT_VALORACION.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }
    

    @Test
    @Transactional
    public void getRespuesta() throws Exception {
        // Initialize the database
        respuestaRepository.saveAndFlush(respuesta);

        // Get the respuesta
        restRespuestaMockMvc.perform(get("/api/respuestas/{id}", respuesta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(respuesta.getId().intValue()))
            .andExpect(jsonPath("$.valoracion").value(DEFAULT_VALORACION.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRespuesta() throws Exception {
        // Get the respuesta
        restRespuestaMockMvc.perform(get("/api/respuestas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRespuesta() throws Exception {
        // Initialize the database
        respuestaService.save(respuesta);

        int databaseSizeBeforeUpdate = respuestaRepository.findAll().size();

        // Update the respuesta
        Respuesta updatedRespuesta = respuestaRepository.findById(respuesta.getId()).get();
        // Disconnect from session so that the updates on updatedRespuesta are not directly saved in db
        em.detach(updatedRespuesta);
        updatedRespuesta
            .valoracion(UPDATED_VALORACION)
            .descripcion(UPDATED_DESCRIPCION)
            .estado(UPDATED_ESTADO);

        restRespuestaMockMvc.perform(put("/api/respuestas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRespuesta)))
            .andExpect(status().isOk());

        // Validate the Respuesta in the database
        List<Respuesta> respuestaList = respuestaRepository.findAll();
        assertThat(respuestaList).hasSize(databaseSizeBeforeUpdate);
        Respuesta testRespuesta = respuestaList.get(respuestaList.size() - 1);
        assertThat(testRespuesta.getValoracion()).isEqualTo(UPDATED_VALORACION);
        assertThat(testRespuesta.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testRespuesta.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingRespuesta() throws Exception {
        int databaseSizeBeforeUpdate = respuestaRepository.findAll().size();

        // Create the Respuesta

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRespuestaMockMvc.perform(put("/api/respuestas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(respuesta)))
            .andExpect(status().isBadRequest());

        // Validate the Respuesta in the database
        List<Respuesta> respuestaList = respuestaRepository.findAll();
        assertThat(respuestaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRespuesta() throws Exception {
        // Initialize the database
        respuestaService.save(respuesta);

        int databaseSizeBeforeDelete = respuestaRepository.findAll().size();

        // Get the respuesta
        restRespuestaMockMvc.perform(delete("/api/respuestas/{id}", respuesta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Respuesta> respuestaList = respuestaRepository.findAll();
        assertThat(respuestaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Respuesta.class);
        Respuesta respuesta1 = new Respuesta();
        respuesta1.setId(1L);
        Respuesta respuesta2 = new Respuesta();
        respuesta2.setId(respuesta1.getId());
        assertThat(respuesta1).isEqualTo(respuesta2);
        respuesta2.setId(2L);
        assertThat(respuesta1).isNotEqualTo(respuesta2);
        respuesta1.setId(null);
        assertThat(respuesta1).isNotEqualTo(respuesta2);
    }
}
