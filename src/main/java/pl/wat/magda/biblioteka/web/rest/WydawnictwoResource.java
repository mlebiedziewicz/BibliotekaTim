package pl.wat.magda.biblioteka.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.wat.magda.biblioteka.service.WydawnictwoService;
import pl.wat.magda.biblioteka.web.rest.errors.BadRequestAlertException;
import pl.wat.magda.biblioteka.web.rest.util.HeaderUtil;
import pl.wat.magda.biblioteka.web.rest.util.PaginationUtil;
import pl.wat.magda.biblioteka.service.dto.WydawnictwoDTO;
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
 * REST controller for managing Wydawnictwo.
 */
@RestController
@RequestMapping("/api")
public class WydawnictwoResource {

    private final Logger log = LoggerFactory.getLogger(WydawnictwoResource.class);

    private static final String ENTITY_NAME = "wydawnictwo";

    private final WydawnictwoService wydawnictwoService;

    public WydawnictwoResource(WydawnictwoService wydawnictwoService) {
        this.wydawnictwoService = wydawnictwoService;
    }

    /**
     * POST  /wydawnictwos : Create a new wydawnictwo.
     *
     * @param wydawnictwoDTO the wydawnictwoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wydawnictwoDTO, or with status 400 (Bad Request) if the wydawnictwo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wydawnictwos")
    @Timed
    public ResponseEntity<WydawnictwoDTO> createWydawnictwo(@Valid @RequestBody WydawnictwoDTO wydawnictwoDTO) throws URISyntaxException {
        log.debug("REST request to save Wydawnictwo : {}", wydawnictwoDTO);
        if (wydawnictwoDTO.getId() != null) {
            throw new BadRequestAlertException("A new wydawnictwo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WydawnictwoDTO result = wydawnictwoService.save(wydawnictwoDTO);
        return ResponseEntity.created(new URI("/api/wydawnictwos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wydawnictwos : Updates an existing wydawnictwo.
     *
     * @param wydawnictwoDTO the wydawnictwoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wydawnictwoDTO,
     * or with status 400 (Bad Request) if the wydawnictwoDTO is not valid,
     * or with status 500 (Internal Server Error) if the wydawnictwoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wydawnictwos")
    @Timed
    public ResponseEntity<WydawnictwoDTO> updateWydawnictwo(@Valid @RequestBody WydawnictwoDTO wydawnictwoDTO) throws URISyntaxException {
        log.debug("REST request to update Wydawnictwo : {}", wydawnictwoDTO);
        if (wydawnictwoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WydawnictwoDTO result = wydawnictwoService.save(wydawnictwoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wydawnictwoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wydawnictwos : get all the wydawnictwos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wydawnictwos in body
     */
    @GetMapping("/wydawnictwos")
    @Timed
    public ResponseEntity<List<WydawnictwoDTO>> getAllWydawnictwos(Pageable pageable) {
        log.debug("REST request to get a page of Wydawnictwos");
        Page<WydawnictwoDTO> page = wydawnictwoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wydawnictwos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /wydawnictwos/:id : get the "id" wydawnictwo.
     *
     * @param id the id of the wydawnictwoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wydawnictwoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wydawnictwos/{id}")
    @Timed
    public ResponseEntity<WydawnictwoDTO> getWydawnictwo(@PathVariable Long id) {
        log.debug("REST request to get Wydawnictwo : {}", id);
        Optional<WydawnictwoDTO> wydawnictwoDTO = wydawnictwoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wydawnictwoDTO);
    }

    /**
     * DELETE  /wydawnictwos/:id : delete the "id" wydawnictwo.
     *
     * @param id the id of the wydawnictwoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wydawnictwos/{id}")
    @Timed
    public ResponseEntity<Void> deleteWydawnictwo(@PathVariable Long id) {
        log.debug("REST request to delete Wydawnictwo : {}", id);
        wydawnictwoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/wydawnictwos?query=:query : search for the wydawnictwo corresponding
     * to the query.
     *
     * @param query the query of the wydawnictwo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/wydawnictwos")
    @Timed
    public ResponseEntity<List<WydawnictwoDTO>> searchWydawnictwos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Wydawnictwos for query {}", query);
        Page<WydawnictwoDTO> page = wydawnictwoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/wydawnictwos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
