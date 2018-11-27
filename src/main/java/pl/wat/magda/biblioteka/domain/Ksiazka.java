package pl.wat.magda.biblioteka.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Ksiazka.
 */
@Entity
@Table(name = "ksiazka")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ksiazka")
public class Ksiazka implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "tytul", length = 50, nullable = false)
    private String tytul;

    @Column(name = "tematyka")
    private String tematyka;

    @Lob
    @Column(name = "opis")
    private String opis;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Autor autor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Wydawnictwo wydawnictwo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Gatunek gatunek;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTytul() {
        return tytul;
    }

    public Ksiazka tytul(String tytul) {
        this.tytul = tytul;
        return this;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getTematyka() {
        return tematyka;
    }

    public Ksiazka tematyka(String tematyka) {
        this.tematyka = tematyka;
        return this;
    }

    public void setTematyka(String tematyka) {
        this.tematyka = tematyka;
    }

    public String getOpis() {
        return opis;
    }

    public Ksiazka opis(String opis) {
        this.opis = opis;
        return this;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Autor getAutor() {
        return autor;
    }

    public Ksiazka autor(Autor autor) {
        this.autor = autor;
        return this;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Wydawnictwo getWydawnictwo() {
        return wydawnictwo;
    }

    public Ksiazka wydawnictwo(Wydawnictwo wydawnictwo) {
        this.wydawnictwo = wydawnictwo;
        return this;
    }

    public void setWydawnictwo(Wydawnictwo wydawnictwo) {
        this.wydawnictwo = wydawnictwo;
    }

    public Gatunek getGatunek() {
        return gatunek;
    }

    public Ksiazka gatunek(Gatunek gatunek) {
        this.gatunek = gatunek;
        return this;
    }

    public void setGatunek(Gatunek gatunek) {
        this.gatunek = gatunek;
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
        Ksiazka ksiazka = (Ksiazka) o;
        if (ksiazka.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ksiazka.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ksiazka{" +
            "id=" + getId() +
            ", tytul='" + getTytul() + "'" +
            ", tematyka='" + getTematyka() + "'" +
            ", opis='" + getOpis() + "'" +
            "}";
    }
}
