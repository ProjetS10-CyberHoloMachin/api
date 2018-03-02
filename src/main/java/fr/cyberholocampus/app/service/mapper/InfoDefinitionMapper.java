package fr.cyberholocampus.app.service.mapper;

import fr.cyberholocampus.app.domain.*;
import fr.cyberholocampus.app.service.dto.InfoDefinitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InfoDefinition and its DTO InfoDefinitionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InfoDefinitionMapper extends EntityMapper<InfoDefinitionDTO, InfoDefinition> {



    default InfoDefinition fromId(Long id) {
        if (id == null) {
            return null;
        }
        InfoDefinition infoDefinition = new InfoDefinition();
        infoDefinition.setId(id);
        return infoDefinition;
    }
}
