package fr.nicolasneto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Competence.
 */
@Entity
@Table(name = "competence")
@Document(indexName = "competence")
public class Competence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom_competence", nullable = false)
    private String nomCompetence;

    @ManyToMany(mappedBy = "competences")
    @JsonIgnore
    private Set<Offre> competences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCompetence() {
        return nomCompetence;
    }

    public Competence nomCompetence(String nomCompetence) {
        this.nomCompetence = nomCompetence;
        return this;
    }

    public void setNomCompetence(String nomCompetence) {
        this.nomCompetence = nomCompetence;
    }

    public Set<Offre> getCompetences() {
        return competences;
    }

    public Competence competences(Set<Offre> offres) {
        this.competences = offres;
        return this;
    }

    public Competence addCompetences(Offre offre) {
        this.competences.add(offre);
        offre.getCompetences().add(this);
        return this;
    }

    public Competence removeCompetences(Offre offre) {
        this.competences.remove(offre);
        offre.getCompetences().remove(this);
        return this;
    }

    public void setCompetences(Set<Offre> offres) {
        this.competences = offres;
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
        Competence competence = (Competence) o;
        if (competence.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competence.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Competence{" +
            "id=" + getId() +
            ", nomCompetence='" + getNomCompetence() + "'" +
            "}";
    }
}
