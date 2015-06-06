package edu.upc.backus.repository;

import edu.upc.backus.domain.PlanSolucion;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PlanSolucion entity.
 */
public interface PlanSolucionRepository extends JpaRepository<PlanSolucion,Long> {

}
