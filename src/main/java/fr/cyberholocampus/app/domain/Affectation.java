package fr.cyberholocampus.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Affectation.
 */
@Entity
@Table(name = "affectation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Affectation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private Notification notification;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Notification getNotification() {
        return notification;
    }

    public Affectation notification(Notification notification) {
        this.notification = notification;
        return this;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public User getUser() {
        return user;
    }

    public Affectation user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Affectation affectation = (Affectation) o;
        if (affectation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), affectation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Affectation{" +
            "id=" + getId() +
            "}";
    }
}
