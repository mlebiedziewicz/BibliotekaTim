package pl.wat.magda.biblioteka.web.rest;

import pl.wat.magda.biblioteka.BibliotekaTimApp;

import pl.wat.magda.biblioteka.domain.Wypozyczone;
import pl.wat.magda.biblioteka.domain.User;
import pl.wat.magda.biblioteka.domain.Ksiazka;
import pl.wat.magda.biblioteka.repository.WypozyczoneRepository;
import pl.wat.magda.biblioteka.repository.search.WypozyczoneSearchRepository;
import pl.wat.magda.biblioteka.service.WypozyczoneService;
import pl.wat.magda.biblioteka.service.dto.WypozyczoneDTO;
import pl.wat.magda.biblioteka.service.mapper.WypozyczoneMapper;
import pl.wat.magda.biblioteka.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
 * Test class for the WypozyczoneResource REST controller.
 *
 * @see WypozyczoneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BibliotekaTimApp.class)
public class WypozyczoneResourceIntTest {

    private static final LocalDate DEFAULT_DATAWYPOZYCZENIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATAWYPOZYCZENIA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATAODDANIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATAODDANIA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private WypozyczoneRepository wypozyczoneRepository;

    @Mock
    private WypozyczoneRepository wypozyczoneRepositoryMock;

    @Autowired
    private WypozyczoneMapper wypozyczoneMapper;

    @Mock
    private WypozyczoneService wypozyczoneServiceMock;

    @Autowired
    private WypozyczoneService wypozyczoneService;

    /**
     * This repository is mocked in the pl.wat.magda.biblioteka.repository.search test package.
     *
     * @see pl.wat.magda.biblioteka.repository.search.WypozyczoneSearchRepositoryMockConfiguration
     */
    @Autowired
    private WypozyczoneSearchRepository mockWypozyczoneSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWypozyczoneMockMvc;

    private Wypozyczone wypozyczone;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WypozyczoneResource wypozyczoneResource = new WypozyczoneResource(wypozyczoneService);
        this.restWypozyczoneMockMvc = MockMvcBuilders.standaloneSetup(wypozyczoneResource)
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
    public static Wypozyczone createEntity(EntityManager em) {
        Wypozyczone wypozyczone = new Wypozyczone()
            .datawypozyczenia(DEFAULT_DATAWYPOZYCZENIA)
            .dataoddania(DEFAULT_DATAODDANIA);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        wypozyczone.getUzytkowniks().add(user);
        // Add required entity
        Ksiazka ksiazka = KsiazkaResourceIntTest.createEntity(em);
        em.persist(ksiazka);
        em.flush();
        wypozyczone.getKsiazkas().add(ksiazka);
        return wypozyczone;
    }

    @Before
    public void initTest() {
        wypozyczone = createEntity(em);
    }

