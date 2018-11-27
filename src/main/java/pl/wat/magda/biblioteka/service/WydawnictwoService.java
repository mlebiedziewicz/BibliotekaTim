package pl.wat.magda.biblioteka.service;

import pl.wat.magda.biblioteka.service.dto.WydawnictwoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Wydawnictwo.
 */
public interface WydawnictwoService {

    /**
     * Save a wydawnictwo.
     *
     * @param wydawnictwoDTO the entity to save
     * @return the persisted entity
     */
    WydawnictwoDTO save(WydawnictwoDTO wydawnictwoDTO);

    /**
     * Get all the wydawnictwos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WydawnictwoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" wydawnictwo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<WydawnictwoDTO> findOne(Long id);

    /**
     * Delete the "id" wydawnictwo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the wydawnictwo corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WydawnictwoDTO> search(String query, Pageable pageable);
}
