package fr.cyberholocampus.app.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the BuildingData entity.
 */
public class BuildingDataDTO implements Serializable {

    private Long id;

    private String description;

    private Long buildingId;

    private String buildingName;

    private Long dataDefinitionId;

    private String dataDefinitionLabel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Long getDataDefinitionId() {
        return dataDefinitionId;
    }

    public void setDataDefinitionId(Long buildingDataDefinitionId) {
        this.dataDefinitionId = buildingDataDefinitionId;
    }

    public String getDataDefinitionLabel() {
        return dataDefinitionLabel;
    }

    public void setDataDefinitionLabel(String buildingDataDefinitionLabel) {
        this.dataDefinitionLabel = buildingDataDefinitionLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuildingDataDTO buildingDataDTO = (BuildingDataDTO) o;
        if(buildingDataDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), buildingDataDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BuildingDataDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
