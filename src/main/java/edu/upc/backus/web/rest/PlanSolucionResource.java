package edu.upc.backus.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.upc.backus.domain.PlanSolucion;
import edu.upc.backus.repository.PlanSolucionRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing PlanSolucion.
 */
@RestController
@RequestMapping("/api")
public class PlanSolucionResource {

    private final Logger log = LoggerFactory.getLogger(PlanSolucionResource.class);

    @Inject
    private PlanSolucionRepository planSolucionRepository;

    /**
     * POST  /planSolucions -> Create a new planSolucion.
     */
    @RequestMapping(value = "/planSolucions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody PlanSolucion planSolucion) throws URISyntaxException {
        log.debug("REST request to save PlanSolucion : {}", planSolucion);
        if (planSolucion.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new planSolucion cannot already have an ID").build();
        }
        planSolucionRepository.save(planSolucion);
        return ResponseEntity.created(new URI("/api/planSolucions/" + planSolucion.getId())).build();
    }

    /**
     * PUT  /planSolucions -> Updates an existing planSolucion.
     */
    @RequestMapping(value = "/planSolucions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody PlanSolucion planSolucion) throws URISyntaxException {
        log.debug("REST request to update PlanSolucion : {}", planSolucion);
        if (planSolucion.getId() == null) {
            return create(planSolucion);
        }
        planSolucionRepository.save(planSolucion);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /planSolucions -> get all the planSolucions.
     */
    @RequestMapping(value = "/planSolucions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PlanSolucion>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<PlanSolucion> page = planSolucionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/planSolucions", offset, limit);
        return new ResponseEntity<List<PlanSolucion>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /planSolucions/:id -> get the "id" planSolucion.
     */
    @RequestMapping(value = "/planSolucions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PlanSolucion> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get PlanSolucion : {}", id);
        PlanSolucion planSolucion = planSolucionRepository.findOne(id);
        if (planSolucion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(planSolucion, HttpStatus.OK);
    }

    /**
     * DELETE  /planSolucions/:id -> delete the "id" planSolucion.
     */
    @RequestMapping(value = "/planSolucions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete PlanSolucion : {}", id);
        planSolucionRepository.delete(id);
    }
}
