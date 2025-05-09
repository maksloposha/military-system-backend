package org.example.militarysystem.repository;

import org.example.militarysystem.model.Zone;
import org.example.militarysystem.utils.mapUtils.ZoneType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    List<Zone> findByName(String name);
    List<Zone> findByType(ZoneType type);
}
