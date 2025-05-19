package org.example.militarysystem.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EncryptedKeyDto {
    private String ciphertext;
    private String iv;
    private String salt;


}
