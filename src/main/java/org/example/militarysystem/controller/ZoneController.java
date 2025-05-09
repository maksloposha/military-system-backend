package org.example.militarysystem.controller;

import org.example.militarysystem.dto.ZoneDto;
import org.example.militarysystem.service.ZoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @PostMapping
    public ResponseEntity<ZoneDto> createZone(@RequestBody ZoneDto dto) {
        ZoneDto zone = zoneService.createZone(dto);
        return ResponseEntity.ok(zone);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateZone(@PathVariable Long id, @RequestBody ZoneDto dto) {
        ZoneDto updatedZone = zoneService.updateZone(id, dto);
        return ResponseEntity.ok(updatedZone);
    }

    @GetMapping
    public List<ZoneDto> getAllZones() {
        return zoneService.getAllZones();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
        return ResponseEntity.ok().build();
    }
}
