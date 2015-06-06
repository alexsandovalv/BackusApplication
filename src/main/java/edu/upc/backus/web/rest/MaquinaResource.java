package edu.upc.backus.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.upc.backus.domain.Maquina;
import edu.upc.backus.repository.MaquinaRepository;
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
 * REST controller for managing Maquina.
 */
@RestController
@RequestMapping("/api")
public class MaquinaResource {

    private final Logger log = LoggerFactory.getLogger(MaquinaResource.class);

    @Inject
    private MaquinaRepository maquinaRepository;

    /**
     * POST  /maquinas -> Create a new maquina.
     */
    @RequestMapping(value = "/maquinas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Maquina maquina) throws URISyntaxException {
        log.debug("REST request to save Maquina : {}", maquina);
        if (maquina.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new maquina cannot already have an ID").build();
        }
        maquinaRepository.save(maquina);
        return ResponseEntity.created(new URI("/api/maquinas/" + maquina.getId())).build();
    }

    /**
     * PUT  /maquinas -> Updates an existing maquina.
     */
    @RequestMapping(value = "/maquinas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Maquina maquina) throws URISyntaxException {
        log.debug("REST request to update Maquina : {}", maquina);
        if (maquina.getId() == null) {
            return create(maquina);
        }
        maquinaRepository.save(maquina);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /maquinas -> get all the maquinas.
     */
    @RequestMapping(value = "/maquinas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Maquina>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Maquina> page = maquinaRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/maquinas", offset, limit);
        return new ResponseEntity<List<Maquina>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /maquinas/:id -> get the "id" maquina.
     */
    @RequestMapping(value = "/maquinas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Maquina> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Maquina : {}", id);
        Maquina maquina = maquinaRepository.findOne(id);
        if (maquina == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(maquina, HttpStatus.OK);
    }

    /**
     * DELETE  /maquinas/:id -> delete the "id" maquina.
     */
    @RequestMapping(value = "/maquinas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Maquina : {}", id);
        maquinaRepository.delete(id);
    }
}
