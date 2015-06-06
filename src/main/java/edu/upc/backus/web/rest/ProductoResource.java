package edu.upc.backus.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.upc.backus.domain.Producto;
import edu.upc.backus.repository.ProductoRepository;
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
 * REST controller for managing Producto.
 */
@RestController
@RequestMapping("/api")
public class ProductoResource {

    private final Logger log = LoggerFactory.getLogger(ProductoResource.class);

    @Inject
    private ProductoRepository productoRepository;

    /**
     * POST  /productos -> Create a new producto.
     */
    @RequestMapping(value = "/productos",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Producto producto) throws URISyntaxException {
        log.debug("REST request to save Producto : {}", producto);
        if (producto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new producto cannot already have an ID").build();
        }
        productoRepository.save(producto);
        return ResponseEntity.created(new URI("/api/productos/" + producto.getId())).build();
    }

    /**
     * PUT  /productos -> Updates an existing producto.
     */
    @RequestMapping(value = "/productos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Producto producto) throws URISyntaxException {
        log.debug("REST request to update Producto : {}", producto);
        if (producto.getId() == null) {
            return create(producto);
        }
        productoRepository.save(producto);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /productos -> get all the productos.
     */
    @RequestMapping(value = "/productos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Producto>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Producto> page = productoRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/productos", offset, limit);
        return new ResponseEntity<List<Producto>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /productos/:id -> get the "id" producto.
     */
    @RequestMapping(value = "/productos/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Producto> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Producto : {}", id);
        Producto producto = productoRepository.findOne(id);
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    /**
     * DELETE  /productos/:id -> delete the "id" producto.
     */
    @RequestMapping(value = "/productos/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Producto : {}", id);
        productoRepository.delete(id);
    }
}
