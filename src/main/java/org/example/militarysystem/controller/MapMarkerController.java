package org.example.militarysystem.controller;

import org.example.militarysystem.dto.MapMarkerDTO;
import org.example.militarysystem.service.MapMarkerService;
import org.example.militarysystem.websockets.markers.MarkerSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/markers")
public class MapMarkerController {
    private final MapMarkerService service;
    private final MarkerSocketService markerSocketService;

    @Autowired
    public MapMarkerController(MapMarkerService service, MarkerSocketService markerSocketService) {
        this.service = service;
        this.markerSocketService = markerSocketService;
    }

    @GetMapping
    public ResponseEntity<List<MapMarkerDTO>> getAllMarkers() {
        List<MapMarkerDTO> markers = service.getAllMarkers();
        return ResponseEntity.ok(markers);
    }

    @PostMapping
    public ResponseEntity<MapMarkerDTO> createMarker(@RequestBody MapMarkerDTO dto) {
        MapMarkerDTO marker = service.createMarker(dto);
        markerSocketService.sendMarkers(); // Notify WebSocket clients about the new marker
        return new ResponseEntity<>(marker, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarker(@PathVariable Long id) {
        service.deleteMarker(id);
        markerSocketService.sendMarkers(); // Notify WebSocket clients about the deletion
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MapMarkerDTO> updateMarker(@PathVariable Long id, @RequestBody MapMarkerDTO dto) {
        MapMarkerDTO updatedMarker = service.updateMarker(id, dto);
        markerSocketService.sendMarkers(); // Notify WebSocket clients about the update
        return ResponseEntity.ok(updatedMarker);
    }
}
