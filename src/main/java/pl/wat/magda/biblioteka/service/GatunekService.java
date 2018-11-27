package pl.wat.magda.biblioteka.service;

import pl.wat.magda.biblioteka.service.dto.GatunekDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Gatunek.
 */
public interface GatunekService {

    /**
     * Save a gatunek.
     *
     * @param gatunekDTO the entity to save
     * @return the persisted entity
     */
    GatunekDTO save(GatunekDTO gatunekDTO);

    /**
     * Get all the gatuneks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GatunekDTO> findAll(Pageable pageable);


    /**
     * Get the "id" gatunek.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GatunekDTO> findOne(Long id);

    /**
     * Delete the "id" gatunek.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the gatunek corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GatunekDTO> search(String query, Pageable pageable);
}
