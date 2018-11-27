package pl.wat.magda.biblioteka.service.impl;

import pl.wat.magda.biblioteka.service.KsiazkaService;
import pl.wat.magda.biblioteka.domain.Ksiazka;
import pl.wat.magda.biblioteka.repository.KsiazkaRepository;
import pl.wat.magda.biblioteka.repository.search.KsiazkaSearchRepository;
import pl.wat.magda.biblioteka.service.dto.KsiazkaDTO;
import pl.wat.magda.biblioteka.service.mapper.KsiazkaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Ksiazka.
 */
@Service
@Transactional
public class KsiazkaServiceImpl implements KsiazkaService {

    private final Logger log = LoggerFactory.getLogger(KsiazkaServiceImpl.class);

    private final KsiazkaRepository ksiazkaRepository;

    private final KsiazkaMapper ksiazkaMapper;

    private final KsiazkaSearchRepository ksiazkaSearchRepository;

    public KsiazkaServiceImpl(KsiazkaRepository ksiazkaRepository, KsiazkaMapper ksiazkaMapper, KsiazkaSearchRepository ksiazkaSearchRepository) {
        this.ksiazkaRepository = ksiazkaRepository;
        this.ksiazkaMapper = ksiazkaMapper;
        this.ksiazkaSearchRepository = ksiazkaSearchRepository;
    }

    /**
     * Save a ksiazka.
     *
     * @param ksiazkaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KsiazkaDTO save(KsiazkaDTO ksiazkaDTO) {
        log.debug("Request to save Ksiazka : {}", ksiazkaDTO);

        Ksiazka ksiazka = ksiazkaMapper.toEntity(ksiazkaDTO);
        ksiazka = ksiazkaRepository.save(ksiazka);
        KsiazkaDTO result = ksiazkaMapper.toDto(ksiazka);
        ksiazkaSearchRepository.save(ksiazka);
        return result;
    }

    /**
     * Get all the ksiazkas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KsiazkaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ksiazkas");
        return ksiazkaRepository.findAll(pageable)
            .map(ksiazkaMapper::toDto);
    }


    /**
     * Get one ksiazka by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<KsiazkaDTO> findOne(Long id) {
        log.debug("Request to get Ksiazka : {}", id);
        return ksiazkaRepository.findById(id)
            .map(ksiazkaMapper::toDto);
    }

    /**
     * Delete the ksiazka by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ksiazka : {}", id);
        ksiazkaRepository.deleteById(id);
        ksiazkaSearchRepository.deleteById(id);
    }

    /**
     * Search for the ksiazka corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<KsiazkaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Ksiazkas for query {}", query);
        return ksiazkaSearchRepository.search(queryStringQuery(query), pageable)
            .map(ksiazkaMapper::toDto);
    }
}
