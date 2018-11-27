package pl.wat.magda.biblioteka.repository;

import pl.wat.magda.biblioteka.domain.Autor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Autor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

}
