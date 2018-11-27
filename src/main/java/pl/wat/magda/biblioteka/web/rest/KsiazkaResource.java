package pl.wat.magda.biblioteka.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.wat.magda.biblioteka.service.KsiazkaService;
import pl.wat.magda.biblioteka.web.rest.errors.BadRequestAlertException;
import pl.wat.magda.biblioteka.web.rest.util.HeaderUtil;
import pl.wat.magda.biblioteka.web.rest.util.PaginationUtil;
import pl.wat.magda.biblioteka.service.dto.KsiazkaDTO;
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
 * REST controller for managing Ksiazka.
 */
@RestController
@RequestMapping("/api")
public class KsiazkaResource {

    private final Logger log = LoggerFactory.getLogger(KsiazkaResource.class);

    private static final String ENTITY_NAME = "ksiazka";

    private final KsiazkaService ksiazkaService;

    public KsiazkaResource(KsiazkaService ksiazkaService) {
        this.ksiazkaService = ksiazkaService;
    }

    /**
     * POST  /ksiazkas : Create a new ksiazka.
     *
     * @param ksiazkaDTO the ksiazkaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ksiazkaDTO, or with status 400 (Bad Request) if the ksiazka has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ksiazkas")
    @Timed
    public ResponseEntity<KsiazkaDTO> createKsiazka(@Valid @RequestBody KsiazkaDTO ksiazkaDTO) throws URISyntaxException {
        log.debug("REST request to save Ksiazka : {}", ksiazkaDTO);
        if (ksiazkaDTO.getId() != null) {
            throw new BadRequestAlertException("A new ksiazka cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KsiazkaDTO result = ksiazkaService.save(ksiazkaDTO);
        return ResponseEntity.created(new URI("/api/ksiazkas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ksiazkas : Updates an existing ksiazka.
     *
     * @param ksiazkaDTO the ksiazkaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ksiazkaDTO,
     * or with status 400 (Bad Request) if the ksiazkaDTO is not valid,
     * or with status 500 (Internal Server Error) if the ksiazkaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ksiazkas")
    @Timed
    public ResponseEntity<KsiazkaDTO> updateKsiazka(@Valid @RequestBody KsiazkaDTO ksiazkaDTO) throws URISyntaxException {
        log.debug("REST request to update Ksiazka : {}", ksiazkaDTO);
        if (ksiazkaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KsiazkaDTO result = ksiazkaService.save(ksiazkaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ksiazkaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ksiazkas : get all the ksiazkas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ksiazkas in body
     */
    @GetMapping("/ksiazkas")
    @Timed
    public ResponseEntity<List<KsiazkaDTO>> getAllKsiazkas(Pageable pageable) {
        log.debug("REST request to get a page of Ksiazkas");
        Page<KsiazkaDTO> page = ksiazkaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ksiazkas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /ksiazkas/:id : get the "id" ksiazka.
     *
     * @param id the id of the ksiazkaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ksiazkaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ksiazkas/{id}")
    @Timed
    public ResponseEntity<KsiazkaDTO> getKsiazka(@PathVariable Long id) {
        log.debug("REST request to get Ksiazka : {}", id);
        Optional<KsiazkaDTO> ksiazkaDTO = ksiazkaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ksiazkaDTO);
    }

    /**
     * DELETE  /ksiazkas/:id : delete the "id" ksiazka.
     *
     * @param id the id of the ksiazkaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ksiazkas/{id}")
    @Timed
    public ResponseEntity<Void> deleteKsiazka(@PathVariable Long id) {
        log.debug("REST request to delete Ksiazka : {}", id);
        ksiazkaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ksiazkas?query=:query : search for the ksiazka corresponding
     * to the query.
     *
     * @param query the query of the ksiazka search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ksiazkas")
    @Timed
    public ResponseEntity<List<KsiazkaDTO>> searchKsiazkas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Ksiazkas for query {}", query);
        Page<KsiazkaDTO> page = ksiazkaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ksiazkas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
