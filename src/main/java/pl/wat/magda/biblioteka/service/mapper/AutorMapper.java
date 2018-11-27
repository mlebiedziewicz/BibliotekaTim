package pl.wat.magda.biblioteka.service.mapper;

import pl.wat.magda.biblioteka.domain.*;
import pl.wat.magda.biblioteka.service.dto.AutorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Autor and its DTO AutorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AutorMapper extends EntityMapper<AutorDTO, Autor> {



    default Autor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Autor autor = new Autor();
        autor.setId(id);
        return autor;
    }
}
