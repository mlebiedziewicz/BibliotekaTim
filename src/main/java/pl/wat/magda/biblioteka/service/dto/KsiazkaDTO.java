package pl.wat.magda.biblioteka.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Ksiazka entity.
 */
public class KsiazkaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String tytul;

    private String tematyka;

    @Lob
    private String opis;

    private Long autorId;

    private String autorImienazwisko;

    private Long wydawnictwoId;

    private String wydawnictwoNazwa;

    private Long gatunekId;

    private String gatunekNazwa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getTematyka() {
        return tematyka;
    }

    public void setTematyka(String tematyka) {
        this.tematyka = tematyka;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    public String getAutorImienazwisko() {
        return autorImienazwisko;
    }

    public void setAutorImienazwisko(String autorImienazwisko) {
        this.autorImienazwisko = autorImienazwisko;
    }

    public Long getWydawnictwoId() {
        return wydawnictwoId;
    }

    public void setWydawnictwoId(Long wydawnictwoId) {
        this.wydawnictwoId = wydawnictwoId;
    }

    public String getWydawnictwoNazwa() {
        return wydawnictwoNazwa;
    }

    public void setWydawnictwoNazwa(String wydawnictwoNazwa) {
        this.wydawnictwoNazwa = wydawnictwoNazwa;
    }

    public Long getGatunekId() {
        return gatunekId;
    }

    public void setGatunekId(Long gatunekId) {
        this.gatunekId = gatunekId;
    }

    public String getGatunekNazwa() {
        return gatunekNazwa;
    }

    public void setGatunekNazwa(String gatunekNazwa) {
        this.gatunekNazwa = gatunekNazwa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KsiazkaDTO ksiazkaDTO = (KsiazkaDTO) o;
        if (ksiazkaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ksiazkaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KsiazkaDTO{" +
            "id=" + getId() +
            ", tytul='" + getTytul() + "'" +
            ", tematyka='" + getTematyka() + "'" +
            ", opis='" + getOpis() + "'" +
            ", autor=" + getAutorId() +
            ", autor='" + getAutorImienazwisko() + "'" +
            ", wydawnictwo=" + getWydawnictwoId() +
            ", wydawnictwo='" + getWydawnictwoNazwa() + "'" +
            ", gatunek=" + getGatunekId() +
            ", gatunek='" + getGatunekNazwa() + "'" +
            "}";
    }
}
