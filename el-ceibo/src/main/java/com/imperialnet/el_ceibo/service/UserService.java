package com.imperialnet.el_ceibo.service;

import com.imperialnet.el_ceibo.configuration.JwtCookieAuthenticationFilter;
import com.imperialnet.el_ceibo.configuration.JwtService;
import com.imperialnet.el_ceibo.dto.EstadoUsuarioDTO;
import com.imperialnet.el_ceibo.dto.UserDTO;
import com.imperialnet.el_ceibo.entity.Role;
import com.imperialnet.el_ceibo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.imperialnet.el_ceibo.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtCookieAuthenticationFilter jwtCookieAuthenticationFilter;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    // Crear un nuevo usuario
    public UserDTO createUser(UserDTO userDTO) {
        User user = toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return toDTO(savedUser);
    }

    // Obtener todos los usuarios
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Obtener un usuario por ID
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return toDTO(user);
    }

    // Actualizar un usuario
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setFirstName(userDTO.getNombre());
        user.setLastName(userDTO.getApellido());
        user.setDni(userDTO.getDni());
        user.setTelefono(userDTO.getTelefono());
        user.setDireccion(userDTO.getDireccion());
        user.setEmail(userDTO.getEmail());
        user.setEnabled(userDTO.getEstado().equals("ACTIVO")?true:false);
        user.setRole(userDTO.getRol());

        User updatedUser = userRepository.save(user);
        return toDTO(updatedUser);
    }

    // Eliminar un usuario (cambio de estado en lugar de eliminación física)
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setEnabled(false); // Deshabilitar el usuario
        userRepository.save(user);
    }

    // Conversión de entidad a DTO
    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .nombre(user.getFirstName())
                .apellido(user.getLastName())
                .dni(user.getDni())
                .telefono(user.getTelefono())
                .direccion(user.getDireccion())
                .email(user.getEmail())
                .estado(user.isEnabled()?"ACTIVO":"INACTIVO")
                .rol(user.getRole())
                .build();
    }

    // Conversión de DTO a entidad
    private User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .firstName(userDTO.getNombre())
                .lastName(userDTO.getApellido())
                .dni(userDTO.getDni())
                .telefono(userDTO.getTelefono())
                .direccion(userDTO.getDireccion())
                .email(userDTO.getEmail())
                .enabled(userDTO.getEstado().equals("ACTIVO")?true:false)
                .role(userDTO.getRol().equals(Role.ADMIN.toString())?Role.ADMIN:Role.USER)
                .build();
    }

    public void anularUsuario(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.get().setEnabled(false);
        userRepository.save(user.get());
    }

    public void habilitarUsuario(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.get().setEnabled(true);
        userRepository.save(user.get());
    }

    public List<EstadoUsuarioDTO> getEstadosSocios() {
        return userRepository.findAll()
                .stream().map(this::toEstadoUsuarioDTO)
                .collect(Collectors.toList());
    }

    public EstadoUsuarioDTO toEstadoUsuarioDTO(User user){
        return EstadoUsuarioDTO.builder()
                .id(user.getId())
                .nombre(user.getFirstName())
                .apellido(user.getLastName())
                .dni(user.getDni())
                .email(user.getEmail())
                .estado(user.isEnabled()?"ACTIVO":"INACTIVO")
                .build();
    }


    public UserDTO getPerfil(HttpServletRequest request) {
        String token = jwtCookieAuthenticationFilter.extractTokenFromCookies(request);
        String username= jwtService.extractUserName(token);
        User user= userRepository.findByEmail(username).get();
        UserDTO userDTO= toDTO(user);
        return userDTO;
    }




    public boolean updatePassword(HttpServletRequest request, String currentPassword, String newPassword) {
        String token = jwtCookieAuthenticationFilter.extractTokenFromCookies(request);
        String username= jwtService.extractUserName(token);
        User user= userRepository.findByEmail(username).get();

        if (user == null) {
            throw new RuntimeException("Usuario no encontrado.");
        }

        // Verificar si la contraseña actual coincide
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return false; // La contraseña actual es incorrecta
        }

        // Actualizar la contraseña en la base de datos
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }
}
