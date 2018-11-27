package pl.wat.magda.biblioteka.service.impl;

import pl.wat.magda.biblioteka.service.WypozyczoneService;
import pl.wat.magda.biblioteka.domain.Wypozyczone;
import pl.wat.magda.biblioteka.repository.WypozyczoneRepository;
import pl.wat.magda.biblioteka.repository.search.WypozyczoneSearchRepository;
import pl.wat.magda.biblioteka.service.dto.WypozyczoneDTO;
import pl.wat.magda.biblioteka.service.mapper.WypozyczoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Wypozyczone.
 */
@Service
@Transactional
public class WypozyczoneServiceImpl implements WypozyczoneService {

    private final Logger log = LoggerFactory.getLogger(WypozyczoneServiceImpl.class);

    private final WypozyczoneRepository wypozyczoneRepository;

    private final WypozyczoneMapper wypozyczoneMapper;

    private final WypozyczoneSearchRepository wypozyczoneSearchRepository;

    public WypozyczoneServiceImpl(WypozyczoneRepository wypozyczoneRepository, WypozyczoneMapper wypozyczoneMapper, WypozyczoneSearchRepository wypozyczoneSearchRepository) {
        this.wypozyczoneRepository = wypozyczoneRepository;
        this.wypozyczoneMapper = wypozyczoneMapper;
        this.wypozyczoneSearchRepository = wypozyczoneSearchRepository;
    }

    /**
     * Save a wypozyczone.
     *
     * @param wypozyczoneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WypozyczoneDTO save(WypozyczoneDTO wypozyczoneDTO) {
        log.debug("Request to save Wypozyczone : {}", wypozyczoneDTO);

        Wypozyczone wypozyczone = wypozyczoneMapper.toEntity(wypozyczoneDTO);
        wypozyczone = wypozyczoneRepository.save(wypozyczone);
        WypozyczoneDTO result = wypozyczoneMapper.toDto(wypozyczone);
        wypozyczoneSearchRepository.save(wypozyczone);
        return result;
    }

    /**
     * Get all the wypozyczones.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WypozyczoneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Wypozyczones");
        return wypozyczoneRepository.findAll(pageable)
            .map(wypozyczoneMapper::toDto);
    }

    /**
     * Get all the Wypozyczone with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<WypozyczoneDTO> findAllWithEagerRelationships(Pageable pageable) {
        return wypozyczoneRepository.findAllWithEagerRelationships(pageable).map(wypozyczoneMapper::toDto);
    }
    

    /**
     * Get one wypozyczone by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WypozyczoneDTO> findOne(Long id) {
        log.debug("Request to get Wypozyczone : {}", id);
        return wypozyczoneRepository.findOneWithEagerRelationships(id)
            .map(wypozyczoneMapper::toDto);
    }

    /**
     * Delete the wypozyczone by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Wypozyczone : {}", id);
        wypozyczoneRepository.deleteById(id);
        wypozyczoneSearchRepository.deleteById(id);
    }

    /**
     * Search for the wypozyczone corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WypozyczoneDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Wypozyczones for query {}", query);
        return wypozyczoneSearchRepository.search(queryStringQuery(query), pageable)
            .map(wypozyczoneMapper::toDto);
    }
}
