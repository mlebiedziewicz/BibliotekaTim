package pl.wat.magda.biblioteka.repository;

import pl.wat.magda.biblioteka.domain.Wydawnictwo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Wydawnictwo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WydawnictwoRepository extends JpaRepository<Wydawnictwo, Long> {

}
