package edu.upc.backus.repository;

import edu.upc.backus.domain.CostoProduccion;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CostoProduccion entity.
 */
public interface CostoProduccionRepository extends JpaRepository<CostoProduccion,Long> {

}
