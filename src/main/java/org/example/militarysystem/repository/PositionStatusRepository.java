package org.example.militarysystem.repository;

import org.example.militarysystem.model.PositionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionStatusRepository extends JpaRepository<PositionStatus, Long> {

    PositionStatus findByName(String name);
}
