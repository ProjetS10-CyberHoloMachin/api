package fr.cyberholocampus.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BuildingData.
 */
@Entity
@JsonInclude(Include.NON_DEFAULT)
@Table(name = "building_data")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "buildingdata")
public class BuildingData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JsonView(View.Building.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JsonView(View.Building.class)
    @Size(min = 3)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @JsonView(View.Building.class)
    @Size(min = 3)
    @Column(name = "jhi_label", nullable = false)
    private String label;

    @ManyToOne(optional = false)
    @JsonView(View.Building.class)
    @JsonIgnoreProperties("data")
    @NotNull
    private Building building;

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

    public String getLabel() {
        return label;
    }

    public BuildingData label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
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
            ", label='" + getLabel() + "'" +
            "}";
    }
}
