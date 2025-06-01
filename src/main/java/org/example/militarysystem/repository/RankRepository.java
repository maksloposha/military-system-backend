package org.example.militarysystem.repository;

import org.example.militarysystem.model.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank, Long> {
    void deleteByName(String name);
    Rank findByName(String name);
}
