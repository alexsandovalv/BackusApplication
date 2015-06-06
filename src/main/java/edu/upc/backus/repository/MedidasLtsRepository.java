package edu.upc.backus.repository;

import edu.upc.backus.domain.MedidasLts;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MedidasLts entity.
 */
public interface MedidasLtsRepository extends JpaRepository<MedidasLts,Long> {

}
