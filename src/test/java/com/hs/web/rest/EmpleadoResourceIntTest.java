package com.hs.web.rest;

import com.hs.HealthSecurityWebApp;

import com.hs.domain.Empleado;
import com.hs.domain.User;
import com.hs.repository.EmpleadoRepository;
import com.hs.service.EmpleadoService;
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

import com.hs.domain.enumeration.TipoDocumentoEnum;
/**
 * Test class for the EmpleadoResource REST controller.
 *
 * @see EmpleadoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthSecurityWebApp.class)
public class EmpleadoResourceIntTest {

    private static final TipoDocumentoEnum DEFAULT_TIPO_DOCUMENTO = TipoDocumentoEnum.CEDULA_CIUDADANIA;
    private static final TipoDocumentoEnum UPDATED_TIPO_DOCUMENTO = TipoDocumentoEnum.CEDULA_EXTRANJERIA;

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

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EmpleadoRepository empleadoRepository;

    

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmpleadoMockMvc;

    private Empleado empleado;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmpleadoResource empleadoResource = new EmpleadoResource(empleadoService);
        this.restEmpleadoMockMvc = MockMvcBuilders.standaloneSetup(empleadoResource)
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
    public static Empleado createEntity(EntityManager em) {
        Empleado empleado = new Empleado()
            .tipo_documento(DEFAULT_TIPO_DOCUMENTO)
            .documento(DEFAULT_DOCUMENTO)
            .primer_nombre(DEFAULT_PRIMER_NOMBRE)
            .segundo_nombre(DEFAULT_SEGUNDO_NOMBRE)
            .primer_apellido(DEFAULT_PRIMER_APELLIDO)
            .segundo_apellido(DEFAULT_SEGUNDO_APELLIDO)
            .direccion(DEFAULT_DIRECCION)
            .telefono(DEFAULT_TELEFONO)
            .nacimiento(DEFAULT_NACIMIENTO);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        empleado.setUser(user);
        return empleado;
    }

    @Before
    public void initTest() {
        empleado = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpleado() throws Exception {
        int databaseSizeBeforeCreate = empleadoRepository.findAll().size();

        // Create the Empleado
        restEmpleadoMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleado)))
            .andExpect(status().isCreated());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeCreate + 1);
        Empleado testEmpleado = empleadoList.get(empleadoList.size() - 1);
        assertThat(testEmpleado.getTipo_documento()).isEqualTo(DEFAULT_TIPO_DOCUMENTO);
        assertThat(testEmpleado.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testEmpleado.getPrimer_nombre()).isEqualTo(DEFAULT_PRIMER_NOMBRE);
        assertThat(testEmpleado.getSegundo_nombre()).isEqualTo(DEFAULT_SEGUNDO_NOMBRE);
        assertThat(testEmpleado.getPrimer_apellido()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testEmpleado.getSegundo_apellido()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO);
        assertThat(testEmpleado.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testEmpleado.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testEmpleado.getNacimiento()).isEqualTo(DEFAULT_NACIMIENTO);
    }

    @Test
    @Transactional
    public void createEmpleadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empleadoRepository.findAll().size();

        // Create the Empleado with an existing ID
        empleado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpleadoMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleado)))
            .andExpect(status().isBadRequest());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTipo_documentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setTipo_documento(null);

        // Create the Empleado, which fails.

        restEmpleadoMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleado)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setDocumento(null);

        // Create the Empleado, which fails.

        restEmpleadoMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleado)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrimer_nombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setPrimer_nombre(null);

        // Create the Empleado, which fails.

        restEmpleadoMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleado)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrimer_apellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setPrimer_apellido(null);

        // Create the Empleado, which fails.

        restEmpleadoMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleado)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSegundo_apellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setSegundo_apellido(null);

        // Create the Empleado, which fails.

        restEmpleadoMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleado)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmpleados() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        // Get all the empleadoList
        restEmpleadoMockMvc.perform(get("/api/empleados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empleado.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo_documento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].primer_nombre").value(hasItem(DEFAULT_PRIMER_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].segundo_nombre").value(hasItem(DEFAULT_SEGUNDO_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].primer_apellido").value(hasItem(DEFAULT_PRIMER_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].segundo_apellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].nacimiento").value(hasItem(DEFAULT_NACIMIENTO.toString())));
    }
    

    @Test
    @Transactional
    public void getEmpleado() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        // Get the empleado
        restEmpleadoMockMvc.perform(get("/api/empleados/{id}", empleado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(empleado.getId().intValue()))
            .andExpect(jsonPath("$.tipo_documento").value(DEFAULT_TIPO_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.primer_nombre").value(DEFAULT_PRIMER_NOMBRE.toString()))
            .andExpect(jsonPath("$.segundo_nombre").value(DEFAULT_SEGUNDO_NOMBRE.toString()))
            .andExpect(jsonPath("$.primer_apellido").value(DEFAULT_PRIMER_APELLIDO.toString()))
            .andExpect(jsonPath("$.segundo_apellido").value(DEFAULT_SEGUNDO_APELLIDO.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.nacimiento").value(DEFAULT_NACIMIENTO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmpleado() throws Exception {
        // Get the empleado
        restEmpleadoMockMvc.perform(get("/api/empleados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpleado() throws Exception {
        // Initialize the database
        empleadoService.save(empleado);

        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();

        // Update the empleado
        Empleado updatedEmpleado = empleadoRepository.findById(empleado.getId()).get();
        // Disconnect from session so that the updates on updatedEmpleado are not directly saved in db
        em.detach(updatedEmpleado);
        updatedEmpleado
            .tipo_documento(UPDATED_TIPO_DOCUMENTO)
            .documento(UPDATED_DOCUMENTO)
            .primer_nombre(UPDATED_PRIMER_NOMBRE)
            .segundo_nombre(UPDATED_SEGUNDO_NOMBRE)
            .primer_apellido(UPDATED_PRIMER_APELLIDO)
            .segundo_apellido(UPDATED_SEGUNDO_APELLIDO)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .nacimiento(UPDATED_NACIMIENTO);

        restEmpleadoMockMvc.perform(put("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmpleado)))
            .andExpect(status().isOk());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
        Empleado testEmpleado = empleadoList.get(empleadoList.size() - 1);
        assertThat(testEmpleado.getTipo_documento()).isEqualTo(UPDATED_TIPO_DOCUMENTO);
        assertThat(testEmpleado.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testEmpleado.getPrimer_nombre()).isEqualTo(UPDATED_PRIMER_NOMBRE);
        assertThat(testEmpleado.getSegundo_nombre()).isEqualTo(UPDATED_SEGUNDO_NOMBRE);
        assertThat(testEmpleado.getPrimer_apellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testEmpleado.getSegundo_apellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testEmpleado.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testEmpleado.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testEmpleado.getNacimiento()).isEqualTo(UPDATED_NACIMIENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();

        // Create the Empleado

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmpleadoMockMvc.perform(put("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleado)))
            .andExpect(status().isBadRequest());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmpleado() throws Exception {
        // Initialize the database
        empleadoService.save(empleado);

        int databaseSizeBeforeDelete = empleadoRepository.findAll().size();

        // Get the empleado
        restEmpleadoMockMvc.perform(delete("/api/empleados/{id}", empleado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Empleado.class);
        Empleado empleado1 = new Empleado();
        empleado1.setId(1L);
        Empleado empleado2 = new Empleado();
        empleado2.setId(empleado1.getId());
        assertThat(empleado1).isEqualTo(empleado2);
        empleado2.setId(2L);
        assertThat(empleado1).isNotEqualTo(empleado2);
        empleado1.setId(null);
        assertThat(empleado1).isNotEqualTo(empleado2);
    }
}
