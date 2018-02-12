package fr.nicolasneto.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Entreprise entity.
 */
public class EntrepriseDTO implements Serializable {

    private Long id;

    @NotNull
    private String entrepriseName;

    @NotNull
    private String telephoneEntreprise;

    @NotNull
    private Long siren;

    private Long lieuEntrepriseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntrepriseName() {
        return entrepriseName;
    }

    public void setEntrepriseName(String entrepriseName) {
        this.entrepriseName = entrepriseName;
    }

    public String getTelephoneEntreprise() {
        return telephoneEntreprise;
    }

    public void setTelephoneEntreprise(String telephoneEntreprise) {
        this.telephoneEntreprise = telephoneEntreprise;
    }

    public Long getSiren() {
        return siren;
    }

    public void setSiren(Long siren) {
        this.siren = siren;
    }

    public Long getLieuEntrepriseId() {
        return lieuEntrepriseId;
    }

    public void setLieuEntrepriseId(Long lieuId) {
        this.lieuEntrepriseId = lieuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntrepriseDTO entrepriseDTO = (EntrepriseDTO) o;
        if(entrepriseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entrepriseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntrepriseDTO{" +
            "id=" + getId() +
            ", entrepriseName='" + getEntrepriseName() + "'" +
            ", telephoneEntreprise='" + getTelephoneEntreprise() + "'" +
            ", siren=" + getSiren() +
            "}";
    }
}
