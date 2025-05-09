package org.example.militarysystem.repository;

import org.example.militarysystem.model.MapMarker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapMarkerRepository extends JpaRepository<MapMarker, Long> {
}
