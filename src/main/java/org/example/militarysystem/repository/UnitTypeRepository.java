package org.example.militarysystem.repository;

import org.example.militarysystem.model.UnitType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitTypeRepository extends JpaRepository<UnitType, Long> {


    UnitType findByName(String name);
}
