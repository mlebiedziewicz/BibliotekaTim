package pl.wat.magda.biblioteka.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Wypozyczone.
 */
@Entity
@Table(name = "wypozyczone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "wypozyczone")
public class Wypozyczone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "datawypozyczenia", nullable = false)
    private LocalDate datawypozyczenia;

    @Column(name = "dataoddania")
    private LocalDate dataoddania;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "wypozyczone_uzytkownik",
               joinColumns = @JoinColumn(name = "wypozyczones_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "uzytkowniks_id", referencedColumnName = "id"))
    private Set<User> uzytkowniks = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "wypozyczone_ksiazka",
               joinColumns = @JoinColumn(name = "wypozyczones_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ksiazkas_id", referencedColumnName = "id"))
    private Set<Ksiazka> ksiazkas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatawypozyczenia() {
        return datawypozyczenia;
    }

    public Wypozyczone datawypozyczenia(LocalDate datawypozyczenia) {
        this.datawypozyczenia = datawypozyczenia;
        return this;
    }

    public void setDatawypozyczenia(LocalDate datawypozyczenia) {
        this.datawypozyczenia = datawypozyczenia;
    }

    public LocalDate getDataoddania() {
        return dataoddania;
    }

    public Wypozyczone dataoddania(LocalDate dataoddania) {
        this.dataoddania = dataoddania;
        return this;
    }

    public void setDataoddania(LocalDate dataoddania) {
        this.dataoddania = dataoddania;
    }

    public Set<User> getUzytkowniks() {
        return uzytkowniks;
    }

    public Wypozyczone uzytkowniks(Set<User> users) {
        this.uzytkowniks = users;
        return this;
    }

    public Wypozyczone addUzytkownik(User user) {
        this.uzytkowniks.add(user);
        return this;
    }

    public Wypozyczone removeUzytkownik(User user) {
        this.uzytkowniks.remove(user);
        return this;
    }

    public void setUzytkowniks(Set<User> users) {
        this.uzytkowniks = users;
    }

    public Set<Ksiazka> getKsiazkas() {
        return ksiazkas;
    }

    public Wypozyczone ksiazkas(Set<Ksiazka> ksiazkas) {
        this.ksiazkas = ksiazkas;
        return this;
    }

    public Wypozyczone addKsiazka(Ksiazka ksiazka) {
        this.ksiazkas.add(ksiazka);
        return this;
    }

    public Wypozyczone removeKsiazka(Ksiazka ksiazka) {
        this.ksiazkas.remove(ksiazka);
        return this;
    }

    public void setKsiazkas(Set<Ksiazka> ksiazkas) {
        this.ksiazkas = ksiazkas;
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
        Wypozyczone wypozyczone = (Wypozyczone) o;
        if (wypozyczone.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wypozyczone.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Wypozyczone{" +
            "id=" + getId() +
            ", datawypozyczenia='" + getDatawypozyczenia() + "'" +
            ", dataoddania='" + getDataoddania() + "'" +
            "}";
    }
}
