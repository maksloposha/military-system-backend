package org.example.militarysystem.dto;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String username;
    private String email;
    private String password;
    private String rank;
    private String unit;
}
