package fr.cyberholocampus.app.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BuildingDataDefinition entity.
 */
public class BuildingDataDefinitionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String label;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuildingDataDefinitionDTO buildingDataDefinitionDTO = (BuildingDataDefinitionDTO) o;
        if(buildingDataDefinitionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), buildingDataDefinitionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BuildingDataDefinitionDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
