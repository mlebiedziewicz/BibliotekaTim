package pl.wat.magda.biblioteka.service.mapper;

import pl.wat.magda.biblioteka.domain.*;
import pl.wat.magda.biblioteka.service.dto.GatunekDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Gatunek and its DTO GatunekDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GatunekMapper extends EntityMapper<GatunekDTO, Gatunek> {



    default Gatunek fromId(Long id) {
        if (id == null) {
            return null;
        }
        Gatunek gatunek = new Gatunek();
        gatunek.setId(id);
        return gatunek;
    }
}
