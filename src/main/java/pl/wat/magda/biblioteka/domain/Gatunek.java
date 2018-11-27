package pl.wat.magda.biblioteka.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Gatunek.
 */
@Entity
@Table(name = "gatunek")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "gatunek")
public class Gatunek implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nazwa", length = 50, nullable = false)
    private String nazwa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public Gatunek nazwa(String nazwa) {
        this.nazwa = nazwa;
        return this;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
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
        Gatunek gatunek = (Gatunek) o;
        if (gatunek.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gatunek.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Gatunek{" +
            "id=" + getId() +
            ", nazwa='" + getNazwa() + "'" +
            "}";
    }
}
