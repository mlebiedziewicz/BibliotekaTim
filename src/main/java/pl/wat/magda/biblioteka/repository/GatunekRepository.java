package pl.wat.magda.biblioteka.repository;

import pl.wat.magda.biblioteka.domain.Gatunek;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Gatunek entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GatunekRepository extends JpaRepository<Gatunek, Long> {

}
