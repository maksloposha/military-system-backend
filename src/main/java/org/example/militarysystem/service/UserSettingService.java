package org.example.militarysystem.service;

import org.example.militarysystem.model.MapMarker;
import org.example.militarysystem.model.PositionStatus;
import org.example.militarysystem.model.Rank;
import org.example.militarysystem.model.UnitType;
import org.example.militarysystem.repository.MapMarkerRepository;
import org.example.militarysystem.repository.PositionStatusRepository;
import org.example.militarysystem.repository.RankRepository;
import org.example.militarysystem.repository.UnitTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSettingService {
    private final RankRepository rankRepository;
    private final UnitTypeRepository unitTypeRepository;
    private final PositionStatusRepository positionStatusRepository;
    private final MapMarkerRepository mapMarkerRepository;

    public UserSettingService(RankRepository rankRepository, UnitTypeRepository unitTypeRepository, PositionStatusRepository positionStatusRepository, MapMarkerRepository mapMarkerRepository) {
        this.rankRepository = rankRepository;
        this.unitTypeRepository = unitTypeRepository;
        this.positionStatusRepository = positionStatusRepository;
        this.mapMarkerRepository = mapMarkerRepository;
    }

    public List<String> getAllRanks() {
        return rankRepository.findAll().stream().map(Rank::getName).toList();
    }

    public List<UnitType> getAllUnitTypes() {
        return unitTypeRepository.findAll();
    }

    public void createRank(String rank) {
        Rank rankEntity = new Rank();
        rankEntity.setName(rank);
        rankRepository.save(rankEntity);
    }

    public void createUnitType(UnitType unitType) {
        UnitType unitTypeEntity = new UnitType();
        unitTypeEntity.setName(unitType.getName());
        unitTypeEntity.setSvgContent(unitType.getSvgContent());
        unitTypeRepository.save(unitTypeEntity);
    }

    public void deleteRank(String name) {
        Rank rank = rankRepository.findByName(name);
        rankRepository.deleteById(rank.getId());
    }

    public void deleteUnitType(Long id) {
        unitTypeRepository.deleteById(id);
    }

    public List<String> getAllPositionStatuses() {
        return positionStatusRepository.findAll().stream().map(PositionStatus::getName).toList();
    }

    public void createPositionStatus(String positionStatus) {
        PositionStatus positionStatusEntity = new PositionStatus();
        positionStatusEntity.setName(positionStatus);
        positionStatusRepository.save(positionStatusEntity);
    }

    public void deletePositionStatus(String name) {
        PositionStatus positionStatus = positionStatusRepository.findByName(name);
        if(positionStatus == null) {
            throw new IllegalArgumentException("Position status not found: " + name);
        }
        List<MapMarker> relatedMarkers = mapMarkerRepository.findByPositionStatus(positionStatus);
        for (MapMarker marker : relatedMarkers) {
            marker.setPositionStatus(null);
        }
        mapMarkerRepository.saveAll(relatedMarkers);
        positionStatusRepository.delete(positionStatus);
    }

}
