package pl.wat.magda.biblioteka.service.mapper;

import pl.wat.magda.biblioteka.domain.*;
import pl.wat.magda.biblioteka.service.dto.WydawnictwoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Wydawnictwo and its DTO WydawnictwoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WydawnictwoMapper extends EntityMapper<WydawnictwoDTO, Wydawnictwo> {



    default Wydawnictwo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Wydawnictwo wydawnictwo = new Wydawnictwo();
        wydawnictwo.setId(id);
        return wydawnictwo;
    }
}
