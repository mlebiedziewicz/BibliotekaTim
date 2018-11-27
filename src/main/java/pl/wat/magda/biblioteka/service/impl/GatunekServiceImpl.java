package pl.wat.magda.biblioteka.service.impl;

import pl.wat.magda.biblioteka.service.GatunekService;
import pl.wat.magda.biblioteka.domain.Gatunek;
import pl.wat.magda.biblioteka.repository.GatunekRepository;
import pl.wat.magda.biblioteka.repository.search.GatunekSearchRepository;
import pl.wat.magda.biblioteka.service.dto.GatunekDTO;
import pl.wat.magda.biblioteka.service.mapper.GatunekMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Gatunek.
 */
@Service
@Transactional
public class GatunekServiceImpl implements GatunekService {

    private final Logger log = LoggerFactory.getLogger(GatunekServiceImpl.class);

    private final GatunekRepository gatunekRepository;

    private final GatunekMapper gatunekMapper;

    private final GatunekSearchRepository gatunekSearchRepository;

    public GatunekServiceImpl(GatunekRepository gatunekRepository, GatunekMapper gatunekMapper, GatunekSearchRepository gatunekSearchRepository) {
        this.gatunekRepository = gatunekRepository;
        this.gatunekMapper = gatunekMapper;
        this.gatunekSearchRepository = gatunekSearchRepository;
    }

    /**
     * Save a gatunek.
     *
     * @param gatunekDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GatunekDTO save(GatunekDTO gatunekDTO) {
        log.debug("Request to save Gatunek : {}", gatunekDTO);

        Gatunek gatunek = gatunekMapper.toEntity(gatunekDTO);
        gatunek = gatunekRepository.save(gatunek);
        GatunekDTO result = gatunekMapper.toDto(gatunek);
        gatunekSearchRepository.save(gatunek);
        return result;
    }

    /**
     * Get all the gatuneks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GatunekDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Gatuneks");
        return gatunekRepository.findAll(pageable)
            .map(gatunekMapper::toDto);
    }


    /**
     * Get one gatunek by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GatunekDTO> findOne(Long id) {
        log.debug("Request to get Gatunek : {}", id);
        return gatunekRepository.findById(id)
            .map(gatunekMapper::toDto);
    }

    /**
     * Delete the gatunek by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gatunek : {}", id);
        gatunekRepository.deleteById(id);
        gatunekSearchRepository.deleteById(id);
    }

    /**
     * Search for the gatunek corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GatunekDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Gatuneks for query {}", query);
        return gatunekSearchRepository.search(queryStringQuery(query), pageable)
            .map(gatunekMapper::toDto);
    }
}
