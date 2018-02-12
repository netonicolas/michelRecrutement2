package fr.nicolasneto.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import fr.nicolasneto.domain.enumeration.TypeOffre;

/**
 * A Offre.
 */
@Entity
@Table(name = "offre")
@Document(indexName = "offre")
public class Offre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom_offre", nullable = false)
    private String nomOffre;

    @NotNull
    @Column(name = "description_offre", nullable = false)
    private String descriptionOffre;

    @Column(name = "salaire_min")
    private Long salaireMin;

    @Column(name = "salaire_max")
    private Long salaireMax;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_offre", nullable = false)
    private TypeOffre typeOffre;

    @NotNull
    @Column(name = "date_offres", nullable = false)
    private LocalDate dateOffres;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Entreprise entreprise;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private CategorieOffre categorieOffre;

    @ManyToMany
    @NotNull
    @JoinTable(name = "offre_competence",
               joinColumns = @JoinColumn(name="offres_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="competences_id", referencedColumnName="id"))
    private Set<Competence> competences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomOffre() {
        return nomOffre;
    }

    public Offre nomOffre(String nomOffre) {
        this.nomOffre = nomOffre;
        return this;
    }

    public void setNomOffre(String nomOffre) {
        this.nomOffre = nomOffre;
    }

    public String getDescriptionOffre() {
        return descriptionOffre;
    }

    public Offre descriptionOffre(String descriptionOffre) {
        this.descriptionOffre = descriptionOffre;
        return this;
    }

    public void setDescriptionOffre(String descriptionOffre) {
        this.descriptionOffre = descriptionOffre;
    }

    public Long getSalaireMin() {
        return salaireMin;
    }

    public Offre salaireMin(Long salaireMin) {
        this.salaireMin = salaireMin;
        return this;
    }

    public void setSalaireMin(Long salaireMin) {
        this.salaireMin = salaireMin;
    }

    public Long getSalaireMax() {
        return salaireMax;
    }

    public Offre salaireMax(Long salaireMax) {
        this.salaireMax = salaireMax;
        return this;
    }

    public void setSalaireMax(Long salaireMax) {
        this.salaireMax = salaireMax;
    }

    public TypeOffre getTypeOffre() {
        return typeOffre;
    }

    public Offre typeOffre(TypeOffre typeOffre) {
        this.typeOffre = typeOffre;
        return this;
    }

    public void setTypeOffre(TypeOffre typeOffre) {
        this.typeOffre = typeOffre;
    }

    public LocalDate getDateOffres() {
        return dateOffres;
    }

    public Offre dateOffres(LocalDate dateOffres) {
        this.dateOffres = dateOffres;
        return this;
    }

    public void setDateOffres(LocalDate dateOffres) {
        this.dateOffres = dateOffres;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public Offre entreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
        return this;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public CategorieOffre getCategorieOffre() {
        return categorieOffre;
    }

    public Offre categorieOffre(CategorieOffre categorieOffre) {
        this.categorieOffre = categorieOffre;
        return this;
    }

    public void setCategorieOffre(CategorieOffre categorieOffre) {
        this.categorieOffre = categorieOffre;
    }

    public Set<Competence> getCompetences() {
        return competences;
    }

    public Offre competences(Set<Competence> competences) {
        this.competences = competences;
        return this;
    }

    public Offre addCompetence(Competence competence) {
        this.competences.add(competence);
        competence.getCompetences().add(this);
        return this;
    }

    public Offre removeCompetence(Competence competence) {
        this.competences.remove(competence);
        competence.getCompetences().remove(this);
        return this;
    }

    public void setCompetences(Set<Competence> competences) {
        this.competences = competences;
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
        Offre offre = (Offre) o;
        if (offre.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Offre{" +
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
