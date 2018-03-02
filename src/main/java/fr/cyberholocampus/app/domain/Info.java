package fr.cyberholocampus.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Info.
 */
@Entity
@Table(name = "info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Info implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private InfoDefinition definition;

    @ManyToOne
    private Notification notification;

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

    public Info description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InfoDefinition getDefinition() {
        return definition;
    }

    public Info definition(InfoDefinition infoDefinition) {
        this.definition = infoDefinition;
        return this;
    }

    public void setDefinition(InfoDefinition infoDefinition) {
        this.definition = infoDefinition;
    }

    public Notification getNotification() {
        return notification;
    }

    public Info notification(Notification notification) {
        this.notification = notification;
        return this;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
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
        Info info = (Info) o;
        if (info.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), info.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Info{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
