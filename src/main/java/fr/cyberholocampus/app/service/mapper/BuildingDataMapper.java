package fr.cyberholocampus.app.service.mapper;

import fr.cyberholocampus.app.domain.*;
import fr.cyberholocampus.app.service.dto.BuildingDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BuildingData and its DTO BuildingDataDTO.
 */
@Mapper(componentModel = "spring", uses = {BuildingMapper.class, BuildingDataDefinitionMapper.class})
public interface BuildingDataMapper extends EntityMapper<BuildingDataDTO, BuildingData> {

    @Mapping(source = "building.id", target = "buildingId")
    @Mapping(source = "building.name", target = "buildingName")
    @Mapping(source = "dataDefinition.id", target = "dataDefinitionId")
    @Mapping(source = "dataDefinition.label", target = "dataDefinitionLabel")
    BuildingDataDTO toDto(BuildingData buildingData);

    @Mapping(source = "buildingId", target = "building")
    @Mapping(source = "dataDefinitionId", target = "dataDefinition")
    BuildingData toEntity(BuildingDataDTO buildingDataDTO);

    default BuildingData fromId(Long id) {
        if (id == null) {
            return null;
        }
        BuildingData buildingData = new BuildingData();
        buildingData.setId(id);
        return buildingData;
    }
}
