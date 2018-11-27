package pl.wat.magda.biblioteka.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Autor.
 */
@Entity
@Table(name = "autor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "autor")
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "imienazwisko", length = 50, nullable = false)
    private String imienazwisko;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImienazwisko() {
        return imienazwisko;
    }

    public Autor imienazwisko(String imienazwisko) {
        this.imienazwisko = imienazwisko;
        return this;
    }

    public void setImienazwisko(String imienazwisko) {
        this.imienazwisko = imienazwisko;
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
        Autor autor = (Autor) o;
        if (autor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), autor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Autor{" +
            "id=" + getId() +
            ", imienazwisko='" + getImienazwisko() + "'" +
            "}";
    }
}
