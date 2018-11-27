package pl.wat.magda.biblioteka.service.mapper;

import pl.wat.magda.biblioteka.domain.*;
import pl.wat.magda.biblioteka.service.dto.KsiazkaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ksiazka and its DTO KsiazkaDTO.
 */
@Mapper(componentModel = "spring", uses = {AutorMapper.class, WydawnictwoMapper.class, GatunekMapper.class})
public interface KsiazkaMapper extends EntityMapper<KsiazkaDTO, Ksiazka> {

    @Mapping(source = "autor.id", target = "autorId")
    @Mapping(source = "autor.imienazwisko", target = "autorImienazwisko")
    @Mapping(source = "wydawnictwo.id", target = "wydawnictwoId")
    @Mapping(source = "wydawnictwo.nazwa", target = "wydawnictwoNazwa")
    @Mapping(source = "gatunek.id", target = "gatunekId")
    @Mapping(source = "gatunek.nazwa", target = "gatunekNazwa")
    KsiazkaDTO toDto(Ksiazka ksiazka);

    @Mapping(source = "autorId", target = "autor")
    @Mapping(source = "wydawnictwoId", target = "wydawnictwo")
    @Mapping(source = "gatunekId", target = "gatunek")
    Ksiazka toEntity(KsiazkaDTO ksiazkaDTO);

    default Ksiazka fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ksiazka ksiazka = new Ksiazka();
        ksiazka.setId(id);
        return ksiazka;
    }
}
