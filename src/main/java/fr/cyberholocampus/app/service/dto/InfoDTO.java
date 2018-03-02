package fr.cyberholocampus.app.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Info entity.
 */
public class InfoDTO implements Serializable {

    private Long id;

    private String description;

    private Long definitionId;

    private String definitionLabel;

    private Long notificationId;

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

    public Long getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(Long infoDefinitionId) {
        this.definitionId = infoDefinitionId;
    }

    public String getDefinitionLabel() {
        return definitionLabel;
    }

    public void setDefinitionLabel(String infoDefinitionLabel) {
        this.definitionLabel = infoDefinitionLabel;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InfoDTO infoDTO = (InfoDTO) o;
        if(infoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), infoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InfoDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
