package fr.cyberholocampus.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BuildingData.
 */
@Entity
@Table(name = "building_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BuildingData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Building building;

    @ManyToOne
    private BuildingDataDefinition dataDefinition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public BuildingData description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Building getBuilding() {
        return building;
    }

    public BuildingData building(Building building) {
        this.building = building;
        return this;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public BuildingDataDefinition getDataDefinition() {
        return dataDefinition;
    }

    public BuildingData dataDefinition(BuildingDataDefinition buildingDataDefinition) {
        this.dataDefinition = buildingDataDefinition;
        return this;
    }

    public void setDataDefinition(BuildingDataDefinition buildingDataDefinition) {
        this.dataDefinition = buildingDataDefinition;
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
        BuildingData buildingData = (BuildingData) o;
        if (buildingData.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), buildingData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BuildingData{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
