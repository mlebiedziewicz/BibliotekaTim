package pl.wat.magda.biblioteka.web.rest;

import pl.wat.magda.biblioteka.BibliotekaTimApp;

import pl.wat.magda.biblioteka.domain.Gatunek;
import pl.wat.magda.biblioteka.repository.GatunekRepository;
import pl.wat.magda.biblioteka.repository.search.GatunekSearchRepository;
import pl.wat.magda.biblioteka.service.GatunekService;
import pl.wat.magda.biblioteka.service.dto.GatunekDTO;
import pl.wat.magda.biblioteka.service.mapper.GatunekMapper;
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
 * Test class for the GatunekResource REST controller.
 *
 * @see GatunekResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BibliotekaTimApp.class)
public class GatunekResourceIntTest {

    private static final String DEFAULT_NAZWA = "AAAAAAAAAA";
    private static final String UPDATED_NAZWA = "BBBBBBBBBB";

    @Autowired
    private GatunekRepository gatunekRepository;

    @Autowired
    private GatunekMapper gatunekMapper;

    @Autowired
    private GatunekService gatunekService;

    /**
     * This repository is mocked in the pl.wat.magda.biblioteka.repository.search test package.
     *
     * @see pl.wat.magda.biblioteka.repository.search.GatunekSearchRepositoryMockConfiguration
     */
    @Autowired
    private GatunekSearchRepository mockGatunekSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGatunekMockMvc;

    private Gatunek gatunek;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GatunekResource gatunekResource = new GatunekResource(gatunekService);
        this.restGatunekMockMvc = MockMvcBuilders.standaloneSetup(gatunekResource)
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
    public static Gatunek createEntity(EntityManager em) {
        Gatunek gatunek = new Gatunek()
            .nazwa(DEFAULT_NAZWA);
        return gatunek;
    }

    @Before
    public void initTest() {
        gatunek = createEntity(em);
    }

