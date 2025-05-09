package org.example.militarysystem.service;

import org.example.militarysystem.dto.ZoneDto;
import org.example.militarysystem.model.Zone;
import org.example.militarysystem.repository.ZoneRepository;
import org.example.militarysystem.utils.mapUtils.ZoneType;
import org.example.militarysystem.websockets.zones.ZoneSocketService;
import org.locationtech.jts.geom.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final GeometryFactory geometryFactory;
    private final ZoneSocketService zoneSocketService;

    public ZoneService(ZoneRepository zoneRepository, ZoneSocketService zoneSocketService) {
        this.zoneRepository = zoneRepository;
        this.zoneSocketService = zoneSocketService;
        this.geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    }

    public ZoneDto createZone(ZoneDto zoneDto) {
        Zone zone = new Zone();
        zone.setName(zoneDto.getName());
        zone.setType(ZoneType.valueOf(zoneDto.getType().name()));
        Coordinate[] coords = zoneDto.getCoordinates().stream()
                .map(pair -> new Coordinate(pair.get(0), pair.get(1)))
                .toArray(Coordinate[]::new);

        if (!coords[0].equals2D(coords[coords.length - 1])) {
            coords = Arrays.copyOf(coords, coords.length + 1);
            coords[coords.length - 1] = coords[0];
        }
        LinearRing ring = geometryFactory.createLinearRing(coords);
        Polygon polygon = geometryFactory.createPolygon(ring);
        zone.setArea(polygon);
        Zone saved = zoneRepository.save(zone);
        zoneDto.setId(saved.getId());
        zoneSocketService.sendNewZone(zoneDto);
        return zoneDto;
    }

    public List<ZoneDto> getAllZones() {
        return zoneRepository.findAll().stream()
                .map(zone -> {
                    List<List<Double>> coords = Arrays.stream(zone.getArea().getCoordinates())
                            .map(c -> List.of(c.x, c.y))
                            .toList();

                    ZoneDto dto = new ZoneDto();
                    dto.setId(zone.getId());
                    dto.setName(zone.getName());
                    dto.setType(zone.getType());
                    dto.setCoordinates(coords);
                    return dto;
                })
                .toList();
    }

    public void deleteZone(Long id) {
        Zone zone = zoneRepository.findById(id).orElseThrow(() -> new RuntimeException("Zone not found"));
        zoneRepository.delete(zone);
        zoneSocketService.sendDeletedZone(new ZoneDto(zone.getId(), zone.getName(), zone.getType(), null));
    }

    public ZoneDto updateZone(Long id, ZoneDto dto) {
        Zone zone = zoneRepository.findById(id).orElseThrow(() -> new RuntimeException("Zone not found"));
        zone.setName(dto.getName());
        zone.setType(ZoneType.valueOf(dto.getType().name()));
        Coordinate[] coords = dto.getCoordinates().stream()
                .map(pair -> new Coordinate(pair.get(0), pair.get(1)))
                .toArray(Coordinate[]::new);

        if (!coords[0].equals2D(coords[coords.length - 1])) {
            coords = Arrays.copyOf(coords, coords.length + 1);
            coords[coords.length - 1] = coords[0];
        }
        LinearRing ring = geometryFactory.createLinearRing(coords);
        Polygon polygon = geometryFactory.createPolygon(ring);
        zone.setArea(polygon);
        zoneRepository.save(zone);
        zoneSocketService.sendUpdatedZone(dto);
        return new ZoneDto(zone.getId(), zone.getName(), zone.getType(), dto.getCoordinates());
    }
}
