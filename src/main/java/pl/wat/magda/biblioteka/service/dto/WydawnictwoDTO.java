package pl.wat.magda.biblioteka.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Wydawnictwo entity.
 */
public class WydawnictwoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String nazwa;

    @NotNull
    @Size(max = 50)
    private String adres;

    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WydawnictwoDTO wydawnictwoDTO = (WydawnictwoDTO) o;
        if (wydawnictwoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wydawnictwoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WydawnictwoDTO{" +
            "id=" + getId() +
            ", nazwa='" + getNazwa() + "'" +
            ", adres='" + getAdres() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
