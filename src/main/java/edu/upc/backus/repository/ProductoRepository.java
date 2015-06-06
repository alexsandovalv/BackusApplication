package edu.upc.backus.repository;

import edu.upc.backus.domain.Producto;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Producto entity.
 */
public interface ProductoRepository extends JpaRepository<Producto,Long> {

}
