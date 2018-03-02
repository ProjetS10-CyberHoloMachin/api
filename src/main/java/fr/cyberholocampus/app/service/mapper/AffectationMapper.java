package fr.cyberholocampus.app.service.mapper;

import fr.cyberholocampus.app.domain.*;
import fr.cyberholocampus.app.service.dto.AffectationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Affectation and its DTO AffectationDTO.
 */
@Mapper(componentModel = "spring", uses = {NotificationMapper.class, UserMapper.class})
public interface AffectationMapper extends EntityMapper<AffectationDTO, Affectation> {

    @Mapping(source = "notification.id", target = "notificationId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    AffectationDTO toDto(Affectation affectation);

    @Mapping(source = "notificationId", target = "notification")
    @Mapping(source = "userId", target = "user")
    Affectation toEntity(AffectationDTO affectationDTO);

    default Affectation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Affectation affectation = new Affectation();
        affectation.setId(id);
        return affectation;
    }
}
