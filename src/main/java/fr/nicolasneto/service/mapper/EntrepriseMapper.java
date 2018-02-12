package fr.nicolasneto.service.mapper;

import fr.nicolasneto.domain.*;
import fr.nicolasneto.service.dto.EntrepriseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Entreprise and its DTO EntrepriseDTO.
 */
@Mapper(componentModel = "spring", uses = {LieuMapper.class})
public interface EntrepriseMapper extends EntityMapper<EntrepriseDTO, Entreprise> {

    @Mapping(source = "lieuEntreprise.id", target = "lieuEntrepriseId")
    EntrepriseDTO toDto(Entreprise entreprise);

    @Mapping(source = "lieuEntrepriseId", target = "lieuEntreprise")
    Entreprise toEntity(EntrepriseDTO entrepriseDTO);

    default Entreprise fromId(Long id) {
        if (id == null) {
            return null;
        }
        Entreprise entreprise = new Entreprise();
        entreprise.setId(id);
        return entreprise;
    }
}
