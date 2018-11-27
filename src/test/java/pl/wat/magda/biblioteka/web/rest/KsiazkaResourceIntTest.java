package pl.wat.magda.biblioteka.web.rest;

import pl.wat.magda.biblioteka.BibliotekaTimApp;

import pl.wat.magda.biblioteka.domain.Ksiazka;
import pl.wat.magda.biblioteka.domain.Autor;
import pl.wat.magda.biblioteka.domain.Wydawnictwo;
import pl.wat.magda.biblioteka.domain.Gatunek;
import pl.wat.magda.biblioteka.repository.KsiazkaRepository;
import pl.wat.magda.biblioteka.repository.search.KsiazkaSearchRepository;
import pl.wat.magda.biblioteka.service.KsiazkaService;
import pl.wat.magda.biblioteka.service.dto.KsiazkaDTO;
import pl.wat.magda.biblioteka.service.mapper.KsiazkaMapper;
import pl.wat.magda.biblioteka.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static pl.wat.magda.biblioteka.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the KsiazkaResource REST controller.
 *
 * @see KsiazkaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BibliotekaTimApp.class)
public class KsiazkaResourceIntTest {

    private static final String DEFAULT_TYTUL = "AAAAAAAAAA";
    private static final String UPDATED_TYTUL = "BBBBBBBBBB";

    private static final String DEFAULT_TEMATYKA = "AAAAAAAAAA";
    private static final String UPDATED_TEMATYKA = "BBBBBBBBBB";

    private static final String DEFAULT_OPIS = "AAAAAAAAAA";
    private static final String UPDATED_OPIS = "BBBBBBBBBB";

    @Autowired
    private KsiazkaRepository ksiazkaRepository;

    @Autowired
    private KsiazkaMapper ksiazkaMapper;

    @Autowired
    private KsiazkaService ksiazkaService;

    /**
     * This repository is mocked in the pl.wat.magda.biblioteka.repository.search test package.
     *
     * @see pl.wat.magda.biblioteka.repository.search.KsiazkaSearchRepositoryMockConfiguration
     */
    @Autowired
    private KsiazkaSearchRepository mockKsiazkaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKsiazkaMockMvc;

    private Ksiazka ksiazka;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KsiazkaResource ksiazkaResource = new KsiazkaResource(ksiazkaService);
        this.restKsiazkaMockMvc = MockMvcBuilders.standaloneSetup(ksiazkaResource)
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
    public static Ksiazka createEntity(EntityManager em) {
        Ksiazka ksiazka = new Ksiazka()
            .tytul(DEFAULT_TYTUL)
            .tematyka(DEFAULT_TEMATYKA)
            .opis(DEFAULT_OPIS);
        // Add required entity
        Autor autor = AutorResourceIntTest.createEntity(em);
        em.persist(autor);
        em.flush();
        ksiazka.setAutor(autor);
        // Add required entity
        Wydawnictwo wydawnictwo = WydawnictwoResourceIntTest.createEntity(em);
        em.persist(wydawnictwo);
        em.flush();
        ksiazka.setWydawnictwo(wydawnictwo);
        // Add required entity
        Gatunek gatunek = GatunekResourceIntTest.createEntity(em);
        em.persist(gatunek);
        em.flush();
        ksiazka.setGatunek(gatunek);
        return ksiazka;
    }

    @Before
    public void initTest() {
        ksiazka = createEntity(em);
    }

