package pl.wat.magda.biblioteka.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Wypozyczone entity.
 */
public class WypozyczoneDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate datawypozyczenia;

    private LocalDate dataoddania;

    private Set<UserDTO> uzytkowniks = new HashSet<>();

    private Set<KsiazkaDTO> ksiazkas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatawypozyczenia() {
        return datawypozyczenia;
    }

    public void setDatawypozyczenia(LocalDate datawypozyczenia) {
        this.datawypozyczenia = datawypozyczenia;
    }

    public LocalDate getDataoddania() {
        return dataoddania;
    }

    public void setDataoddania(LocalDate dataoddania) {
        this.dataoddania = dataoddania;
    }

    public Set<UserDTO> getUzytkowniks() {
        return uzytkowniks;
    }

    public void setUzytkowniks(Set<UserDTO> users) {
        this.uzytkowniks = users;
    }

    public Set<KsiazkaDTO> getKsiazkas() {
        return ksiazkas;
    }

    public void setKsiazkas(Set<KsiazkaDTO> ksiazkas) {
        this.ksiazkas = ksiazkas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WypozyczoneDTO wypozyczoneDTO = (WypozyczoneDTO) o;
        if (wypozyczoneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wypozyczoneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WypozyczoneDTO{" +
            "id=" + getId() +
            ", datawypozyczenia='" + getDatawypozyczenia() + "'" +
            ", dataoddania='" + getDataoddania() + "'" +
            "}";
    }
}
