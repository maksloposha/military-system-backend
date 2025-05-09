package org.example.militarysystem.dto;

import lombok.*;
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
    private String unitType;
    private String commander;
    private Integer estimatedPersonnel;
    private double latitude;
    private double longitude;
}
