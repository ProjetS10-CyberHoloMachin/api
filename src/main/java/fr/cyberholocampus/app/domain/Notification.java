package fr.cyberholocampus.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import fr.cyberholocampus.app.domain.enumeration.NotificationType;

/**
 * A Notification.
 */
@Entity
@JsonInclude(Include.NON_DEFAULT)
@Table(name = "notification")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "notification")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private Instant date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private NotificationType type;

    @NotNull
    @Size(min = 3)
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "notification", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("notification")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NotificationData> infos = new HashSet<>();

    @OneToMany(mappedBy = "notification", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("notification")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Affectation> affectations = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("notifications")
    private Building building;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public Notification date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public NotificationType getType() {
        return type;
    }

    public Notification type(NotificationType type) {
        this.type = type;
        return this;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public Notification title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean isActive() {
        return active;
    }

    public Notification active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<NotificationData> getInfos() {
        return infos;
    }

    public Notification infos(Set<NotificationData> notificationData) {
        this.infos = notificationData;
        return this;
    }

    public Notification addInfos(NotificationData notificationData) {
        this.infos.add(notificationData);
        notificationData.setNotification(this);
        return this;
    }

    public Notification removeInfos(NotificationData notificationData) {
        this.infos.remove(notificationData);
        notificationData.setNotification(null);
        return this;
    }

    public void setInfos(Set<NotificationData> notificationData) {
        this.infos = notificationData;
    }

    public Set<Affectation> getAffectations() {
        return affectations;
    }

    public Notification affectations(Set<Affectation> affectations) {
        this.affectations = affectations;
        return this;
    }

    public Notification addAffectation(Affectation affectation) {
        this.affectations.add(affectation);
        affectation.setNotification(this);
        return this;
    }

    public Notification removeAffectation(Affectation affectation) {
        this.affectations.remove(affectation);
        affectation.setNotification(null);
        return this;
    }

    public void setAffectations(Set<Affectation> affectations) {
        this.affectations = affectations;
    }

    public Building getBuilding() {
        return building;
    }

    public Notification building(Building building) {
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
        Notification notification = (Notification) o;
        if (notification.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Notification{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", type='" + getType() + "'" +
            ", title='" + getTitle() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
