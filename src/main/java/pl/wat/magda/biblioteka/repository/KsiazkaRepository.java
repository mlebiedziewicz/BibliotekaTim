package pl.wat.magda.biblioteka.repository;

import pl.wat.magda.biblioteka.domain.Ksiazka;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ksiazka entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KsiazkaRepository extends JpaRepository<Ksiazka, Long> {

}
