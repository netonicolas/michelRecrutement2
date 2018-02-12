package fr.nicolasneto.service.mapper;

import fr.nicolasneto.domain.*;
import fr.nicolasneto.service.dto.CompetenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Competence and its DTO CompetenceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompetenceMapper extends EntityMapper<CompetenceDTO, Competence> {


    @Mapping(target = "competences", ignore = true)
    Competence toEntity(CompetenceDTO competenceDTO);

    default Competence fromId(Long id) {
        if (id == null) {
            return null;
        }
        Competence competence = new Competence();
        competence.setId(id);
        return competence;
    }
}
