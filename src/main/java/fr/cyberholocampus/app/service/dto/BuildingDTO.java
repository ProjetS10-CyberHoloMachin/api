package fr.cyberholocampus.app.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Building entity.
 */
public class BuildingDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuildingDTO buildingDTO = (BuildingDTO) o;
        if(buildingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), buildingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BuildingDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
