package com.imperialnet.el_ceibo.auth;

import com.imperialnet.el_ceibo.dto.UserDTO;
import com.imperialnet.el_ceibo.entity.Role;
import com.imperialnet.el_ceibo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;


    @PostMapping("/registrarUsuario")
    public ResponseEntity<AuthenticationResponse>registrarUsuario(
            @RequestBody RegisterRequest request ){
        return ResponseEntity.ok(authenticationService.registrarUsuario(request));
    }
//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> login(
//            @RequestBody AuthenticationRequest request
//    ){
//        return ResponseEntity.ok(authenticationService.login(request));
//
//    }


    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ) {
        // Llamar al servicio para manejar el login y obtener la respuesta de autenticación
        AuthenticationResponse authResponse = authenticationService.login(request);

        // Crear la cookie como una cookie de sesión (sin tiempo de vida definido)
        ResponseCookie cookie = ResponseCookie.from("authToken", authResponse.getJwt())
                .httpOnly(false)  // Solo accesible vía HTTP (evita JavaScript)
                .secure(false)   // Activa true si estás usando HTTPS
                .path("/")       // Disponible en toda la aplicación
                .sameSite("Lax") // Política SameSite para evitar CSRF
                .maxAge(3600)    // Duración de la cookie en segundos (1 hora)
                .build();

        // Agregar la cookie a la respuesta HTTP
        response.addHeader("Set-Cookie", cookie.toString());

        UserDTO userDto = userService.toDTO(authenticationService.getUserByEmail(request.getEmail()).get() );

        // Devolver el UserDTO en la respuesta
        return ResponseEntity.ok(userDto);
    }


    //elimina la cookie que tiene el token cuando se llama a este endpoint
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
            // Crear una cookie con tiempo de vida de 0 para eliminarla
            ResponseCookie cookie = ResponseCookie.from("authToken", "")
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(0) // Expira inmediatamente
                    .sameSite("Lax")
                    .build();

            // Agregar la cookie a la respuesta HTTP
            response.addHeader("Set-Cookie", cookie.toString());

            // Devolver una respuesta de logout exitoso
            return ResponseEntity.ok("Logout successful!");
        }


    @GetMapping("/roles")
    public ResponseEntity<List<String>> getRoles() {
        // Convertir los valores del enum a una lista de Strings
        List<String> roles = Arrays.stream(Role.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }



}
