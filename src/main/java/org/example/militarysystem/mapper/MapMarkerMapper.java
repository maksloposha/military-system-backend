package org.example.militarysystem.mapper;

import org.example.militarysystem.dto.MapMarkerDTO;
import org.example.militarysystem.model.MapMarker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapMarkerMapper {
    MapMarkerDTO toDto(MapMarker marker);
    MapMarker toEntity(MapMarkerDTO dto);
}
