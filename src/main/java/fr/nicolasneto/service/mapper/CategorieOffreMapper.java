package fr.nicolasneto.service.mapper;

import fr.nicolasneto.domain.*;
import fr.nicolasneto.service.dto.CategorieOffreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CategorieOffre and its DTO CategorieOffreDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategorieOffreMapper extends EntityMapper<CategorieOffreDTO, CategorieOffre> {



    default CategorieOffre fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategorieOffre categorieOffre = new CategorieOffre();
        categorieOffre.setId(id);
        return categorieOffre;
    }
}
