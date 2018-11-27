package pl.wat.magda.biblioteka.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.wat.magda.biblioteka.service.GatunekService;
import pl.wat.magda.biblioteka.web.rest.errors.BadRequestAlertException;
import pl.wat.magda.biblioteka.web.rest.util.HeaderUtil;
import pl.wat.magda.biblioteka.web.rest.util.PaginationUtil;
import pl.wat.magda.biblioteka.service.dto.GatunekDTO;
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
 * REST controller for managing Gatunek.
 */
@RestController
@RequestMapping("/api")
public class GatunekResource {

    private final Logger log = LoggerFactory.getLogger(GatunekResource.class);

    private static final String ENTITY_NAME = "gatunek";

    private final GatunekService gatunekService;

    public GatunekResource(GatunekService gatunekService) {
        this.gatunekService = gatunekService;
    }

    /**
     * POST  /gatuneks : Create a new gatunek.
     *
     * @param gatunekDTO the gatunekDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gatunekDTO, or with status 400 (Bad Request) if the gatunek has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gatuneks")
    @Timed
    public ResponseEntity<GatunekDTO> createGatunek(@Valid @RequestBody GatunekDTO gatunekDTO) throws URISyntaxException {
        log.debug("REST request to save Gatunek : {}", gatunekDTO);
        if (gatunekDTO.getId() != null) {
            throw new BadRequestAlertException("A new gatunek cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GatunekDTO result = gatunekService.save(gatunekDTO);
        return ResponseEntity.created(new URI("/api/gatuneks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gatuneks : Updates an existing gatunek.
     *
     * @param gatunekDTO the gatunekDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gatunekDTO,
     * or with status 400 (Bad Request) if the gatunekDTO is not valid,
     * or with status 500 (Internal Server Error) if the gatunekDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gatuneks")
    @Timed
    public ResponseEntity<GatunekDTO> updateGatunek(@Valid @RequestBody GatunekDTO gatunekDTO) throws URISyntaxException {
        log.debug("REST request to update Gatunek : {}", gatunekDTO);
        if (gatunekDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GatunekDTO result = gatunekService.save(gatunekDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gatunekDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gatuneks : get all the gatuneks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of gatuneks in body
     */
    @GetMapping("/gatuneks")
    @Timed
    public ResponseEntity<List<GatunekDTO>> getAllGatuneks(Pageable pageable) {
        log.debug("REST request to get a page of Gatuneks");
        Page<GatunekDTO> page = gatunekService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/gatuneks");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /gatuneks/:id : get the "id" gatunek.
     *
     * @param id the id of the gatunekDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gatunekDTO, or with status 404 (Not Found)
     */
    @GetMapping("/gatuneks/{id}")
    @Timed
    public ResponseEntity<GatunekDTO> getGatunek(@PathVariable Long id) {
        log.debug("REST request to get Gatunek : {}", id);
        Optional<GatunekDTO> gatunekDTO = gatunekService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gatunekDTO);
    }

    /**
     * DELETE  /gatuneks/:id : delete the "id" gatunek.
     *
     * @param id the id of the gatunekDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gatuneks/{id}")
    @Timed
    public ResponseEntity<Void> deleteGatunek(@PathVariable Long id) {
        log.debug("REST request to delete Gatunek : {}", id);
        gatunekService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/gatuneks?query=:query : search for the gatunek corresponding
     * to the query.
     *
     * @param query the query of the gatunek search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/gatuneks")
    @Timed
    public ResponseEntity<List<GatunekDTO>> searchGatuneks(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Gatuneks for query {}", query);
        Page<GatunekDTO> page = gatunekService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/gatuneks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
