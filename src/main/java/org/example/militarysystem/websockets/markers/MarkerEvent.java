package org.example.militarysystem.websockets.markers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.militarysystem.dto.MapMarkerDTO;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MarkerEvent {
    private String type;
    private List<MapMarkerDTO> markers;
}

