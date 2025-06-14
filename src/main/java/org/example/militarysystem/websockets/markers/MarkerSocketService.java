package org.example.militarysystem.websockets.markers;

import org.example.militarysystem.dto.MapMarkerDTO;
import org.example.militarysystem.service.MapMarkerService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkerSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final MapMarkerService markerService;

    public MarkerSocketService(SimpMessagingTemplate messagingTemplate, MapMarkerService markerService) {
        this.messagingTemplate = messagingTemplate;
        this.markerService = markerService;
    }

    public void sendMarkers() {

        List<MapMarkerDTO> allMarkers = markerService.getAllMarkers();
        messagingTemplate.convertAndSend("/topic/markers", new MarkerEvent("SYNC_ALL", allMarkers));
    }
}
