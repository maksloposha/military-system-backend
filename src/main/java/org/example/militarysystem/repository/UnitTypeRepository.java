package org.example.militarysystem.repository;

import org.example.militarysystem.model.UnitType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitTypeRepository extends JpaRepository<UnitType, Long> {
    // Custom query methods can be defined here if needed
    // For example, find by name or other attributes
    UnitType findByName(String name);
}
