package pl.wat.magda.biblioteka.service.impl;

import pl.wat.magda.biblioteka.service.WydawnictwoService;
import pl.wat.magda.biblioteka.domain.Wydawnictwo;
import pl.wat.magda.biblioteka.repository.WydawnictwoRepository;
import pl.wat.magda.biblioteka.repository.search.WydawnictwoSearchRepository;
import pl.wat.magda.biblioteka.service.dto.WydawnictwoDTO;
import pl.wat.magda.biblioteka.service.mapper.WydawnictwoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Wydawnictwo.
 */
@Service
@Transactional
public class WydawnictwoServiceImpl implements WydawnictwoService {

    private final Logger log = LoggerFactory.getLogger(WydawnictwoServiceImpl.class);

    private final WydawnictwoRepository wydawnictwoRepository;

    private final WydawnictwoMapper wydawnictwoMapper;

    private final WydawnictwoSearchRepository wydawnictwoSearchRepository;

    public WydawnictwoServiceImpl(WydawnictwoRepository wydawnictwoRepository, WydawnictwoMapper wydawnictwoMapper, WydawnictwoSearchRepository wydawnictwoSearchRepository) {
        this.wydawnictwoRepository = wydawnictwoRepository;
        this.wydawnictwoMapper = wydawnictwoMapper;
        this.wydawnictwoSearchRepository = wydawnictwoSearchRepository;
    }

    /**
     * Save a wydawnictwo.
     *
     * @param wydawnictwoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WydawnictwoDTO save(WydawnictwoDTO wydawnictwoDTO) {
        log.debug("Request to save Wydawnictwo : {}", wydawnictwoDTO);

        Wydawnictwo wydawnictwo = wydawnictwoMapper.toEntity(wydawnictwoDTO);
        wydawnictwo = wydawnictwoRepository.save(wydawnictwo);
        WydawnictwoDTO result = wydawnictwoMapper.toDto(wydawnictwo);
        wydawnictwoSearchRepository.save(wydawnictwo);
        return result;
    }

    /**
     * Get all the wydawnictwos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WydawnictwoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Wydawnictwos");
        return wydawnictwoRepository.findAll(pageable)
            .map(wydawnictwoMapper::toDto);
    }


    /**
     * Get one wydawnictwo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WydawnictwoDTO> findOne(Long id) {
        log.debug("Request to get Wydawnictwo : {}", id);
        return wydawnictwoRepository.findById(id)
            .map(wydawnictwoMapper::toDto);
    }

    /**
     * Delete the wydawnictwo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Wydawnictwo : {}", id);
        wydawnictwoRepository.deleteById(id);
        wydawnictwoSearchRepository.deleteById(id);
    }

    /**
     * Search for the wydawnictwo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WydawnictwoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Wydawnictwos for query {}", query);
        return wydawnictwoSearchRepository.search(queryStringQuery(query), pageable)
            .map(wydawnictwoMapper::toDto);
    }
}
