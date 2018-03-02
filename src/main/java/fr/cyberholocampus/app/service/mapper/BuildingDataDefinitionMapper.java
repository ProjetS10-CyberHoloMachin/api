package fr.cyberholocampus.app.service.mapper;

import fr.cyberholocampus.app.domain.*;
import fr.cyberholocampus.app.service.dto.BuildingDataDefinitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BuildingDataDefinition and its DTO BuildingDataDefinitionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BuildingDataDefinitionMapper extends EntityMapper<BuildingDataDefinitionDTO, BuildingDataDefinition> {



    default BuildingDataDefinition fromId(Long id) {
        if (id == null) {
            return null;
        }
        BuildingDataDefinition buildingDataDefinition = new BuildingDataDefinition();
        buildingDataDefinition.setId(id);
        return buildingDataDefinition;
    }
}
