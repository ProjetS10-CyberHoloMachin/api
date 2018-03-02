package fr.cyberholocampus.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

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
@Table(name = "notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private Instant date;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private NotificationType type;

    @NotNull
    @Size(min = 3)
    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(mappedBy = "notification")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Info> infos = new HashSet<>();

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

    public Set<Info> getInfos() {
        return infos;
    }

    public Notification infos(Set<Info> infos) {
        this.infos = infos;
        return this;
    }

    public Notification addInfos(Info info) {
        this.infos.add(info);
        info.setNotification(this);
        return this;
    }

    public Notification removeInfos(Info info) {
        this.infos.remove(info);
        info.setNotification(null);
        return this;
    }

    public void setInfos(Set<Info> infos) {
        this.infos = infos;
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
            "}";
    }
}
