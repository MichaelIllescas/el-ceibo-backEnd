package com.imperialnet.el_ceibo.service;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    public ResponseCookie createAuthCookie(String jwt) {
        return ResponseCookie.from("authToken", jwt)
                .httpOnly(false)  // Permitir acceso desde el frontend
                .secure(true)    // Cambiar a true si usas HTTPS
                .path("/")        // Disponible en toda la aplicación
                .domain("elceibo.imperial-net.com")  // Dominio para compartir la cookie entre subdominios
                .sameSite("Lax")  // Política SameSite para evitar CSRF
                .maxAge(3600)     // Duración de la cookie en segundos (1 hora)
                .build();
    }
}
