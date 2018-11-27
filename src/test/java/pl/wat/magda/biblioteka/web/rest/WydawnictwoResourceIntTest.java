package pl.wat.magda.biblioteka.web.rest;

import pl.wat.magda.biblioteka.BibliotekaTimApp;

import pl.wat.magda.biblioteka.domain.Wydawnictwo;
import pl.wat.magda.biblioteka.repository.WydawnictwoRepository;
import pl.wat.magda.biblioteka.repository.search.WydawnictwoSearchRepository;
import pl.wat.magda.biblioteka.service.WydawnictwoService;
import pl.wat.magda.biblioteka.service.dto.WydawnictwoDTO;
import pl.wat.magda.biblioteka.service.mapper.WydawnictwoMapper;
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
 * Test class for the WydawnictwoResource REST controller.
 *
 * @see WydawnictwoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BibliotekaTimApp.class)
public class WydawnictwoResourceIntTest {

    private static final String DEFAULT_NAZWA = "AAAAAAAAAA";
    private static final String UPDATED_NAZWA = "BBBBBBBBBB";

    private static final String DEFAULT_ADRES = "AAAAAAAAAA";
    private static final String UPDATED_ADRES = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private WydawnictwoRepository wydawnictwoRepository;

    @Autowired
    private WydawnictwoMapper wydawnictwoMapper;

    @Autowired
    private WydawnictwoService wydawnictwoService;

    /**
     * This repository is mocked in the pl.wat.magda.biblioteka.repository.search test package.
     *
     * @see pl.wat.magda.biblioteka.repository.search.WydawnictwoSearchRepositoryMockConfiguration
     */
    @Autowired
    private WydawnictwoSearchRepository mockWydawnictwoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWydawnictwoMockMvc;

    private Wydawnictwo wydawnictwo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WydawnictwoResource wydawnictwoResource = new WydawnictwoResource(wydawnictwoService);
        this.restWydawnictwoMockMvc = MockMvcBuilders.standaloneSetup(wydawnictwoResource)
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
    public static Wydawnictwo createEntity(EntityManager em) {
        Wydawnictwo wydawnictwo = new Wydawnictwo()
            .nazwa(DEFAULT_NAZWA)
            .adres(DEFAULT_ADRES)
            .email(DEFAULT_EMAIL);
        return wydawnictwo;
    }

    @Before
    public void initTest() {
        wydawnictwo = createEntity(em);
    }

