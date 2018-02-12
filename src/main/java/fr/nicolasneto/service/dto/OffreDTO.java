package fr.nicolasneto.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import fr.nicolasneto.domain.enumeration.TypeOffre;

/**
 * A DTO for the Offre entity.
 */
public class OffreDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomOffre;

    @NotNull
    private String descriptionOffre;

    private Long salaireMin;

    private Long salaireMax;

    @NotNull
    private TypeOffre typeOffre;

    @NotNull
    private LocalDate dateOffres;

    private Long entrepriseId;

    private Long categorieOffreId;

    private Set<CompetenceDTO> competences = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomOffre() {
        return nomOffre;
    }

    public void setNomOffre(String nomOffre) {
        this.nomOffre = nomOffre;
    }

    public String getDescriptionOffre() {
        return descriptionOffre;
    }

    public void setDescriptionOffre(String descriptionOffre) {
        this.descriptionOffre = descriptionOffre;
    }

    public Long getSalaireMin() {
        return salaireMin;
    }

    public void setSalaireMin(Long salaireMin) {
        this.salaireMin = salaireMin;
    }

    public Long getSalaireMax() {
        return salaireMax;
    }

    public void setSalaireMax(Long salaireMax) {
        this.salaireMax = salaireMax;
    }

    public TypeOffre getTypeOffre() {
        return typeOffre;
    }

    public void setTypeOffre(TypeOffre typeOffre) {
        this.typeOffre = typeOffre;
    }

    public LocalDate getDateOffres() {
        return dateOffres;
    }

    public void setDateOffres(LocalDate dateOffres) {
        this.dateOffres = dateOffres;
    }

    public Long getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(Long entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    public Long getCategorieOffreId() {
        return categorieOffreId;
    }

    public void setCategorieOffreId(Long categorieOffreId) {
        this.categorieOffreId = categorieOffreId;
    }

    public Set<CompetenceDTO> getCompetences() {
        return competences;
    }

    public void setCompetences(Set<CompetenceDTO> competences) {
        this.competences = competences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OffreDTO offreDTO = (OffreDTO) o;
        if(offreDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offreDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OffreDTO{" +
            "id=" + getId() +
            ", nomOffre='" + getNomOffre() + "'" +
            ", descriptionOffre='" + getDescriptionOffre() + "'" +
            ", salaireMin=" + getSalaireMin() +
            ", salaireMax=" + getSalaireMax() +
            ", typeOffre='" + getTypeOffre() + "'" +
            ", dateOffres='" + getDateOffres() + "'" +
            "}";
    }
}
