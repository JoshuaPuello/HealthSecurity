package com.hs.web.rest;

import com.hs.HealthSecurityWebApp;

import com.hs.domain.Reporte;
import com.hs.repository.ReporteRepository;
import com.hs.service.ReporteService;
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
import com.hs.domain.enumeration.LugarEventoEnum;
import com.hs.domain.enumeration.TipoEventoEnum;
/**
 * Test class for the ReporteResource REST controller.
 *
 * @see ReporteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthSecurityWebApp.class)
public class ReporteResourceIntTest {

    private static final ValoracionEnum DEFAULT_VALORACION = ValoracionEnum.LEVE;
    private static final ValoracionEnum UPDATED_VALORACION = ValoracionEnum.MODERADO;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_ACCINONES_REALIZADAS = "AAAAAAAAAA";
    private static final String UPDATED_ACCINONES_REALIZADAS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_EVIDENCIA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EVIDENCIA = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_EVIDENCIA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EVIDENCIA_CONTENT_TYPE = "image/png";

    private static final LugarEventoEnum DEFAULT_LUGAR_EVENTO = LugarEventoEnum.DENTRO_EMPRESA;
    private static final LugarEventoEnum UPDATED_LUGAR_EVENTO = LugarEventoEnum.FUERA_EMPRESA;

    private static final TipoEventoEnum DEFAULT_TIPO_EVENTO = TipoEventoEnum.ACCIDENTE;
    private static final TipoEventoEnum UPDATED_TIPO_EVENTO = TipoEventoEnum.INCIDENTE;

    private static final Boolean DEFAULT_B_LABORES = false;
    private static final Boolean UPDATED_B_LABORES = true;

    private static final Boolean DEFAULT_B_REPORTADO = false;
    private static final Boolean UPDATED_B_REPORTADO = true;

    @Autowired
    private ReporteRepository reporteRepository;

    

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReporteMockMvc;

    private Reporte reporte;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReporteResource reporteResource = new ReporteResource(reporteService);
        this.restReporteMockMvc = MockMvcBuilders.standaloneSetup(reporteResource)
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
    public static Reporte createEntity(EntityManager em) {
        Reporte reporte = new Reporte()
            .valoracion(DEFAULT_VALORACION)
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .accinones_realizadas(DEFAULT_ACCINONES_REALIZADAS)
            .evidencia(DEFAULT_EVIDENCIA)
            .evidenciaContentType(DEFAULT_EVIDENCIA_CONTENT_TYPE)
            .lugar_evento(DEFAULT_LUGAR_EVENTO)
            .tipo_evento(DEFAULT_TIPO_EVENTO)
            .bLabores(DEFAULT_B_LABORES)
            .bReportado(DEFAULT_B_REPORTADO);
        return reporte;
    }

    @Before
    public void initTest() {
        reporte = createEntity(em);
    }

    @Test
    @Transactional
    public void createReporte() throws Exception {
        int databaseSizeBeforeCreate = reporteRepository.findAll().size();

        // Create the Reporte
        restReporteMockMvc.perform(post("/api/reportes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isCreated());

        // Validate the Reporte in the database
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeCreate + 1);
        Reporte testReporte = reporteList.get(reporteList.size() - 1);
        assertThat(testReporte.getValoracion()).isEqualTo(DEFAULT_VALORACION);
        assertThat(testReporte.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testReporte.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testReporte.getAccinones_realizadas()).isEqualTo(DEFAULT_ACCINONES_REALIZADAS);
        assertThat(testReporte.getEvidencia()).isEqualTo(DEFAULT_EVIDENCIA);
        assertThat(testReporte.getEvidenciaContentType()).isEqualTo(DEFAULT_EVIDENCIA_CONTENT_TYPE);
        assertThat(testReporte.getLugar_evento()).isEqualTo(DEFAULT_LUGAR_EVENTO);
        assertThat(testReporte.getTipo_evento()).isEqualTo(DEFAULT_TIPO_EVENTO);
        assertThat(testReporte.isbLabores()).isEqualTo(DEFAULT_B_LABORES);
        assertThat(testReporte.isbReportado()).isEqualTo(DEFAULT_B_REPORTADO);
    }

    @Test
    @Transactional
    public void createReporteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reporteRepository.findAll().size();

        // Create the Reporte with an existing ID
        reporte.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReporteMockMvc.perform(post("/api/reportes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isBadRequest());

        // Validate the Reporte in the database
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValoracionIsRequired() throws Exception {
        int databaseSizeBeforeTest = reporteRepository.findAll().size();
        // set the field null
        reporte.setValoracion(null);

        // Create the Reporte, which fails.

        restReporteMockMvc.perform(post("/api/reportes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isBadRequest());

        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = reporteRepository.findAll().size();
        // set the field null
        reporte.setNombre(null);

        // Create the Reporte, which fails.

        restReporteMockMvc.perform(post("/api/reportes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isBadRequest());

        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLugar_eventoIsRequired() throws Exception {
        int databaseSizeBeforeTest = reporteRepository.findAll().size();
        // set the field null
        reporte.setLugar_evento(null);

        // Create the Reporte, which fails.

        restReporteMockMvc.perform(post("/api/reportes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isBadRequest());

        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipo_eventoIsRequired() throws Exception {
        int databaseSizeBeforeTest = reporteRepository.findAll().size();
        // set the field null
        reporte.setTipo_evento(null);

        // Create the Reporte, which fails.

        restReporteMockMvc.perform(post("/api/reportes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isBadRequest());

        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkbLaboresIsRequired() throws Exception {
        int databaseSizeBeforeTest = reporteRepository.findAll().size();
        // set the field null
        reporte.setbLabores(null);

        // Create the Reporte, which fails.

        restReporteMockMvc.perform(post("/api/reportes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isBadRequest());

        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkbReportadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = reporteRepository.findAll().size();
        // set the field null
        reporte.setbReportado(null);

        // Create the Reporte, which fails.

        restReporteMockMvc.perform(post("/api/reportes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isBadRequest());

        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReportes() throws Exception {
        // Initialize the database
        reporteRepository.saveAndFlush(reporte);

        // Get all the reporteList
        restReporteMockMvc.perform(get("/api/reportes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reporte.getId().intValue())))
            .andExpect(jsonPath("$.[*].valoracion").value(hasItem(DEFAULT_VALORACION.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].accinones_realizadas").value(hasItem(DEFAULT_ACCINONES_REALIZADAS.toString())))
            .andExpect(jsonPath("$.[*].evidenciaContentType").value(hasItem(DEFAULT_EVIDENCIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].evidencia").value(hasItem(Base64Utils.encodeToString(DEFAULT_EVIDENCIA))))
            .andExpect(jsonPath("$.[*].lugar_evento").value(hasItem(DEFAULT_LUGAR_EVENTO.toString())))
            .andExpect(jsonPath("$.[*].tipo_evento").value(hasItem(DEFAULT_TIPO_EVENTO.toString())))
            .andExpect(jsonPath("$.[*].bLabores").value(hasItem(DEFAULT_B_LABORES.booleanValue())))
            .andExpect(jsonPath("$.[*].bReportado").value(hasItem(DEFAULT_B_REPORTADO.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getReporte() throws Exception {
        // Initialize the database
        reporteRepository.saveAndFlush(reporte);

        // Get the reporte
        restReporteMockMvc.perform(get("/api/reportes/{id}", reporte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reporte.getId().intValue()))
            .andExpect(jsonPath("$.valoracion").value(DEFAULT_VALORACION.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.accinones_realizadas").value(DEFAULT_ACCINONES_REALIZADAS.toString()))
            .andExpect(jsonPath("$.evidenciaContentType").value(DEFAULT_EVIDENCIA_CONTENT_TYPE))
            .andExpect(jsonPath("$.evidencia").value(Base64Utils.encodeToString(DEFAULT_EVIDENCIA)))
            .andExpect(jsonPath("$.lugar_evento").value(DEFAULT_LUGAR_EVENTO.toString()))
            .andExpect(jsonPath("$.tipo_evento").value(DEFAULT_TIPO_EVENTO.toString()))
            .andExpect(jsonPath("$.bLabores").value(DEFAULT_B_LABORES.booleanValue()))
            .andExpect(jsonPath("$.bReportado").value(DEFAULT_B_REPORTADO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingReporte() throws Exception {
        // Get the reporte
        restReporteMockMvc.perform(get("/api/reportes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReporte() throws Exception {
        // Initialize the database
        reporteService.save(reporte);

        int databaseSizeBeforeUpdate = reporteRepository.findAll().size();

        // Update the reporte
        Reporte updatedReporte = reporteRepository.findById(reporte.getId()).get();
        // Disconnect from session so that the updates on updatedReporte are not directly saved in db
        em.detach(updatedReporte);
        updatedReporte
            .valoracion(UPDATED_VALORACION)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .accinones_realizadas(UPDATED_ACCINONES_REALIZADAS)
            .evidencia(UPDATED_EVIDENCIA)
            .evidenciaContentType(UPDATED_EVIDENCIA_CONTENT_TYPE)
            .lugar_evento(UPDATED_LUGAR_EVENTO)
            .tipo_evento(UPDATED_TIPO_EVENTO)
            .bLabores(UPDATED_B_LABORES)
            .bReportado(UPDATED_B_REPORTADO);

        restReporteMockMvc.perform(put("/api/reportes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReporte)))
            .andExpect(status().isOk());

        // Validate the Reporte in the database
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeUpdate);
        Reporte testReporte = reporteList.get(reporteList.size() - 1);
        assertThat(testReporte.getValoracion()).isEqualTo(UPDATED_VALORACION);
        assertThat(testReporte.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testReporte.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testReporte.getAccinones_realizadas()).isEqualTo(UPDATED_ACCINONES_REALIZADAS);
        assertThat(testReporte.getEvidencia()).isEqualTo(UPDATED_EVIDENCIA);
        assertThat(testReporte.getEvidenciaContentType()).isEqualTo(UPDATED_EVIDENCIA_CONTENT_TYPE);
        assertThat(testReporte.getLugar_evento()).isEqualTo(UPDATED_LUGAR_EVENTO);
        assertThat(testReporte.getTipo_evento()).isEqualTo(UPDATED_TIPO_EVENTO);
        assertThat(testReporte.isbLabores()).isEqualTo(UPDATED_B_LABORES);
        assertThat(testReporte.isbReportado()).isEqualTo(UPDATED_B_REPORTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingReporte() throws Exception {
        int databaseSizeBeforeUpdate = reporteRepository.findAll().size();

        // Create the Reporte

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReporteMockMvc.perform(put("/api/reportes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reporte)))
            .andExpect(status().isBadRequest());

        // Validate the Reporte in the database
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReporte() throws Exception {
        // Initialize the database
        reporteService.save(reporte);

        int databaseSizeBeforeDelete = reporteRepository.findAll().size();

        // Get the reporte
        restReporteMockMvc.perform(delete("/api/reportes/{id}", reporte.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reporte> reporteList = reporteRepository.findAll();
        assertThat(reporteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reporte.class);
        Reporte reporte1 = new Reporte();
        reporte1.setId(1L);
        Reporte reporte2 = new Reporte();
        reporte2.setId(reporte1.getId());
        assertThat(reporte1).isEqualTo(reporte2);
        reporte2.setId(2L);
        assertThat(reporte1).isNotEqualTo(reporte2);
        reporte1.setId(null);
        assertThat(reporte1).isNotEqualTo(reporte2);
    }
}