    @Test
    @Transactional
    public void createKsiazka() throws Exception {
        int databaseSizeBeforeCreate = ksiazkaRepository.findAll().size();

        // Create the Ksiazka
        KsiazkaDTO ksiazkaDTO = ksiazkaMapper.toDto(ksiazka);
        restKsiazkaMockMvc.perform(post("/api/ksiazkas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ksiazkaDTO)))
            .andExpect(status().isCreated());

        // Validate the Ksiazka in the database
        List<Ksiazka> ksiazkaList = ksiazkaRepository.findAll();
        assertThat(ksiazkaList).hasSize(databaseSizeBeforeCreate + 1);
        Ksiazka testKsiazka = ksiazkaList.get(ksiazkaList.size() - 1);
        assertThat(testKsiazka.getTytul()).isEqualTo(DEFAULT_TYTUL);
        assertThat(testKsiazka.getTematyka()).isEqualTo(DEFAULT_TEMATYKA);
        assertThat(testKsiazka.getOpis()).isEqualTo(DEFAULT_OPIS);

        // Validate the Ksiazka in Elasticsearch
        verify(mockKsiazkaSearchRepository, times(1)).save(testKsiazka);
    }

    @Test
    @Transactional
    public void createKsiazkaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ksiazkaRepository.findAll().size();

        // Create the Ksiazka with an existing ID
        ksiazka.setId(1L);
        KsiazkaDTO ksiazkaDTO = ksiazkaMapper.toDto(ksiazka);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKsiazkaMockMvc.perform(post("/api/ksiazkas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ksiazkaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ksiazka in the database
        List<Ksiazka> ksiazkaList = ksiazkaRepository.findAll();
        assertThat(ksiazkaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Ksiazka in Elasticsearch
        verify(mockKsiazkaSearchRepository, times(0)).save(ksiazka);
    }

    @Test
    @Transactional
    public void checkTytulIsRequired() throws Exception {
        int databaseSizeBeforeTest = ksiazkaRepository.findAll().size();
        // set the field null
        ksiazka.setTytul(null);

        // Create the Ksiazka, which fails.
        KsiazkaDTO ksiazkaDTO = ksiazkaMapper.toDto(ksiazka);

        restKsiazkaMockMvc.perform(post("/api/ksiazkas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ksiazkaDTO)))
            .andExpect(status().isBadRequest());

        List<Ksiazka> ksiazkaList = ksiazkaRepository.findAll();
        assertThat(ksiazkaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKsiazkas() throws Exception {
        // Initialize the database
        ksiazkaRepository.saveAndFlush(ksiazka);

        // Get all the ksiazkaList
        restKsiazkaMockMvc.perform(get("/api/ksiazkas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ksiazka.getId().intValue())))
            .andExpect(jsonPath("$.[*].tytul").value(hasItem(DEFAULT_TYTUL.toString())))
            .andExpect(jsonPath("$.[*].tematyka").value(hasItem(DEFAULT_TEMATYKA.toString())))
            .andExpect(jsonPath("$.[*].opis").value(hasItem(DEFAULT_OPIS.toString())));
    }
    
    @Test
    @Transactional
    public void getKsiazka() throws Exception {
        // Initialize the database
        ksiazkaRepository.saveAndFlush(ksiazka);

        // Get the ksiazka
        restKsiazkaMockMvc.perform(get("/api/ksiazkas/{id}", ksiazka.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ksiazka.getId().intValue()))
            .andExpect(jsonPath("$.tytul").value(DEFAULT_TYTUL.toString()))
            .andExpect(jsonPath("$.tematyka").value(DEFAULT_TEMATYKA.toString()))
            .andExpect(jsonPath("$.opis").value(DEFAULT_OPIS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKsiazka() throws Exception {
        // Get the ksiazka
        restKsiazkaMockMvc.perform(get("/api/ksiazkas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKsiazka() throws Exception {
        // Initialize the database
        ksiazkaRepository.saveAndFlush(ksiazka);

        int databaseSizeBeforeUpdate = ksiazkaRepository.findAll().size();

        // Update the ksiazka
        Ksiazka updatedKsiazka = ksiazkaRepository.findById(ksiazka.getId()).get();
        // Disconnect from session so that the updates on updatedKsiazka are not directly saved in db
        em.detach(updatedKsiazka);
        updatedKsiazka
            .tytul(UPDATED_TYTUL)
            .tematyka(UPDATED_TEMATYKA)
            .opis(UPDATED_OPIS);
        KsiazkaDTO ksiazkaDTO = ksiazkaMapper.toDto(updatedKsiazka);

        restKsiazkaMockMvc.perform(put("/api/ksiazkas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ksiazkaDTO)))
            .andExpect(status().isOk());

        // Validate the Ksiazka in the database
        List<Ksiazka> ksiazkaList = ksiazkaRepository.findAll();
        assertThat(ksiazkaList).hasSize(databaseSizeBeforeUpdate);
        Ksiazka testKsiazka = ksiazkaList.get(ksiazkaList.size() - 1);
        assertThat(testKsiazka.getTytul()).isEqualTo(UPDATED_TYTUL);
        assertThat(testKsiazka.getTematyka()).isEqualTo(UPDATED_TEMATYKA);
        assertThat(testKsiazka.getOpis()).isEqualTo(UPDATED_OPIS);

        // Validate the Ksiazka in Elasticsearch
        verify(mockKsiazkaSearchRepository, times(1)).save(testKsiazka);
    }

    @Test
    @Transactional
    public void updateNonExistingKsiazka() throws Exception {
        int databaseSizeBeforeUpdate = ksiazkaRepository.findAll().size();

        // Create the Ksiazka
        KsiazkaDTO ksiazkaDTO = ksiazkaMapper.toDto(ksiazka);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKsiazkaMockMvc.perform(put("/api/ksiazkas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ksiazkaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ksiazka in the database
        List<Ksiazka> ksiazkaList = ksiazkaRepository.findAll();
        assertThat(ksiazkaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ksiazka in Elasticsearch
        verify(mockKsiazkaSearchRepository, times(0)).save(ksiazka);
    }

    @Test
    @Transactional
    public void deleteKsiazka() throws Exception {
        // Initialize the database
        ksiazkaRepository.saveAndFlush(ksiazka);

        int databaseSizeBeforeDelete = ksiazkaRepository.findAll().size();

        // Get the ksiazka
        restKsiazkaMockMvc.perform(delete("/api/ksiazkas/{id}", ksiazka.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ksiazka> ksiazkaList = ksiazkaRepository.findAll();
        assertThat(ksiazkaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Ksiazka in Elasticsearch
        verify(mockKsiazkaSearchRepository, times(1)).deleteById(ksiazka.getId());
    }

    @Test
    @Transactional
    public void searchKsiazka() throws Exception {
        // Initialize the database
        ksiazkaRepository.saveAndFlush(ksiazka);
        when(mockKsiazkaSearchRepository.search(queryStringQuery("id:" + ksiazka.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ksiazka), PageRequest.of(0, 1), 1));
        // Search the ksiazka
        restKsiazkaMockMvc.perform(get("/api/_search/ksiazkas?query=id:" + ksiazka.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ksiazka.getId().intValue())))
            .andExpect(jsonPath("$.[*].tytul").value(hasItem(DEFAULT_TYTUL)))
            .andExpect(jsonPath("$.[*].tematyka").value(hasItem(DEFAULT_TEMATYKA)))
            .andExpect(jsonPath("$.[*].opis").value(hasItem(DEFAULT_OPIS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ksiazka.class);
        Ksiazka ksiazka1 = new Ksiazka();
        ksiazka1.setId(1L);
        Ksiazka ksiazka2 = new Ksiazka();
        ksiazka2.setId(ksiazka1.getId());
        assertThat(ksiazka1).isEqualTo(ksiazka2);
        ksiazka2.setId(2L);
        assertThat(ksiazka1).isNotEqualTo(ksiazka2);
        ksiazka1.setId(null);
        assertThat(ksiazka1).isNotEqualTo(ksiazka2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KsiazkaDTO.class);
        KsiazkaDTO ksiazkaDTO1 = new KsiazkaDTO();
        ksiazkaDTO1.setId(1L);
        KsiazkaDTO ksiazkaDTO2 = new KsiazkaDTO();
        assertThat(ksiazkaDTO1).isNotEqualTo(ksiazkaDTO2);
        ksiazkaDTO2.setId(ksiazkaDTO1.getId());
        assertThat(ksiazkaDTO1).isEqualTo(ksiazkaDTO2);
        ksiazkaDTO2.setId(2L);
        assertThat(ksiazkaDTO1).isNotEqualTo(ksiazkaDTO2);
        ksiazkaDTO1.setId(null);
        assertThat(ksiazkaDTO1).isNotEqualTo(ksiazkaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ksiazkaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ksiazkaMapper.fromId(null)).isNull();
    }
}
