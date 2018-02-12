package fr.nicolasneto.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Lieu.
 */
@Entity
@Table(name = "lieu")
@Document(indexName = "lieu")
public class Lieu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "pays", nullable = false)
    private String pays;

    @NotNull
    @Column(name = "ville", nullable = false)
    private String ville;

    @Column(name = "departement")
    private String departement;

    @NotNull
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @NotNull
    @Column(name = "rue", nullable = false)
    private String rue;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Long numero;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPays() {
        return pays;
    }

    public Lieu pays(String pays) {
        this.pays = pays;
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public Lieu ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDepartement() {
        return departement;
    }

    public Lieu departement(String departement) {
        this.departement = departement;
        return this;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Lieu zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getRue() {
        return rue;
    }

    public Lieu rue(String rue) {
        this.rue = rue;
        return this;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public Long getNumero() {
        return numero;
    }

    public Lieu numero(Long numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
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
        Lieu lieu = (Lieu) o;
        if (lieu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lieu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lieu{" +
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
