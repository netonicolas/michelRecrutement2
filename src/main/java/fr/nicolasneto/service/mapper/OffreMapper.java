package fr.nicolasneto.service.mapper;

import fr.nicolasneto.domain.*;
import fr.nicolasneto.service.dto.OffreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Offre and its DTO OffreDTO.
 */
@Mapper(componentModel = "spring", uses = {EntrepriseMapper.class, CategorieOffreMapper.class, CompetenceMapper.class})
public interface OffreMapper extends EntityMapper<OffreDTO, Offre> {

    @Mapping(source = "entreprise.id", target = "entrepriseId")
    @Mapping(source = "categorieOffre.id", target = "categorieOffreId")
    OffreDTO toDto(Offre offre);

    @Mapping(source = "entrepriseId", target = "entreprise")
    @Mapping(source = "categorieOffreId", target = "categorieOffre")
    Offre toEntity(OffreDTO offreDTO);

    default Offre fromId(Long id) {
        if (id == null) {
            return null;
        }
        Offre offre = new Offre();
        offre.setId(id);
        return offre;
    }
}
