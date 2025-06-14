package org.example.militarysystem.dto;

import lombok.*;
import org.example.militarysystem.model.UnitType;
import org.example.militarysystem.utils.mapUtils.MarkerType;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MapMarkerDTO {
    private Long id;
    private String name;
    private MarkerType type;
    private String description;
    private UnitType unitType;
    private String commander;
    private Integer estimatedPersonnel;
    private String positionStatus;
    private double latitude;
    private double longitude;
}
