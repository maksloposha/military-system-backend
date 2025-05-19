package org.example.militarysystem.repository;

import org.example.militarysystem.model.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank, Long> {
    // Custom query methods can be defined here if needed
    // For example, find by name or other attributes
    Rank findByName(String name);
}
