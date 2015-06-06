package edu.upc.backus.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.upc.backus.domain.CostoProduccion;
import edu.upc.backus.repository.CostoProduccionRepository;
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
 * REST controller for managing CostoProduccion.
 */
@RestController
@RequestMapping("/api")
public class CostoProduccionResource {

    private final Logger log = LoggerFactory.getLogger(CostoProduccionResource.class);

    @Inject
    private CostoProduccionRepository costoProduccionRepository;

    /**
     * POST  /costoProduccions -> Create a new costoProduccion.
     */
    @RequestMapping(value = "/costoProduccions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody CostoProduccion costoProduccion) throws URISyntaxException {
        log.debug("REST request to save CostoProduccion : {}", costoProduccion);
        if (costoProduccion.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new costoProduccion cannot already have an ID").build();
        }
        costoProduccionRepository.save(costoProduccion);
        return ResponseEntity.created(new URI("/api/costoProduccions/" + costoProduccion.getId())).build();
    }

    /**
     * PUT  /costoProduccions -> Updates an existing costoProduccion.
     */
    @RequestMapping(value = "/costoProduccions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody CostoProduccion costoProduccion) throws URISyntaxException {
        log.debug("REST request to update CostoProduccion : {}", costoProduccion);
        if (costoProduccion.getId() == null) {
            return create(costoProduccion);
        }
        costoProduccionRepository.save(costoProduccion);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /costoProduccions -> get all the costoProduccions.
     */
    @RequestMapping(value = "/costoProduccions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CostoProduccion>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<CostoProduccion> page = costoProduccionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/costoProduccions", offset, limit);
        return new ResponseEntity<List<CostoProduccion>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /costoProduccions/:id -> get the "id" costoProduccion.
     */
    @RequestMapping(value = "/costoProduccions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CostoProduccion> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get CostoProduccion : {}", id);
        CostoProduccion costoProduccion = costoProduccionRepository.findOne(id);
        if (costoProduccion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(costoProduccion, HttpStatus.OK);
    }

    /**
     * DELETE  /costoProduccions/:id -> delete the "id" costoProduccion.
     */
    @RequestMapping(value = "/costoProduccions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete CostoProduccion : {}", id);
        costoProduccionRepository.delete(id);
    }
}
