package pl.wat.magda.biblioteka.service;

import pl.wat.magda.biblioteka.service.dto.KsiazkaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Ksiazka.
 */
public interface KsiazkaService {

    /**
     * Save a ksiazka.
     *
     * @param ksiazkaDTO the entity to save
     * @return the persisted entity
     */
    KsiazkaDTO save(KsiazkaDTO ksiazkaDTO);

    /**
     * Get all the ksiazkas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<KsiazkaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ksiazka.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<KsiazkaDTO> findOne(Long id);

    /**
     * Delete the "id" ksiazka.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the ksiazka corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<KsiazkaDTO> search(String query, Pageable pageable);
}
