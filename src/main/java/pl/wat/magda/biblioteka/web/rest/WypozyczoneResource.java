package pl.wat.magda.biblioteka.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.wat.magda.biblioteka.service.WypozyczoneService;
import pl.wat.magda.biblioteka.web.rest.errors.BadRequestAlertException;
import pl.wat.magda.biblioteka.web.rest.util.HeaderUtil;
import pl.wat.magda.biblioteka.web.rest.util.PaginationUtil;
import pl.wat.magda.biblioteka.service.dto.WypozyczoneDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Wypozyczone.
 */
@RestController
@RequestMapping("/api")
public class WypozyczoneResource {

    private final Logger log = LoggerFactory.getLogger(WypozyczoneResource.class);

    private static final String ENTITY_NAME = "wypozyczone";

    private final WypozyczoneService wypozyczoneService;

    public WypozyczoneResource(WypozyczoneService wypozyczoneService) {
        this.wypozyczoneService = wypozyczoneService;
    }

    /**
     * POST  /wypozyczones : Create a new wypozyczone.
     *
     * @param wypozyczoneDTO the wypozyczoneDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wypozyczoneDTO, or with status 400 (Bad Request) if the wypozyczone has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wypozyczones")
    @Timed
    public ResponseEntity<WypozyczoneDTO> createWypozyczone(@Valid @RequestBody WypozyczoneDTO wypozyczoneDTO) throws URISyntaxException {
        log.debug("REST request to save Wypozyczone : {}", wypozyczoneDTO);
        if (wypozyczoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new wypozyczone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WypozyczoneDTO result = wypozyczoneService.save(wypozyczoneDTO);
        return ResponseEntity.created(new URI("/api/wypozyczones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wypozyczones : Updates an existing wypozyczone.
     *
     * @param wypozyczoneDTO the wypozyczoneDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wypozyczoneDTO,
     * or with status 400 (Bad Request) if the wypozyczoneDTO is not valid,
     * or with status 500 (Internal Server Error) if the wypozyczoneDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wypozyczones")
    @Timed
    public ResponseEntity<WypozyczoneDTO> updateWypozyczone(@Valid @RequestBody WypozyczoneDTO wypozyczoneDTO) throws URISyntaxException {
        log.debug("REST request to update Wypozyczone : {}", wypozyczoneDTO);
        if (wypozyczoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WypozyczoneDTO result = wypozyczoneService.save(wypozyczoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wypozyczoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wypozyczones : get all the wypozyczones.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of wypozyczones in body
     */
    @GetMapping("/wypozyczones")
    @Timed
    public ResponseEntity<List<WypozyczoneDTO>> getAllWypozyczones(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Wypozyczones");
        Page<WypozyczoneDTO> page;
        if (eagerload) {
            page = wypozyczoneService.findAllWithEagerRelationships(pageable);
        } else {
            page = wypozyczoneService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/wypozyczones?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /wypozyczones/:id : get the "id" wypozyczone.
     *
     * @param id the id of the wypozyczoneDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wypozyczoneDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wypozyczones/{id}")
    @Timed
    public ResponseEntity<WypozyczoneDTO> getWypozyczone(@PathVariable Long id) {
        log.debug("REST request to get Wypozyczone : {}", id);
        Optional<WypozyczoneDTO> wypozyczoneDTO = wypozyczoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wypozyczoneDTO);
    }

    /**
     * DELETE  /wypozyczones/:id : delete the "id" wypozyczone.
     *
     * @param id the id of the wypozyczoneDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wypozyczones/{id}")
    @Timed
    public ResponseEntity<Void> deleteWypozyczone(@PathVariable Long id) {
        log.debug("REST request to delete Wypozyczone : {}", id);
        wypozyczoneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/wypozyczones?query=:query : search for the wypozyczone corresponding
     * to the query.
     *
     * @param query the query of the wypozyczone search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/wypozyczones")
    @Timed
    public ResponseEntity<List<WypozyczoneDTO>> searchWypozyczones(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Wypozyczones for query {}", query);
        Page<WypozyczoneDTO> page = wypozyczoneService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/wypozyczones");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