    @Test
    @Transactional
    public void createWypozyczone() throws Exception {
        int databaseSizeBeforeCreate = wypozyczoneRepository.findAll().size();

        // Create the Wypozyczone
        WypozyczoneDTO wypozyczoneDTO = wypozyczoneMapper.toDto(wypozyczone);
        restWypozyczoneMockMvc.perform(post("/api/wypozyczones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wypozyczoneDTO)))
            .andExpect(status().isCreated());

        // Validate the Wypozyczone in the database
        List<Wypozyczone> wypozyczoneList = wypozyczoneRepository.findAll();
        assertThat(wypozyczoneList).hasSize(databaseSizeBeforeCreate + 1);
        Wypozyczone testWypozyczone = wypozyczoneList.get(wypozyczoneList.size() - 1);
        assertThat(testWypozyczone.getDatawypozyczenia()).isEqualTo(DEFAULT_DATAWYPOZYCZENIA);
        assertThat(testWypozyczone.getDataoddania()).isEqualTo(DEFAULT_DATAODDANIA);

        // Validate the Wypozyczone in Elasticsearch
        verify(mockWypozyczoneSearchRepository, times(1)).save(testWypozyczone);
    }

    @Test
    @Transactional
    public void createWypozyczoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wypozyczoneRepository.findAll().size();

        // Create the Wypozyczone with an existing ID
        wypozyczone.setId(1L);
        WypozyczoneDTO wypozyczoneDTO = wypozyczoneMapper.toDto(wypozyczone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWypozyczoneMockMvc.perform(post("/api/wypozyczones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wypozyczoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wypozyczone in the database
        List<Wypozyczone> wypozyczoneList = wypozyczoneRepository.findAll();
        assertThat(wypozyczoneList).hasSize(databaseSizeBeforeCreate);

        // Validate the Wypozyczone in Elasticsearch
        verify(mockWypozyczoneSearchRepository, times(0)).save(wypozyczone);
    }

    @Test
    @Transactional
    public void checkDatawypozyczeniaIsRequired() throws Exception {
        int databaseSizeBeforeTest = wypozyczoneRepository.findAll().size();
        // set the field null
        wypozyczone.setDatawypozyczenia(null);

        // Create the Wypozyczone, which fails.
        WypozyczoneDTO wypozyczoneDTO = wypozyczoneMapper.toDto(wypozyczone);

        restWypozyczoneMockMvc.perform(post("/api/wypozyczones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wypozyczoneDTO)))
            .andExpect(status().isBadRequest());

        List<Wypozyczone> wypozyczoneList = wypozyczoneRepository.findAll();
        assertThat(wypozyczoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWypozyczones() throws Exception {
        // Initialize the database
        wypozyczoneRepository.saveAndFlush(wypozyczone);

        // Get all the wypozyczoneList
        restWypozyczoneMockMvc.perform(get("/api/wypozyczones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wypozyczone.getId().intValue())))
            .andExpect(jsonPath("$.[*].datawypozyczenia").value(hasItem(DEFAULT_DATAWYPOZYCZENIA.toString())))
            .andExpect(jsonPath("$.[*].dataoddania").value(hasItem(DEFAULT_DATAODDANIA.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllWypozyczonesWithEagerRelationshipsIsEnabled() throws Exception {
        WypozyczoneResource wypozyczoneResource = new WypozyczoneResource(wypozyczoneServiceMock);
        when(wypozyczoneServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restWypozyczoneMockMvc = MockMvcBuilders.standaloneSetup(wypozyczoneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restWypozyczoneMockMvc.perform(get("/api/wypozyczones?eagerload=true"))
        .andExpect(status().isOk());

        verify(wypozyczoneServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllWypozyczonesWithEagerRelationshipsIsNotEnabled() throws Exception {
        WypozyczoneResource wypozyczoneResource = new WypozyczoneResource(wypozyczoneServiceMock);
            when(wypozyczoneServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restWypozyczoneMockMvc = MockMvcBuilders.standaloneSetup(wypozyczoneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restWypozyczoneMockMvc.perform(get("/api/wypozyczones?eagerload=true"))
        .andExpect(status().isOk());

            verify(wypozyczoneServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getWypozyczone() throws Exception {
        // Initialize the database
        wypozyczoneRepository.saveAndFlush(wypozyczone);

        // Get the wypozyczone
        restWypozyczoneMockMvc.perform(get("/api/wypozyczones/{id}", wypozyczone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wypozyczone.getId().intValue()))
            .andExpect(jsonPath("$.datawypozyczenia").value(DEFAULT_DATAWYPOZYCZENIA.toString()))
            .andExpect(jsonPath("$.dataoddania").value(DEFAULT_DATAODDANIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWypozyczone() throws Exception {
        // Get the wypozyczone
        restWypozyczoneMockMvc.perform(get("/api/wypozyczones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWypozyczone() throws Exception {
        // Initialize the database
        wypozyczoneRepository.saveAndFlush(wypozyczone);

        int databaseSizeBeforeUpdate = wypozyczoneRepository.findAll().size();

        // Update the wypozyczone
        Wypozyczone updatedWypozyczone = wypozyczoneRepository.findById(wypozyczone.getId()).get();
        // Disconnect from session so that the updates on updatedWypozyczone are not directly saved in db
        em.detach(updatedWypozyczone);
        updatedWypozyczone
            .datawypozyczenia(UPDATED_DATAWYPOZYCZENIA)
            .dataoddania(UPDATED_DATAODDANIA);
        WypozyczoneDTO wypozyczoneDTO = wypozyczoneMapper.toDto(updatedWypozyczone);

        restWypozyczoneMockMvc.perform(put("/api/wypozyczones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wypozyczoneDTO)))
            .andExpect(status().isOk());

        // Validate the Wypozyczone in the database
        List<Wypozyczone> wypozyczoneList = wypozyczoneRepository.findAll();
        assertThat(wypozyczoneList).hasSize(databaseSizeBeforeUpdate);
        Wypozyczone testWypozyczone = wypozyczoneList.get(wypozyczoneList.size() - 1);
        assertThat(testWypozyczone.getDatawypozyczenia()).isEqualTo(UPDATED_DATAWYPOZYCZENIA);
        assertThat(testWypozyczone.getDataoddania()).isEqualTo(UPDATED_DATAODDANIA);

        // Validate the Wypozyczone in Elasticsearch
        verify(mockWypozyczoneSearchRepository, times(1)).save(testWypozyczone);
    }

    @Test
    @Transactional
    public void updateNonExistingWypozyczone() throws Exception {
        int databaseSizeBeforeUpdate = wypozyczoneRepository.findAll().size();

        // Create the Wypozyczone
        WypozyczoneDTO wypozyczoneDTO = wypozyczoneMapper.toDto(wypozyczone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWypozyczoneMockMvc.perform(put("/api/wypozyczones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wypozyczoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wypozyczone in the database
        List<Wypozyczone> wypozyczoneList = wypozyczoneRepository.findAll();
        assertThat(wypozyczoneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Wypozyczone in Elasticsearch
        verify(mockWypozyczoneSearchRepository, times(0)).save(wypozyczone);
    }

    @Test
    @Transactional
    public void deleteWypozyczone() throws Exception {
        // Initialize the database
        wypozyczoneRepository.saveAndFlush(wypozyczone);

        int databaseSizeBeforeDelete = wypozyczoneRepository.findAll().size();

        // Get the wypozyczone
        restWypozyczoneMockMvc.perform(delete("/api/wypozyczones/{id}", wypozyczone.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Wypozyczone> wypozyczoneList = wypozyczoneRepository.findAll();
        assertThat(wypozyczoneList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Wypozyczone in Elasticsearch
        verify(mockWypozyczoneSearchRepository, times(1)).deleteById(wypozyczone.getId());
    }

    @Test
    @Transactional
    public void searchWypozyczone() throws Exception {
        // Initialize the database
        wypozyczoneRepository.saveAndFlush(wypozyczone);
        when(mockWypozyczoneSearchRepository.search(queryStringQuery("id:" + wypozyczone.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(wypozyczone), PageRequest.of(0, 1), 1));
        // Search the wypozyczone
        restWypozyczoneMockMvc.perform(get("/api/_search/wypozyczones?query=id:" + wypozyczone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wypozyczone.getId().intValue())))
            .andExpect(jsonPath("$.[*].datawypozyczenia").value(hasItem(DEFAULT_DATAWYPOZYCZENIA.toString())))
            .andExpect(jsonPath("$.[*].dataoddania").value(hasItem(DEFAULT_DATAODDANIA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wypozyczone.class);
        Wypozyczone wypozyczone1 = new Wypozyczone();
        wypozyczone1.setId(1L);
        Wypozyczone wypozyczone2 = new Wypozyczone();
        wypozyczone2.setId(wypozyczone1.getId());
        assertThat(wypozyczone1).isEqualTo(wypozyczone2);
        wypozyczone2.setId(2L);
        assertThat(wypozyczone1).isNotEqualTo(wypozyczone2);
        wypozyczone1.setId(null);
        assertThat(wypozyczone1).isNotEqualTo(wypozyczone2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WypozyczoneDTO.class);
        WypozyczoneDTO wypozyczoneDTO1 = new WypozyczoneDTO();
        wypozyczoneDTO1.setId(1L);
        WypozyczoneDTO wypozyczoneDTO2 = new WypozyczoneDTO();
        assertThat(wypozyczoneDTO1).isNotEqualTo(wypozyczoneDTO2);
        wypozyczoneDTO2.setId(wypozyczoneDTO1.getId());
        assertThat(wypozyczoneDTO1).isEqualTo(wypozyczoneDTO2);
        wypozyczoneDTO2.setId(2L);
        assertThat(wypozyczoneDTO1).isNotEqualTo(wypozyczoneDTO2);
        wypozyczoneDTO1.setId(null);
        assertThat(wypozyczoneDTO1).isNotEqualTo(wypozyczoneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wypozyczoneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wypozyczoneMapper.fromId(null)).isNull();
    }
}
