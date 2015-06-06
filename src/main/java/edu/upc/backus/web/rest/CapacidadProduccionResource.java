package edu.upc.backus.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.upc.backus.domain.CapacidadProduccion;
import edu.upc.backus.repository.CapacidadProduccionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing CapacidadProduccion.
 */
@RestController
@RequestMapping("/api")
public class CapacidadProduccionResource {

    private final Logger log = LoggerFactory.getLogger(CapacidadProduccionResource.class);

    @Inject
    private CapacidadProduccionRepository capacidadProduccionRepository;

    /**
     * POST  /capacidadProduccions -> Create a new capacidadProduccion.
     */
    @RequestMapping(value = "/capacidadProduccions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody CapacidadProduccion capacidadProduccion) throws URISyntaxException {
        log.debug("REST request to save CapacidadProduccion : {}", capacidadProduccion);
        if (capacidadProduccion.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new capacidadProduccion cannot already have an ID").build();
        }
        capacidadProduccionRepository.save(capacidadProduccion);
        return ResponseEntity.created(new URI("/api/capacidadProduccions/" + capacidadProduccion.getId())).build();
    }

    /**
     * PUT  /capacidadProduccions -> Updates an existing capacidadProduccion.
     */
    @RequestMapping(value = "/capacidadProduccions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody CapacidadProduccion capacidadProduccion) throws URISyntaxException {
        log.debug("REST request to update CapacidadProduccion : {}", capacidadProduccion);
        if (capacidadProduccion.getId() == null) {
            return create(capacidadProduccion);
        }
        capacidadProduccionRepository.save(capacidadProduccion);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /capacidadProduccions -> get all the capacidadProduccions.
     */
    @RequestMapping(value = "/capacidadProduccions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CapacidadProduccion> getAll() {
        log.debug("REST request to get all CapacidadProduccions");
        return capacidadProduccionRepository.findAll();
    }

    /**
     * GET  /capacidadProduccions/:id -> get the "id" capacidadProduccion.
     */
    @RequestMapping(value = "/capacidadProduccions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CapacidadProduccion> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get CapacidadProduccion : {}", id);
        CapacidadProduccion capacidadProduccion = capacidadProduccionRepository.findOne(id);
        if (capacidadProduccion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(capacidadProduccion, HttpStatus.OK);
    }

    /**
     * DELETE  /capacidadProduccions/:id -> delete the "id" capacidadProduccion.
     */
    @RequestMapping(value = "/capacidadProduccions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete CapacidadProduccion : {}", id);
        capacidadProduccionRepository.delete(id);
    }
}
