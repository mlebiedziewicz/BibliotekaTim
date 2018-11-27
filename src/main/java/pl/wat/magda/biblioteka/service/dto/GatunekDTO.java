package pl.wat.magda.biblioteka.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Gatunek entity.
 */
public class GatunekDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String nazwa;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GatunekDTO gatunekDTO = (GatunekDTO) o;
        if (gatunekDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gatunekDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GatunekDTO{" +
            "id=" + getId() +
            ", nazwa='" + getNazwa() + "'" +
            "}";
    }
}