    @Test
    @Transactional
    public void createWydawnictwo() throws Exception {
        int databaseSizeBeforeCreate = wydawnictwoRepository.findAll().size();

        // Create the Wydawnictwo
        WydawnictwoDTO wydawnictwoDTO = wydawnictwoMapper.toDto(wydawnictwo);
        restWydawnictwoMockMvc.perform(post("/api/wydawnictwos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wydawnictwoDTO)))
            .andExpect(status().isCreated());

        // Validate the Wydawnictwo in the database
        List<Wydawnictwo> wydawnictwoList = wydawnictwoRepository.findAll();
        assertThat(wydawnictwoList).hasSize(databaseSizeBeforeCreate + 1);
        Wydawnictwo testWydawnictwo = wydawnictwoList.get(wydawnictwoList.size() - 1);
        assertThat(testWydawnictwo.getNazwa()).isEqualTo(DEFAULT_NAZWA);
        assertThat(testWydawnictwo.getAdres()).isEqualTo(DEFAULT_ADRES);
        assertThat(testWydawnictwo.getEmail()).isEqualTo(DEFAULT_EMAIL);

        // Validate the Wydawnictwo in Elasticsearch
        verify(mockWydawnictwoSearchRepository, times(1)).save(testWydawnictwo);
    }

    @Test
    @Transactional
    public void createWydawnictwoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wydawnictwoRepository.findAll().size();

        // Create the Wydawnictwo with an existing ID
        wydawnictwo.setId(1L);
        WydawnictwoDTO wydawnictwoDTO = wydawnictwoMapper.toDto(wydawnictwo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWydawnictwoMockMvc.perform(post("/api/wydawnictwos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wydawnictwoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wydawnictwo in the database
        List<Wydawnictwo> wydawnictwoList = wydawnictwoRepository.findAll();
        assertThat(wydawnictwoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Wydawnictwo in Elasticsearch
        verify(mockWydawnictwoSearchRepository, times(0)).save(wydawnictwo);
    }

    @Test
    @Transactional
    public void checkNazwaIsRequired() throws Exception {
        int databaseSizeBeforeTest = wydawnictwoRepository.findAll().size();
        // set the field null
        wydawnictwo.setNazwa(null);

        // Create the Wydawnictwo, which fails.
        WydawnictwoDTO wydawnictwoDTO = wydawnictwoMapper.toDto(wydawnictwo);

        restWydawnictwoMockMvc.perform(post("/api/wydawnictwos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wydawnictwoDTO)))
            .andExpect(status().isBadRequest());

        List<Wydawnictwo> wydawnictwoList = wydawnictwoRepository.findAll();
        assertThat(wydawnictwoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresIsRequired() throws Exception {
        int databaseSizeBeforeTest = wydawnictwoRepository.findAll().size();
        // set the field null
        wydawnictwo.setAdres(null);

        // Create the Wydawnictwo, which fails.
        WydawnictwoDTO wydawnictwoDTO = wydawnictwoMapper.toDto(wydawnictwo);

        restWydawnictwoMockMvc.perform(post("/api/wydawnictwos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wydawnictwoDTO)))
            .andExpect(status().isBadRequest());

        List<Wydawnictwo> wydawnictwoList = wydawnictwoRepository.findAll();
        assertThat(wydawnictwoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWydawnictwos() throws Exception {
        // Initialize the database
        wydawnictwoRepository.saveAndFlush(wydawnictwo);

        // Get all the wydawnictwoList
        restWydawnictwoMockMvc.perform(get("/api/wydawnictwos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wydawnictwo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nazwa").value(hasItem(DEFAULT_NAZWA.toString())))
            .andExpect(jsonPath("$.[*].adres").value(hasItem(DEFAULT_ADRES.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getWydawnictwo() throws Exception {
        // Initialize the database
        wydawnictwoRepository.saveAndFlush(wydawnictwo);

        // Get the wydawnictwo
        restWydawnictwoMockMvc.perform(get("/api/wydawnictwos/{id}", wydawnictwo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wydawnictwo.getId().intValue()))
            .andExpect(jsonPath("$.nazwa").value(DEFAULT_NAZWA.toString()))
            .andExpect(jsonPath("$.adres").value(DEFAULT_ADRES.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWydawnictwo() throws Exception {
        // Get the wydawnictwo
        restWydawnictwoMockMvc.perform(get("/api/wydawnictwos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWydawnictwo() throws Exception {
        // Initialize the database
        wydawnictwoRepository.saveAndFlush(wydawnictwo);

        int databaseSizeBeforeUpdate = wydawnictwoRepository.findAll().size();

        // Update the wydawnictwo
        Wydawnictwo updatedWydawnictwo = wydawnictwoRepository.findById(wydawnictwo.getId()).get();
        // Disconnect from session so that the updates on updatedWydawnictwo are not directly saved in db
        em.detach(updatedWydawnictwo);
        updatedWydawnictwo
            .nazwa(UPDATED_NAZWA)
            .adres(UPDATED_ADRES)
            .email(UPDATED_EMAIL);
        WydawnictwoDTO wydawnictwoDTO = wydawnictwoMapper.toDto(updatedWydawnictwo);

        restWydawnictwoMockMvc.perform(put("/api/wydawnictwos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wydawnictwoDTO)))
            .andExpect(status().isOk());

        // Validate the Wydawnictwo in the database
        List<Wydawnictwo> wydawnictwoList = wydawnictwoRepository.findAll();
        assertThat(wydawnictwoList).hasSize(databaseSizeBeforeUpdate);
        Wydawnictwo testWydawnictwo = wydawnictwoList.get(wydawnictwoList.size() - 1);
        assertThat(testWydawnictwo.getNazwa()).isEqualTo(UPDATED_NAZWA);
        assertThat(testWydawnictwo.getAdres()).isEqualTo(UPDATED_ADRES);
        assertThat(testWydawnictwo.getEmail()).isEqualTo(UPDATED_EMAIL);

        // Validate the Wydawnictwo in Elasticsearch
        verify(mockWydawnictwoSearchRepository, times(1)).save(testWydawnictwo);
    }

    @Test
    @Transactional
    public void updateNonExistingWydawnictwo() throws Exception {
        int databaseSizeBeforeUpdate = wydawnictwoRepository.findAll().size();

        // Create the Wydawnictwo
        WydawnictwoDTO wydawnictwoDTO = wydawnictwoMapper.toDto(wydawnictwo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWydawnictwoMockMvc.perform(put("/api/wydawnictwos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wydawnictwoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wydawnictwo in the database
        List<Wydawnictwo> wydawnictwoList = wydawnictwoRepository.findAll();
        assertThat(wydawnictwoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Wydawnictwo in Elasticsearch
        verify(mockWydawnictwoSearchRepository, times(0)).save(wydawnictwo);
    }

    @Test
    @Transactional
    public void deleteWydawnictwo() throws Exception {
        // Initialize the database
        wydawnictwoRepository.saveAndFlush(wydawnictwo);

        int databaseSizeBeforeDelete = wydawnictwoRepository.findAll().size();

        // Get the wydawnictwo
        restWydawnictwoMockMvc.perform(delete("/api/wydawnictwos/{id}", wydawnictwo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Wydawnictwo> wydawnictwoList = wydawnictwoRepository.findAll();
        assertThat(wydawnictwoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Wydawnictwo in Elasticsearch
        verify(mockWydawnictwoSearchRepository, times(1)).deleteById(wydawnictwo.getId());
    }

    @Test
    @Transactional
    public void searchWydawnictwo() throws Exception {
        // Initialize the database
        wydawnictwoRepository.saveAndFlush(wydawnictwo);
        when(mockWydawnictwoSearchRepository.search(queryStringQuery("id:" + wydawnictwo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(wydawnictwo), PageRequest.of(0, 1), 1));
        // Search the wydawnictwo
        restWydawnictwoMockMvc.perform(get("/api/_search/wydawnictwos?query=id:" + wydawnictwo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wydawnictwo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nazwa").value(hasItem(DEFAULT_NAZWA)))
            .andExpect(jsonPath("$.[*].adres").value(hasItem(DEFAULT_ADRES)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wydawnictwo.class);
        Wydawnictwo wydawnictwo1 = new Wydawnictwo();
        wydawnictwo1.setId(1L);
        Wydawnictwo wydawnictwo2 = new Wydawnictwo();
        wydawnictwo2.setId(wydawnictwo1.getId());
        assertThat(wydawnictwo1).isEqualTo(wydawnictwo2);
        wydawnictwo2.setId(2L);
        assertThat(wydawnictwo1).isNotEqualTo(wydawnictwo2);
        wydawnictwo1.setId(null);
        assertThat(wydawnictwo1).isNotEqualTo(wydawnictwo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WydawnictwoDTO.class);
        WydawnictwoDTO wydawnictwoDTO1 = new WydawnictwoDTO();
        wydawnictwoDTO1.setId(1L);
        WydawnictwoDTO wydawnictwoDTO2 = new WydawnictwoDTO();
        assertThat(wydawnictwoDTO1).isNotEqualTo(wydawnictwoDTO2);
        wydawnictwoDTO2.setId(wydawnictwoDTO1.getId());
        assertThat(wydawnictwoDTO1).isEqualTo(wydawnictwoDTO2);
        wydawnictwoDTO2.setId(2L);
        assertThat(wydawnictwoDTO1).isNotEqualTo(wydawnictwoDTO2);
        wydawnictwoDTO1.setId(null);
        assertThat(wydawnictwoDTO1).isNotEqualTo(wydawnictwoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wydawnictwoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wydawnictwoMapper.fromId(null)).isNull();
    }
}
