package fr.nicolasneto.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Lieu entity.
 */
public class LieuDTO implements Serializable {

    private Long id;

    @NotNull
    private String pays;

    @NotNull
    private String ville;

    private String departement;

    @NotNull
    private String zipCode;

    @NotNull
    private String rue;

    @NotNull
    private Long numero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LieuDTO lieuDTO = (LieuDTO) o;
        if(lieuDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lieuDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LieuDTO{" +
            "id=" + getId() +
            ", pays='" + getPays() + "'" +
            ", ville='" + getVille() + "'" +
            ", departement='" + getDepartement() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", rue='" + getRue() + "'" +
            ", numero=" + getNumero() +
            "}";
    }
}
