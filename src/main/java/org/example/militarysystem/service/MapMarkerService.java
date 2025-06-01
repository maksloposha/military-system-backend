package org.example.militarysystem.service;

import org.example.militarysystem.dto.MapMarkerDTO;
import org.example.militarysystem.model.MapMarker;
import org.example.militarysystem.repository.MapMarkerRepository;
import org.example.militarysystem.repository.PositionStatusRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapMarkerService {

    private final MapMarkerRepository repository;
    private final PositionStatusRepository positionStatusRepository;

    @Autowired
    public MapMarkerService(MapMarkerRepository repository, PositionStatusRepository positionStatusRepository) {
        this.repository = repository;
        this.positionStatusRepository = positionStatusRepository;
    }

    public List<MapMarkerDTO> getAllMarkers() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public MapMarkerDTO createMarker(MapMarkerDTO dto) {
        MapMarker mapMarker = toEntity(dto, new MapMarker());

        return toDTO(repository.save(mapMarker));
    }

    private MapMarker toEntity(MapMarkerDTO dto, MapMarker marker) {
        marker.setUnitType(dto.getUnitType());
        marker.setCommander(dto.getCommander());
        marker.setEstimatedPersonnel(dto.getEstimatedPersonnel());
        marker.setName(dto.getName());
        marker.setType(dto.getType());
        marker.setDescription(dto.getDescription());
        marker.setPositionStatus(positionStatusRepository.findByName(dto.getPositionStatus()));

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point point = geometryFactory.createPoint(new Coordinate(dto.getLongitude(), dto.getLatitude()));
        marker.setLocation(point);
        return marker;
    }

    private MapMarkerDTO toDTO(MapMarker marker) {
        MapMarkerDTO dto = new MapMarkerDTO();
        dto.setId(marker.getId());
        dto.setName(marker.getName());
        dto.setType(marker.getType());
        dto.setUnitType(marker.getUnitType());
        dto.setCommander(marker.getCommander());
        dto.setEstimatedPersonnel(marker.getEstimatedPersonnel());
        dto.setDescription(marker.getDescription());
        dto.setLatitude(marker.getLocation().getY());
        dto.setLongitude(marker.getLocation().getX());
        dto.setPositionStatus(marker.getPositionStatus() != null ? marker.getPositionStatus().getName() : null);
        return dto;
    }

    public void deleteMarker(Long id) {
        repository.deleteById(id);
    }

    public MapMarkerDTO updateMarker(Long id, MapMarkerDTO dto) {
        MapMarker marker = repository.findById(id).orElseThrow(() -> new RuntimeException("Marker not found"));
        toEntity(dto, marker);
        return toDTO(repository.save(marker));
    }
}
