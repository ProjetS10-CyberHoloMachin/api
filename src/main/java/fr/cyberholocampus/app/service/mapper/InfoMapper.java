package fr.cyberholocampus.app.service.mapper;

import fr.cyberholocampus.app.domain.*;
import fr.cyberholocampus.app.service.dto.InfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Info and its DTO InfoDTO.
 */
@Mapper(componentModel = "spring", uses = {InfoDefinitionMapper.class, NotificationMapper.class})
public interface InfoMapper extends EntityMapper<InfoDTO, Info> {

    @Mapping(source = "definition.id", target = "definitionId")
    @Mapping(source = "definition.label", target = "definitionLabel")
    @Mapping(source = "notification.id", target = "notificationId")
    InfoDTO toDto(Info info);

    @Mapping(source = "definitionId", target = "definition")
    @Mapping(source = "notificationId", target = "notification")
    Info toEntity(InfoDTO infoDTO);

    default Info fromId(Long id) {
        if (id == null) {
            return null;
        }
        Info info = new Info();
        info.setId(id);
        return info;
    }
}
