package org.example.militarysystem.repository;

import jakarta.transaction.Transactional;
import org.example.militarysystem.model.MapMarker;
import org.example.militarysystem.model.PositionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapMarkerRepository extends JpaRepository<MapMarker, Long> {
    @Transactional
    List<MapMarker> findByPositionStatus(PositionStatus positionStatus);
}
