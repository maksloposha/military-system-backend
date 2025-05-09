package org.example.militarysystem.chatEncryption;

import jakarta.servlet.http.HttpServletRequest;
import org.example.militarysystem.chatEncryption.model.PublicKeyEntity;
import org.example.militarysystem.chatEncryption.repository.PublicKeyRepository;
import org.example.militarysystem.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Service
public class PublicKeyService {


    private final PublicKeyRepository publicKeyRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public PublicKeyService(PublicKeyRepository publicKeyRepository, JwtTokenUtil jwtTokenUtil) {
        this.publicKeyRepository = publicKeyRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public void savePublicKey(String username, String jwk) {
        PublicKeyEntity entity = new PublicKeyEntity();
        entity.setUsername(username);
        entity.setPublicKeyJwk(jwk);
        publicKeyRepository.save(entity);
    }

    public Optional<String> getPublicKeyForUser(String username) {
        return publicKeyRepository.findById(username).map(PublicKeyEntity::getPublicKeyJwk);
    }

    public Optional<String> getMyPublicKey() {
        String username = jwtTokenUtil.getUsernameFromToken(getToken());
        return publicKeyRepository.findById(username).map(PublicKeyEntity::getPublicKeyJwk);
    }

    private String getToken() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();
        return jwtTokenUtil.getTokenFromCookie(request);
    }
}
