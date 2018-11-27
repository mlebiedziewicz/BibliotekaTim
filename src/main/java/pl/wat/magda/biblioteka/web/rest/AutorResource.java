package pl.wat.magda.biblioteka.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.wat.magda.biblioteka.service.AutorService;
import pl.wat.magda.biblioteka.web.rest.errors.BadRequestAlertException;
import pl.wat.magda.biblioteka.web.rest.util.HeaderUtil;
import pl.wat.magda.biblioteka.web.rest.util.PaginationUtil;
import pl.wat.magda.biblioteka.service.dto.AutorDTO;
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
 * REST controller for managing Autor.
 */
@RestController
@RequestMapping("/api")
public class AutorResource {

    private final Logger log = LoggerFactory.getLogger(AutorResource.class);

    private static final String ENTITY_NAME = "autor";

    private final AutorService autorService;

    public AutorResource(AutorService autorService) {
        this.autorService = autorService;
    }

    /**
     * POST  /autors : Create a new autor.
     *
     * @param autorDTO the autorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new autorDTO, or with status 400 (Bad Request) if the autor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/autors")
    @Timed
    public ResponseEntity<AutorDTO> createAutor(@Valid @RequestBody AutorDTO autorDTO) throws URISyntaxException {
        log.debug("REST request to save Autor : {}", autorDTO);
        if (autorDTO.getId() != null) {
            throw new BadRequestAlertException("A new autor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutorDTO result = autorService.save(autorDTO);
        return ResponseEntity.created(new URI("/api/autors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /autors : Updates an existing autor.
     *
     * @param autorDTO the autorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated autorDTO,
     * or with status 400 (Bad Request) if the autorDTO is not valid,
     * or with status 500 (Internal Server Error) if the autorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/autors")
    @Timed
    public ResponseEntity<AutorDTO> updateAutor(@Valid @RequestBody AutorDTO autorDTO) throws URISyntaxException {
        log.debug("REST request to update Autor : {}", autorDTO);
        if (autorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutorDTO result = autorService.save(autorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, autorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /autors : get all the autors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of autors in body
     */
    @GetMapping("/autors")
    @Timed
    public ResponseEntity<List<AutorDTO>> getAllAutors(Pageable pageable) {
        log.debug("REST request to get a page of Autors");
        Page<AutorDTO> page = autorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/autors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /autors/:id : get the "id" autor.
     *
     * @param id the id of the autorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the autorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/autors/{id}")
    @Timed
    public ResponseEntity<AutorDTO> getAutor(@PathVariable Long id) {
        log.debug("REST request to get Autor : {}", id);
        Optional<AutorDTO> autorDTO = autorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autorDTO);
    }

    /**
     * DELETE  /autors/:id : delete the "id" autor.
     *
     * @param id the id of the autorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/autors/{id}")
    @Timed
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {
        log.debug("REST request to delete Autor : {}", id);
        autorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/autors?query=:query : search for the autor corresponding
     * to the query.
     *
     * @param query the query of the autor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/autors")
    @Timed
    public ResponseEntity<List<AutorDTO>> searchAutors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Autors for query {}", query);
        Page<AutorDTO> page = autorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/autors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
