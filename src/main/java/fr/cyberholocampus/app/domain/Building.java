package fr.cyberholocampus.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Building.
 */
@Entity
@Table(name = "building")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "building")
public class Building implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JsonView(View.Building.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JsonView(View.Building.class)
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "mapping")
    private byte[] mapping;

    @Column(name = "mapping_content_type")
    private String mappingContentType;

    @OneToMany(mappedBy = "building", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("building")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonView(View.Building.class)
    private Set<BuildingData> data = new HashSet<>();

    @OneToMany(mappedBy = "building", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("building")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notification> notifications = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Building name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getMapping() {
        return mapping;
    }

    public Building mapping(byte[] mapping) {
        this.mapping = mapping;
        return this;
    }

    public void setMapping(byte[] mapping) {
        this.mapping = mapping;
    }

    public String getMappingContentType() {
        return mappingContentType;
    }

    public Building mappingContentType(String mappingContentType) {
        this.mappingContentType = mappingContentType;
        return this;
    }

    public void setMappingContentType(String mappingContentType) {
        this.mappingContentType = mappingContentType;
    }

    public Set<BuildingData> getData() {
        return data;
    }

    public Building data(Set<BuildingData> buildingData) {
        this.data = buildingData;
        return this;
    }

    public Building addData(BuildingData buildingData) {
        this.data.add(buildingData);
        buildingData.setBuilding(this);
        return this;
    }

    public Building removeData(BuildingData buildingData) {
        this.data.remove(buildingData);
        buildingData.setBuilding(null);
        return this;
    }

    public void setData(Set<BuildingData> buildingData) {
        this.data = buildingData;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public Building notifications(Set<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }

    public Building addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setBuilding(this);
        return this;
    }

    public Building removeNotification(Notification notification) {
        this.notifications.remove(notification);
        notification.setBuilding(null);
        return this;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
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
        Building building = (Building) o;
        if (building.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), building.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Building{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", mapping='" + getMapping() + "'" +
            ", mappingContentType='" + getMappingContentType() + "'" +
            "}";
    }
}
