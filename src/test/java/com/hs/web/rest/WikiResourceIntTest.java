package com.hs.web.rest;

import com.hs.HealthSecurityWebApp;

import com.hs.domain.Wiki;
import com.hs.domain.Tema;
import com.hs.domain.Categoria;
import com.hs.repository.WikiRepository;
import com.hs.service.WikiService;
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
 * Test class for the WikiResource REST controller.
 *
 * @see WikiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthSecurityWebApp.class)
public class WikiResourceIntTest {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private WikiRepository wikiRepository;

    

    @Autowired
    private WikiService wikiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWikiMockMvc;

    private Wiki wiki;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WikiResource wikiResource = new WikiResource(wikiService);
        this.restWikiMockMvc = MockMvcBuilders.standaloneSetup(wikiResource)
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
    public static Wiki createEntity(EntityManager em) {
        Wiki wiki = new Wiki()
            .titulo(DEFAULT_TITULO)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .descripcion(DEFAULT_DESCRIPCION);
        // Add required entity
        Tema tema = TemaResourceIntTest.createEntity(em);
        em.persist(tema);
        em.flush();
        wiki.setTema(tema);
        // Add required entity
        Categoria categoria = CategoriaResourceIntTest.createEntity(em);
        em.persist(categoria);
        em.flush();
        wiki.setCategoria(categoria);
        return wiki;
    }

    @Before
    public void initTest() {
        wiki = createEntity(em);
    }

    @Test
    @Transactional
    public void createWiki() throws Exception {
        int databaseSizeBeforeCreate = wikiRepository.findAll().size();

        // Create the Wiki
        restWikiMockMvc.perform(post("/api/wikis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wiki)))
            .andExpect(status().isCreated());

        // Validate the Wiki in the database
        List<Wiki> wikiList = wikiRepository.findAll();
        assertThat(wikiList).hasSize(databaseSizeBeforeCreate + 1);
        Wiki testWiki = wikiList.get(wikiList.size() - 1);
        assertThat(testWiki.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testWiki.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testWiki.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testWiki.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createWikiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wikiRepository.findAll().size();

        // Create the Wiki with an existing ID
        wiki.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWikiMockMvc.perform(post("/api/wikis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wiki)))
            .andExpect(status().isBadRequest());

        // Validate the Wiki in the database
        List<Wiki> wikiList = wikiRepository.findAll();
        assertThat(wikiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = wikiRepository.findAll().size();
        // set the field null
        wiki.setTitulo(null);

        // Create the Wiki, which fails.

        restWikiMockMvc.perform(post("/api/wikis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wiki)))
            .andExpect(status().isBadRequest());

        List<Wiki> wikiList = wikiRepository.findAll();
        assertThat(wikiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWikis() throws Exception {
        // Initialize the database
        wikiRepository.saveAndFlush(wiki);

        // Get all the wikiList
        restWikiMockMvc.perform(get("/api/wikis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wiki.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    

    @Test
    @Transactional
    public void getWiki() throws Exception {
        // Initialize the database
        wikiRepository.saveAndFlush(wiki);

        // Get the wiki
        restWikiMockMvc.perform(get("/api/wikis/{id}", wiki.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wiki.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingWiki() throws Exception {
        // Get the wiki
        restWikiMockMvc.perform(get("/api/wikis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWiki() throws Exception {
        // Initialize the database
        wikiService.save(wiki);

        int databaseSizeBeforeUpdate = wikiRepository.findAll().size();

        // Update the wiki
        Wiki updatedWiki = wikiRepository.findById(wiki.getId()).get();
        // Disconnect from session so that the updates on updatedWiki are not directly saved in db
        em.detach(updatedWiki);
        updatedWiki
            .titulo(UPDATED_TITULO)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .descripcion(UPDATED_DESCRIPCION);

        restWikiMockMvc.perform(put("/api/wikis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWiki)))
            .andExpect(status().isOk());

        // Validate the Wiki in the database
        List<Wiki> wikiList = wikiRepository.findAll();
        assertThat(wikiList).hasSize(databaseSizeBeforeUpdate);
        Wiki testWiki = wikiList.get(wikiList.size() - 1);
        assertThat(testWiki.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testWiki.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testWiki.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testWiki.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingWiki() throws Exception {
        int databaseSizeBeforeUpdate = wikiRepository.findAll().size();

        // Create the Wiki

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWikiMockMvc.perform(put("/api/wikis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wiki)))
            .andExpect(status().isBadRequest());

        // Validate the Wiki in the database
        List<Wiki> wikiList = wikiRepository.findAll();
        assertThat(wikiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWiki() throws Exception {
        // Initialize the database
        wikiService.save(wiki);

        int databaseSizeBeforeDelete = wikiRepository.findAll().size();

        // Get the wiki
        restWikiMockMvc.perform(delete("/api/wikis/{id}", wiki.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Wiki> wikiList = wikiRepository.findAll();
        assertThat(wikiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wiki.class);
        Wiki wiki1 = new Wiki();
        wiki1.setId(1L);
        Wiki wiki2 = new Wiki();
        wiki2.setId(wiki1.getId());
        assertThat(wiki1).isEqualTo(wiki2);
        wiki2.setId(2L);
        assertThat(wiki1).isNotEqualTo(wiki2);
        wiki1.setId(null);
        assertThat(wiki1).isNotEqualTo(wiki2);
    }
}
