package edu.upc.backus.repository;

import edu.upc.backus.domain.ProgramaProduccion;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProgramaProduccion entity.
 */
public interface ProgramaProduccionRepository extends JpaRepository<ProgramaProduccion,Long> {

}
