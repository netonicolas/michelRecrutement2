package fr.nicolasneto.service.mapper;

import fr.nicolasneto.domain.*;
import fr.nicolasneto.service.dto.LieuDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lieu and its DTO LieuDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LieuMapper extends EntityMapper<LieuDTO, Lieu> {



    default Lieu fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lieu lieu = new Lieu();
        lieu.setId(id);
        return lieu;
    }
}
