package fr.nicolasneto.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CategorieOffre entity.
 */
public class CategorieOffreDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomCategorie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategorieOffreDTO categorieOffreDTO = (CategorieOffreDTO) o;
        if(categorieOffreDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categorieOffreDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategorieOffreDTO{" +
            "id=" + getId() +
            ", nomCategorie='" + getNomCategorie() + "'" +
            "}";
    }
}
