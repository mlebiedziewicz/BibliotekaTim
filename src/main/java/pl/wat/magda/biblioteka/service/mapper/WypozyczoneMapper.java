package pl.wat.magda.biblioteka.service.mapper;

import pl.wat.magda.biblioteka.domain.*;
import pl.wat.magda.biblioteka.service.dto.WypozyczoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Wypozyczone and its DTO WypozyczoneDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, KsiazkaMapper.class})
public interface WypozyczoneMapper extends EntityMapper<WypozyczoneDTO, Wypozyczone> {



    default Wypozyczone fromId(Long id) {
        if (id == null) {
            return null;
        }
        Wypozyczone wypozyczone = new Wypozyczone();
        wypozyczone.setId(id);
        return wypozyczone;
    }
}
