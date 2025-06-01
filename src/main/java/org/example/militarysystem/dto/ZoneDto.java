package org.example.militarysystem.dto;

import lombok.*;
import org.example.militarysystem.utils.mapUtils.ZoneType;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
@Setter
public class ZoneDto {
    private Long id;
    private String name;
    private ZoneType type;
    private List<List<Double>> coordinates;
}
