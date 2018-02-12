package fr.nicolasneto.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Entreprise.
 */
@Entity
@Table(name = "entreprise")
@Document(indexName = "entreprise")
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "entreprise_name", nullable = false)
    private String entrepriseName;

    @NotNull
    @Column(name = "telephone_entreprise", nullable = false)
    private String telephoneEntreprise;

    @NotNull
    @Column(name = "siren", nullable = false)
    private Long siren;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Lieu lieuEntreprise;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntrepriseName() {
        return entrepriseName;
    }

    public Entreprise entrepriseName(String entrepriseName) {
        this.entrepriseName = entrepriseName;
        return this;
    }

    public void setEntrepriseName(String entrepriseName) {
        this.entrepriseName = entrepriseName;
    }

    public String getTelephoneEntreprise() {
        return telephoneEntreprise;
    }

    public Entreprise telephoneEntreprise(String telephoneEntreprise) {
        this.telephoneEntreprise = telephoneEntreprise;
        return this;
    }

    public void setTelephoneEntreprise(String telephoneEntreprise) {
        this.telephoneEntreprise = telephoneEntreprise;
    }

    public Long getSiren() {
        return siren;
    }

    public Entreprise siren(Long siren) {
        this.siren = siren;
        return this;
    }

    public void setSiren(Long siren) {
        this.siren = siren;
    }

    public Lieu getLieuEntreprise() {
        return lieuEntreprise;
    }

    public Entreprise lieuEntreprise(Lieu lieu) {
        this.lieuEntreprise = lieu;
        return this;
    }

    public void setLieuEntreprise(Lieu lieu) {
        this.lieuEntreprise = lieu;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entreprise entreprise = (Entreprise) o;
        if (entreprise.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entreprise.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Entreprise{" +
            "id=" + getId() +
            ", entrepriseName='" + getEntrepriseName() + "'" +
            ", telephoneEntreprise='" + getTelephoneEntreprise() + "'" +
            ", siren=" + getSiren() +
            "}";
    }
}
