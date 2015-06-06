package edu.upc.backus.repository;

import edu.upc.backus.domain.CapacidadProduccion;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CapacidadProduccion entity.
 */
public interface CapacidadProduccionRepository extends JpaRepository<CapacidadProduccion,Long> {

}
