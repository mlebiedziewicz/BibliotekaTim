package pl.wat.magda.biblioteka.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Autor entity.
 */
public class AutorDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String imienazwisko;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImienazwisko() {
        return imienazwisko;
    }

    public void setImienazwisko(String imienazwisko) {
        this.imienazwisko = imienazwisko;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AutorDTO autorDTO = (AutorDTO) o;
        if (autorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), autorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutorDTO{" +
            "id=" + getId() +
            ", imienazwisko='" + getImienazwisko() + "'" +
            "}";
    }
}
