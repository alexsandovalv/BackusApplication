package edu.upc.backus.web.rest;

import com.codahale.metrics.annotation.Timed;

import edu.upc.backus.domain.ProgramaProduccion;
import edu.upc.backus.repository.ProgramaProduccionRepository;
import edu.upc.backus.web.rest.util.ExportExcelUtil;
import edu.upc.backus.web.rest.util.LingoUTil;
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
 * REST controller for managing ProgramaProduccion.
 */
@RestController
@RequestMapping("/api")
public class ProgramaProduccionResource {

    private final Logger log = LoggerFactory.getLogger(ProgramaProduccionResource.class);

    @Inject
    private ProgramaProduccionRepository programaProduccionRepository;

    /**
     * POST  /programaProduccions -> Create a new programaProduccion.
     */
    @RequestMapping(value = "/programaProduccions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody ProgramaProduccion programaProduccion) throws URISyntaxException {
        log.debug("REST request to save ProgramaProduccion : {}", programaProduccion);
        if (programaProduccion.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new programaProduccion cannot already have an ID").build();
        }
        programaProduccionRepository.save(programaProduccion);
        return ResponseEntity.created(new URI("/api/programaProduccions/" + programaProduccion.getId())).build();
    }

    /**
     * PUT  /programaProduccions -> Updates an existing programaProduccion.
     */
    @RequestMapping(value = "/programaProduccions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody ProgramaProduccion programaProduccion) throws URISyntaxException {
        log.debug("REST request to update ProgramaProduccion : {}", programaProduccion);
        if (programaProduccion.getId() == null) {
            return create(programaProduccion);
        }
        programaProduccionRepository.save(programaProduccion);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /programaProduccions -> get all the programaProduccions.
     */
    @RequestMapping(value = "/programaProduccions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ProgramaProduccion>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<ProgramaProduccion> page = programaProduccionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/programaProduccions", offset, limit);
        return new ResponseEntity<List<ProgramaProduccion>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /programaProduccions/:id -> get the "id" programaProduccion.
     */
    @RequestMapping(value = "/programaProduccions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProgramaProduccion> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get ProgramaProduccion : {}", id);
        ProgramaProduccion programaProduccion = programaProduccionRepository.findOne(id);
        if (programaProduccion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(programaProduccion, HttpStatus.OK);
    }

    /**
     * DELETE  /programaProduccions/:id -> delete the "id" programaProduccion.
     */
    @RequestMapping(value = "/programaProduccions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete ProgramaProduccion : {}", id);
        programaProduccionRepository.delete(id);
    }
    
    @RequestMapping(value = "/programaProduccions/lingo",
            method = RequestMethod.GET)
    public void processLingo(){
    	String filePath = System.getProperty("user.dir")+ "\\src\\main\\resources";
    	log.debug("Leyendo excel con path "+filePath);
    	List<ProgramaProduccion> programas = programaProduccionRepository.findAll();
    	ExportExcelUtil.writeToExcel(programas, filePath);
    	
    	log.debug("Procesando Lingo");
    	LingoUTil lingo = new LingoUTil();
    	lingo.executeLingoFile(filePath, filePath);
    	
    }
    
}
