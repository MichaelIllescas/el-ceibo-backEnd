package com.imperialnet.el_ceibo.controller;

import com.imperialnet.el_ceibo.dto.EstadoUsuarioDTO;
import com.imperialnet.el_ceibo.dto.UserDTO;
import com.imperialnet.el_ceibo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {


        private final UserService userService;

        // Crear un nuevo usuario
        @PostMapping
        public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
            UserDTO createdUser = userService.createUser(userDTO);
            return ResponseEntity.ok(createdUser);
        }

        // Obtener todos los usuarios
        @GetMapping("/getAllUsers")
        public ResponseEntity<List<UserDTO>> getAllUsers() {
            List<UserDTO> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        }

        // Obtener un usuario por ID
        @GetMapping("/{id}")
        public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
            UserDTO user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        }

        // Actualizar un usuario
        @PutMapping("/{id}")
        public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            return ResponseEntity.ok(updatedUser);
        }

        // Eliminar un usuario (deshabilitar)
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        // Anular un usuario
        @PutMapping("/{id}/anular")
        public ResponseEntity<Void> anularUsuario(@PathVariable Long id) {
            try {
                userService.anularUsuario(id);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }

        // Habilitar un socio
        @PutMapping("/{id}/habilitar")
        public ResponseEntity<Void> habilitarSocio(@PathVariable Long id) {
            try {
                userService.habilitarUsuario(id);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }

    // Obtener todos los usuarios
    @GetMapping("/estados-usuarios")
    public ResponseEntity<List<EstadoUsuarioDTO>> obtenerEstadosDeUsuarios(){
            return ResponseEntity.ok(userService.getEstadosSocios());
    }
}




