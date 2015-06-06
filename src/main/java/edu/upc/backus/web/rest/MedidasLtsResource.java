package edu.upc.backus.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.upc.backus.domain.MedidasLts;
import edu.upc.backus.repository.MedidasLtsRepository;
import edu.upc.backus.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing MedidasLts.
 */
@RestController
@RequestMapping("/api")
public class MedidasLtsResource {

    private final Logger log = LoggerFactory.getLogger(MedidasLtsResource.class);

    @Inject
    private MedidasLtsRepository medidasLtsRepository;

    /**
     * POST  /medidasLtss -> Create a new medidasLts.
     */
    @RequestMapping(value = "/medidasLtss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody MedidasLts medidasLts) throws URISyntaxException {
        log.debug("REST request to save MedidasLts : {}", medidasLts);
        if (medidasLts.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new medidasLts cannot already have an ID").build();
        }
        medidasLtsRepository.save(medidasLts);
        return ResponseEntity.created(new URI("/api/medidasLtss/" + medidasLts.getId())).build();
    }

    /**
     * PUT  /medidasLtss -> Updates an existing medidasLts.
     */
    @RequestMapping(value = "/medidasLtss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody MedidasLts medidasLts) throws URISyntaxException {
        log.debug("REST request to update MedidasLts : {}", medidasLts);
        if (medidasLts.getId() == null) {
            return create(medidasLts);
        }
        medidasLtsRepository.save(medidasLts);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /medidasLtss -> get all the medidasLtss.
     */
    @RequestMapping(value = "/medidasLtss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MedidasLts>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<MedidasLts> page = medidasLtsRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medidasLtss", offset, limit);
        return new ResponseEntity<List<MedidasLts>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /medidasLtss/:id -> get the "id" medidasLts.
     */
    @RequestMapping(value = "/medidasLtss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MedidasLts> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get MedidasLts : {}", id);
        MedidasLts medidasLts = medidasLtsRepository.findOne(id);
        if (medidasLts == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(medidasLts, HttpStatus.OK);
    }

    /**
     * DELETE  /medidasLtss/:id -> delete the "id" medidasLts.
     */
    @RequestMapping(value = "/medidasLtss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete MedidasLts : {}", id);
        medidasLtsRepository.delete(id);
    }
}
