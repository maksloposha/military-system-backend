package org.example.militarysystem.service;

import org.example.militarysystem.model.Rank;
import org.example.militarysystem.model.UnitType;
import org.example.militarysystem.repository.RankRepository;
import org.example.militarysystem.repository.UnitTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSettingService {
    private final RankRepository rankRepository;
    private final UnitTypeRepository unitTypeRepository;

    public UserSettingService(RankRepository rankRepository, UnitTypeRepository unitTypeRepository) {
        this.rankRepository = rankRepository;
        this.unitTypeRepository = unitTypeRepository;
    }

    public List<String> getAllRanks() {
        return rankRepository.findAll().stream().map(Rank::getName).toList();
    }

    public List<String> getAllUnitTypes() {
        return unitTypeRepository.findAll().stream().map(UnitType::getName).toList();
    }

    public void createRank(String rank) {
        Rank rankEntity = new Rank();
        rankEntity.setName(rank);
        rankRepository.save(rankEntity);
    }

    public void createUnitType(String unitType) {
        UnitType unitTypeEntity = new UnitType();
        unitTypeEntity.setName(unitType);
        unitTypeRepository.save(unitTypeEntity);
    }

    public void deleteRank(Long id) {
        rankRepository.deleteById(id);
    }

    public void deleteUnitType(Long id) {
        unitTypeRepository.deleteById(id);
    }

}
