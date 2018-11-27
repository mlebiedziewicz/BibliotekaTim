package pl.wat.magda.biblioteka.service;

import pl.wat.magda.biblioteka.service.dto.WypozyczoneDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Wypozyczone.
 */
public interface WypozyczoneService {

    /**
     * Save a wypozyczone.
     *
     * @param wypozyczoneDTO the entity to save
     * @return the persisted entity
     */
    WypozyczoneDTO save(WypozyczoneDTO wypozyczoneDTO);

    /**
     * Get all the wypozyczones.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WypozyczoneDTO> findAll(Pageable pageable);

    /**
     * Get all the Wypozyczone with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<WypozyczoneDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" wypozyczone.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<WypozyczoneDTO> findOne(Long id);

    /**
     * Delete the "id" wypozyczone.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the wypozyczone corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WypozyczoneDTO> search(String query, Pageable pageable);
}