    @Test
    @Transactional
    public void createGatunek() throws Exception {
        int databaseSizeBeforeCreate = gatunekRepository.findAll().size();

        // Create the Gatunek
        GatunekDTO gatunekDTO = gatunekMapper.toDto(gatunek);
        restGatunekMockMvc.perform(post("/api/gatuneks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gatunekDTO)))
            .andExpect(status().isCreated());

        // Validate the Gatunek in the database
        List<Gatunek> gatunekList = gatunekRepository.findAll();
        assertThat(gatunekList).hasSize(databaseSizeBeforeCreate + 1);
        Gatunek testGatunek = gatunekList.get(gatunekList.size() - 1);
        assertThat(testGatunek.getNazwa()).isEqualTo(DEFAULT_NAZWA);

        // Validate the Gatunek in Elasticsearch
        verify(mockGatunekSearchRepository, times(1)).save(testGatunek);
    }

    @Test
    @Transactional
    public void createGatunekWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gatunekRepository.findAll().size();

        // Create the Gatunek with an existing ID
        gatunek.setId(1L);
        GatunekDTO gatunekDTO = gatunekMapper.toDto(gatunek);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGatunekMockMvc.perform(post("/api/gatuneks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gatunekDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gatunek in the database
        List<Gatunek> gatunekList = gatunekRepository.findAll();
        assertThat(gatunekList).hasSize(databaseSizeBeforeCreate);

        // Validate the Gatunek in Elasticsearch
        verify(mockGatunekSearchRepository, times(0)).save(gatunek);
    }

    @Test
    @Transactional
    public void checkNazwaIsRequired() throws Exception {
        int databaseSizeBeforeTest = gatunekRepository.findAll().size();
        // set the field null
        gatunek.setNazwa(null);

        // Create the Gatunek, which fails.
        GatunekDTO gatunekDTO = gatunekMapper.toDto(gatunek);

        restGatunekMockMvc.perform(post("/api/gatuneks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gatunekDTO)))
            .andExpect(status().isBadRequest());

        List<Gatunek> gatunekList = gatunekRepository.findAll();
        assertThat(gatunekList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGatuneks() throws Exception {
        // Initialize the database
        gatunekRepository.saveAndFlush(gatunek);

        // Get all the gatunekList
        restGatunekMockMvc.perform(get("/api/gatuneks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gatunek.getId().intValue())))
            .andExpect(jsonPath("$.[*].nazwa").value(hasItem(DEFAULT_NAZWA.toString())));
    }
    
    @Test
    @Transactional
    public void getGatunek() throws Exception {
        // Initialize the database
        gatunekRepository.saveAndFlush(gatunek);

        // Get the gatunek
        restGatunekMockMvc.perform(get("/api/gatuneks/{id}", gatunek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gatunek.getId().intValue()))
            .andExpect(jsonPath("$.nazwa").value(DEFAULT_NAZWA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGatunek() throws Exception {
        // Get the gatunek
        restGatunekMockMvc.perform(get("/api/gatuneks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGatunek() throws Exception {
        // Initialize the database
        gatunekRepository.saveAndFlush(gatunek);

        int databaseSizeBeforeUpdate = gatunekRepository.findAll().size();

        // Update the gatunek
        Gatunek updatedGatunek = gatunekRepository.findById(gatunek.getId()).get();
        // Disconnect from session so that the updates on updatedGatunek are not directly saved in db
        em.detach(updatedGatunek);
        updatedGatunek
            .nazwa(UPDATED_NAZWA);
        GatunekDTO gatunekDTO = gatunekMapper.toDto(updatedGatunek);

        restGatunekMockMvc.perform(put("/api/gatuneks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gatunekDTO)))
            .andExpect(status().isOk());

        // Validate the Gatunek in the database
        List<Gatunek> gatunekList = gatunekRepository.findAll();
        assertThat(gatunekList).hasSize(databaseSizeBeforeUpdate);
        Gatunek testGatunek = gatunekList.get(gatunekList.size() - 1);
        assertThat(testGatunek.getNazwa()).isEqualTo(UPDATED_NAZWA);

        // Validate the Gatunek in Elasticsearch
        verify(mockGatunekSearchRepository, times(1)).save(testGatunek);
    }

    @Test
    @Transactional
    public void updateNonExistingGatunek() throws Exception {
        int databaseSizeBeforeUpdate = gatunekRepository.findAll().size();

        // Create the Gatunek
        GatunekDTO gatunekDTO = gatunekMapper.toDto(gatunek);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGatunekMockMvc.perform(put("/api/gatuneks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gatunekDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gatunek in the database
        List<Gatunek> gatunekList = gatunekRepository.findAll();
        assertThat(gatunekList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Gatunek in Elasticsearch
        verify(mockGatunekSearchRepository, times(0)).save(gatunek);
    }

    @Test
    @Transactional
    public void deleteGatunek() throws Exception {
        // Initialize the database
        gatunekRepository.saveAndFlush(gatunek);

        int databaseSizeBeforeDelete = gatunekRepository.findAll().size();

        // Get the gatunek
        restGatunekMockMvc.perform(delete("/api/gatuneks/{id}", gatunek.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gatunek> gatunekList = gatunekRepository.findAll();
        assertThat(gatunekList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Gatunek in Elasticsearch
        verify(mockGatunekSearchRepository, times(1)).deleteById(gatunek.getId());
    }

    @Test
    @Transactional
    public void searchGatunek() throws Exception {
        // Initialize the database
        gatunekRepository.saveAndFlush(gatunek);
        when(mockGatunekSearchRepository.search(queryStringQuery("id:" + gatunek.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(gatunek), PageRequest.of(0, 1), 1));
        // Search the gatunek
        restGatunekMockMvc.perform(get("/api/_search/gatuneks?query=id:" + gatunek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gatunek.getId().intValue())))
            .andExpect(jsonPath("$.[*].nazwa").value(hasItem(DEFAULT_NAZWA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gatunek.class);
        Gatunek gatunek1 = new Gatunek();
        gatunek1.setId(1L);
        Gatunek gatunek2 = new Gatunek();
        gatunek2.setId(gatunek1.getId());
        assertThat(gatunek1).isEqualTo(gatunek2);
        gatunek2.setId(2L);
        assertThat(gatunek1).isNotEqualTo(gatunek2);
        gatunek1.setId(null);
        assertThat(gatunek1).isNotEqualTo(gatunek2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GatunekDTO.class);
        GatunekDTO gatunekDTO1 = new GatunekDTO();
        gatunekDTO1.setId(1L);
        GatunekDTO gatunekDTO2 = new GatunekDTO();
        assertThat(gatunekDTO1).isNotEqualTo(gatunekDTO2);
        gatunekDTO2.setId(gatunekDTO1.getId());
        assertThat(gatunekDTO1).isEqualTo(gatunekDTO2);
        gatunekDTO2.setId(2L);
        assertThat(gatunekDTO1).isNotEqualTo(gatunekDTO2);
        gatunekDTO1.setId(null);
        assertThat(gatunekDTO1).isNotEqualTo(gatunekDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gatunekMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gatunekMapper.fromId(null)).isNull();
    }
}
