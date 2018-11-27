package pl.wat.magda.biblioteka.repository;

import pl.wat.magda.biblioteka.domain.Wypozyczone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Wypozyczone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WypozyczoneRepository extends JpaRepository<Wypozyczone, Long> {

    @Query(value = "select distinct wypozyczone from Wypozyczone wypozyczone left join fetch wypozyczone.uzytkowniks left join fetch wypozyczone.ksiazkas",
        countQuery = "select count(distinct wypozyczone) from Wypozyczone wypozyczone")
    Page<Wypozyczone> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct wypozyczone from Wypozyczone wypozyczone left join fetch wypozyczone.uzytkowniks left join fetch wypozyczone.ksiazkas")
    List<Wypozyczone> findAllWithEagerRelationships();

    @Query("select wypozyczone from Wypozyczone wypozyczone left join fetch wypozyczone.uzytkowniks left join fetch wypozyczone.ksiazkas where wypozyczone.id =:id")
    Optional<Wypozyczone> findOneWithEagerRelationships(@Param("id") Long id);

}
