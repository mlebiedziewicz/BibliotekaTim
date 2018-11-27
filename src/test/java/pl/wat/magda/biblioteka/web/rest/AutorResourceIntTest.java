package pl.wat.magda.biblioteka.web.rest;

import pl.wat.magda.biblioteka.BibliotekaTimApp;

import pl.wat.magda.biblioteka.domain.Autor;
import pl.wat.magda.biblioteka.repository.AutorRepository;
import pl.wat.magda.biblioteka.repository.search.AutorSearchRepository;
import pl.wat.magda.biblioteka.service.AutorService;
import pl.wat.magda.biblioteka.service.dto.AutorDTO;
import pl.wat.magda.biblioteka.service.mapper.AutorMapper;
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
 * Test class for the AutorResource REST controller.
 *
 * @see AutorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BibliotekaTimApp.class)
public class AutorResourceIntTest {

    private static final String DEFAULT_IMIENAZWISKO = "AAAAAAAAAA";
    private static final String UPDATED_IMIENAZWISKO = "BBBBBBBBBB";

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private AutorMapper autorMapper;

    @Autowired
    private AutorService autorService;

    /**
     * This repository is mocked in the pl.wat.magda.biblioteka.repository.search test package.
     *
     * @see pl.wat.magda.biblioteka.repository.search.AutorSearchRepositoryMockConfiguration
     */
    @Autowired
    private AutorSearchRepository mockAutorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAutorMockMvc;

    private Autor autor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutorResource autorResource = new AutorResource(autorService);
        this.restAutorMockMvc = MockMvcBuilders.standaloneSetup(autorResource)
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
    public static Autor createEntity(EntityManager em) {
        Autor autor = new Autor()
            .imienazwisko(DEFAULT_IMIENAZWISKO);
        return autor;
    }

    @Before
    public void initTest() {
        autor = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutor() throws Exception {
        int databaseSizeBeforeCreate = autorRepository.findAll().size();

        // Create the Autor
        AutorDTO autorDTO = autorMapper.toDto(autor);
        restAutorMockMvc.perform(post("/api/autors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autorDTO)))
            .andExpect(status().isCreated());

        // Validate the Autor in the database
        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeCreate + 1);
        Autor testAutor = autorList.get(autorList.size() - 1);
        assertThat(testAutor.getImienazwisko()).isEqualTo(DEFAULT_IMIENAZWISKO);

        // Validate the Autor in Elasticsearch
        verify(mockAutorSearchRepository, times(1)).save(testAutor);
    }

    @Test
    @Transactional
    public void createAutorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autorRepository.findAll().size();

        // Create the Autor with an existing ID
        autor.setId(1L);
        AutorDTO autorDTO = autorMapper.toDto(autor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutorMockMvc.perform(post("/api/autors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Autor in the database
        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Autor in Elasticsearch
        verify(mockAutorSearchRepository, times(0)).save(autor);
    }

    @Test
    @Transactional
    public void checkImienazwiskoIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorRepository.findAll().size();
        // set the field null
        autor.setImienazwisko(null);

        // Create the Autor, which fails.
        AutorDTO autorDTO = autorMapper.toDto(autor);

        restAutorMockMvc.perform(post("/api/autors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autorDTO)))
            .andExpect(status().isBadRequest());

        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutors() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get all the autorList
        restAutorMockMvc.perform(get("/api/autors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autor.getId().intValue())))
            .andExpect(jsonPath("$.[*].imienazwisko").value(hasItem(DEFAULT_IMIENAZWISKO.toString())));
    }
    
    @Test
    @Transactional
    public void getAutor() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get the autor
        restAutorMockMvc.perform(get("/api/autors/{id}", autor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(autor.getId().intValue()))
            .andExpect(jsonPath("$.imienazwisko").value(DEFAULT_IMIENAZWISKO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAutor() throws Exception {
        // Get the autor
        restAutorMockMvc.perform(get("/api/autors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutor() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        int databaseSizeBeforeUpdate = autorRepository.findAll().size();

        // Update the autor
        Autor updatedAutor = autorRepository.findById(autor.getId()).get();
        // Disconnect from session so that the updates on updatedAutor are not directly saved in db
        em.detach(updatedAutor);
        updatedAutor
            .imienazwisko(UPDATED_IMIENAZWISKO);
        AutorDTO autorDTO = autorMapper.toDto(updatedAutor);

        restAutorMockMvc.perform(put("/api/autors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autorDTO)))
            .andExpect(status().isOk());

        // Validate the Autor in the database
        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeUpdate);
        Autor testAutor = autorList.get(autorList.size() - 1);
        assertThat(testAutor.getImienazwisko()).isEqualTo(UPDATED_IMIENAZWISKO);

        // Validate the Autor in Elasticsearch
        verify(mockAutorSearchRepository, times(1)).save(testAutor);
    }

    @Test
    @Transactional
    public void updateNonExistingAutor() throws Exception {
        int databaseSizeBeforeUpdate = autorRepository.findAll().size();

        // Create the Autor
        AutorDTO autorDTO = autorMapper.toDto(autor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutorMockMvc.perform(put("/api/autors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Autor in the database
        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Autor in Elasticsearch
        verify(mockAutorSearchRepository, times(0)).save(autor);
    }

    @Test
    @Transactional
    public void deleteAutor() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        int databaseSizeBeforeDelete = autorRepository.findAll().size();

        // Get the autor
        restAutorMockMvc.perform(delete("/api/autors/{id}", autor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Autor in Elasticsearch
        verify(mockAutorSearchRepository, times(1)).deleteById(autor.getId());
    }

    @Test
    @Transactional
    public void searchAutor() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);
        when(mockAutorSearchRepository.search(queryStringQuery("id:" + autor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(autor), PageRequest.of(0, 1), 1));
        // Search the autor
        restAutorMockMvc.perform(get("/api/_search/autors?query=id:" + autor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autor.getId().intValue())))
            .andExpect(jsonPath("$.[*].imienazwisko").value(hasItem(DEFAULT_IMIENAZWISKO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Autor.class);
        Autor autor1 = new Autor();
        autor1.setId(1L);
        Autor autor2 = new Autor();
        autor2.setId(autor1.getId());
        assertThat(autor1).isEqualTo(autor2);
        autor2.setId(2L);
        assertThat(autor1).isNotEqualTo(autor2);
        autor1.setId(null);
        assertThat(autor1).isNotEqualTo(autor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutorDTO.class);
        AutorDTO autorDTO1 = new AutorDTO();
        autorDTO1.setId(1L);
        AutorDTO autorDTO2 = new AutorDTO();
        assertThat(autorDTO1).isNotEqualTo(autorDTO2);
        autorDTO2.setId(autorDTO1.getId());
        assertThat(autorDTO1).isEqualTo(autorDTO2);
        autorDTO2.setId(2L);
        assertThat(autorDTO1).isNotEqualTo(autorDTO2);
        autorDTO1.setId(null);
        assertThat(autorDTO1).isNotEqualTo(autorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(autorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(autorMapper.fromId(null)).isNull();
    }
}
