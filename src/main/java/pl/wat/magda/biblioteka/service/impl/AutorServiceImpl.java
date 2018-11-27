package pl.wat.magda.biblioteka.service.impl;

import pl.wat.magda.biblioteka.service.AutorService;
import pl.wat.magda.biblioteka.domain.Autor;
import pl.wat.magda.biblioteka.repository.AutorRepository;
import pl.wat.magda.biblioteka.repository.search.AutorSearchRepository;
import pl.wat.magda.biblioteka.service.dto.AutorDTO;
import pl.wat.magda.biblioteka.service.mapper.AutorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Autor.
 */
@Service
@Transactional
public class AutorServiceImpl implements AutorService {

    private final Logger log = LoggerFactory.getLogger(AutorServiceImpl.class);

    private final AutorRepository autorRepository;

    private final AutorMapper autorMapper;

    private final AutorSearchRepository autorSearchRepository;

    public AutorServiceImpl(AutorRepository autorRepository, AutorMapper autorMapper, AutorSearchRepository autorSearchRepository) {
        this.autorRepository = autorRepository;
        this.autorMapper = autorMapper;
        this.autorSearchRepository = autorSearchRepository;
    }

    /**
     * Save a autor.
     *
     * @param autorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AutorDTO save(AutorDTO autorDTO) {
        log.debug("Request to save Autor : {}", autorDTO);

        Autor autor = autorMapper.toEntity(autorDTO);
        autor = autorRepository.save(autor);
        AutorDTO result = autorMapper.toDto(autor);
        autorSearchRepository.save(autor);
        return result;
    }

    /**
     * Get all the autors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AutorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Autors");
        return autorRepository.findAll(pageable)
            .map(autorMapper::toDto);
    }


    /**
     * Get one autor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AutorDTO> findOne(Long id) {
        log.debug("Request to get Autor : {}", id);
        return autorRepository.findById(id)
            .map(autorMapper::toDto);
    }

    /**
     * Delete the autor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Autor : {}", id);
        autorRepository.deleteById(id);
        autorSearchRepository.deleteById(id);
    }

    /**
     * Search for the autor corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AutorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Autors for query {}", query);
        return autorSearchRepository.search(queryStringQuery(query), pageable)
            .map(autorMapper::toDto);
    }
}
