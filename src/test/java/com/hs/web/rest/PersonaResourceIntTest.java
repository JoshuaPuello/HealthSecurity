package com.hs.web.rest;

import com.hs.HealthSecurityApp;

import com.hs.domain.Persona;
import com.hs.repository.PersonaRepository;
import com.hs.service.PersonaService;
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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.hs.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hs.domain.enumeration.TipoDocumento;
/**
 * Test class for the PersonaResource REST controller.
 *
 * @see PersonaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthSecurityApp.class)
public class PersonaResourceIntTest {

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final TipoDocumento DEFAULT_TIPO_DOCUMENTO = TipoDocumento.CEDULA_CIUDADANIA;
    private static final TipoDocumento UPDATED_TIPO_DOCUMENTO = TipoDocumento.CEDULA_EXTRANJERIA;

    @Autowired
    private PersonaRepository personaRepository;

    

    @Autowired
    private PersonaService personaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonaMockMvc;

    private Persona persona;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonaResource personaResource = new PersonaResource(personaService);
        this.restPersonaMockMvc = MockMvcBuilders.standaloneSetup(personaResource)
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
    public static Persona createEntity(EntityManager em) {
        Persona persona = new Persona()
            .documento(DEFAULT_DOCUMENTO)
            .primer_nombre(DEFAULT_PRIMER_NOMBRE)
            .segundo_nombre(DEFAULT_SEGUNDO_NOMBRE)
            .primer_apellido(DEFAULT_PRIMER_APELLIDO)
            .segundo_apellido(DEFAULT_SEGUNDO_APELLIDO)
            .correo(DEFAULT_CORREO)
            .direccion(DEFAULT_DIRECCION)
            .telefono(DEFAULT_TELEFONO)
            .nacimiento(DEFAULT_NACIMIENTO)
            .tipo_documento(DEFAULT_TIPO_DOCUMENTO);
        return persona;
    }

    @Before
    public void initTest() {
        persona = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersona() throws Exception {
        int databaseSizeBeforeCreate = personaRepository.findAll().size();

        // Create the Persona
        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isCreated());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeCreate + 1);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testPersona.getPrimer_nombre()).isEqualTo(DEFAULT_PRIMER_NOMBRE);
        assertThat(testPersona.getSegundo_nombre()).isEqualTo(DEFAULT_SEGUNDO_NOMBRE);
        assertThat(testPersona.getPrimer_apellido()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testPersona.getSegundo_apellido()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO);
        assertThat(testPersona.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testPersona.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testPersona.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testPersona.getNacimiento()).isEqualTo(DEFAULT_NACIMIENTO);
        assertThat(testPersona.getTipo_documento()).isEqualTo(DEFAULT_TIPO_DOCUMENTO);
    }

    @Test
    @Transactional
    public void createPersonaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personaRepository.findAll().size();

        // Create the Persona with an existing ID
        persona.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setDocumento(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrimer_nombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setPrimer_nombre(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrimer_apellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setPrimer_apellido(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSegundo_apellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setSegundo_apellido(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorreoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setCorreo(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNacimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setNacimiento(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipo_documentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setTipo_documento(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonas() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList
        restPersonaMockMvc.perform(get("/api/personas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(persona.getId().intValue())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].primer_nombre").value(hasItem(DEFAULT_PRIMER_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].segundo_nombre").value(hasItem(DEFAULT_SEGUNDO_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].primer_apellido").value(hasItem(DEFAULT_PRIMER_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].segundo_apellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].nacimiento").value(hasItem(DEFAULT_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].tipo_documento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())));
    }
    

    @Test
    @Transactional
    public void getPersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get the persona
        restPersonaMockMvc.perform(get("/api/personas/{id}", persona.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(persona.getId().intValue()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.primer_nombre").value(DEFAULT_PRIMER_NOMBRE.toString()))
            .andExpect(jsonPath("$.segundo_nombre").value(DEFAULT_SEGUNDO_NOMBRE.toString()))
            .andExpect(jsonPath("$.primer_apellido").value(DEFAULT_PRIMER_APELLIDO.toString()))
            .andExpect(jsonPath("$.segundo_apellido").value(DEFAULT_SEGUNDO_APELLIDO.toString()))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.nacimiento").value(DEFAULT_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.tipo_documento").value(DEFAULT_TIPO_DOCUMENTO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPersona() throws Exception {
        // Get the persona
        restPersonaMockMvc.perform(get("/api/personas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersona() throws Exception {
        // Initialize the database
        personaService.save(persona);

        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // Update the persona
        Persona updatedPersona = personaRepository.findById(persona.getId()).get();
        // Disconnect from session so that the updates on updatedPersona are not directly saved in db
        em.detach(updatedPersona);
        updatedPersona
            .documento(UPDATED_DOCUMENTO)
            .primer_nombre(UPDATED_PRIMER_NOMBRE)
            .segundo_nombre(UPDATED_SEGUNDO_NOMBRE)
            .primer_apellido(UPDATED_PRIMER_APELLIDO)
            .segundo_apellido(UPDATED_SEGUNDO_APELLIDO)
            .correo(UPDATED_CORREO)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .nacimiento(UPDATED_NACIMIENTO)
            .tipo_documento(UPDATED_TIPO_DOCUMENTO);

        restPersonaMockMvc.perform(put("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersona)))
            .andExpect(status().isOk());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testPersona.getPrimer_nombre()).isEqualTo(UPDATED_PRIMER_NOMBRE);
        assertThat(testPersona.getSegundo_nombre()).isEqualTo(UPDATED_SEGUNDO_NOMBRE);
        assertThat(testPersona.getPrimer_apellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testPersona.getSegundo_apellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testPersona.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testPersona.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testPersona.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testPersona.getNacimiento()).isEqualTo(UPDATED_NACIMIENTO);
        assertThat(testPersona.getTipo_documento()).isEqualTo(UPDATED_TIPO_DOCUMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingPersona() throws Exception {
        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // Create the Persona

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonaMockMvc.perform(put("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersona() throws Exception {
        // Initialize the database
        personaService.save(persona);

        int databaseSizeBeforeDelete = personaRepository.findAll().size();

        // Get the persona
        restPersonaMockMvc.perform(delete("/api/personas/{id}", persona.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Persona.class);
        Persona persona1 = new Persona();
        persona1.setId(1L);
        Persona persona2 = new Persona();
        persona2.setId(persona1.getId());
        assertThat(persona1).isEqualTo(persona2);
        persona2.setId(2L);
        assertThat(persona1).isNotEqualTo(persona2);
        persona1.setId(null);
        assertThat(persona1).isNotEqualTo(persona2);
    }
}
