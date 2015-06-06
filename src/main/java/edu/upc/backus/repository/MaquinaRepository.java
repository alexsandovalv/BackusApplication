package edu.upc.backus.repository;

import edu.upc.backus.domain.Maquina;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Maquina entity.
 */
public interface MaquinaRepository extends JpaRepository<Maquina,Long> {

}
