package fr.nicolasneto.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Competence entity.
 */
public class CompetenceDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomCompetence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCompetence() {
        return nomCompetence;
    }

    public void setNomCompetence(String nomCompetence) {
        this.nomCompetence = nomCompetence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompetenceDTO competenceDTO = (CompetenceDTO) o;
        if(competenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompetenceDTO{" +
            "id=" + getId() +
            ", nomCompetence='" + getNomCompetence() + "'" +
            "}";
    }
}
