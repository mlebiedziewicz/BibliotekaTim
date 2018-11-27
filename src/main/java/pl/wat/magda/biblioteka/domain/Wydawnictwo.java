package pl.wat.magda.biblioteka.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Wydawnictwo.
 */
@Entity
@Table(name = "wydawnictwo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "wydawnictwo")
public class Wydawnictwo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nazwa", length = 50, nullable = false)
    private String nazwa;

    @NotNull
    @Size(max = 50)
    @Column(name = "adres", length = 50, nullable = false)
    private String adres;

    @Column(name = "email")
    private String email;

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

    public Wydawnictwo nazwa(String nazwa) {
        this.nazwa = nazwa;
        return this;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getAdres() {
        return adres;
    }

    public Wydawnictwo adres(String adres) {
        this.adres = adres;
        return this;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getEmail() {
        return email;
    }

    public Wydawnictwo email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
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
        Wydawnictwo wydawnictwo = (Wydawnictwo) o;
        if (wydawnictwo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wydawnictwo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Wydawnictwo{" +
            "id=" + getId() +
            ", nazwa='" + getNazwa() + "'" +
            ", adres='" + getAdres() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
