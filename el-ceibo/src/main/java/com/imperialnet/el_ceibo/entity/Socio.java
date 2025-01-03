package com.imperialnet.el_ceibo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "socios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres.")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío.")
    @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres.")
    @Column(nullable = false)
    private String apellido;

    @NotBlank(message = "El DNI no puede estar vacío.")
    @Pattern(regexp = "\\d{7,8}", message = "El DNI debe tener entre 7 y 8 dígitos.")
    @Column(nullable = false, unique = true)
    private String dni;

    @NotBlank(message = "La dirección no puede estar vacía.")
    @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres.")
    @Column(nullable = false)
    private String direccion;

    @NotBlank(message = "El teléfono no puede estar vacío.")
    @Column(nullable = false)
    private String telefono;

    @NotBlank(message = "El email no puede estar vacío.")
    @Email(message = "El email debe ser válido.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "La fecha de registro no puede ser nula.")
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSocio estado;

    public enum EstadoSocio {
        ACTIVO,
        ANULADO
    }

    public void anular() {
        this.estado = EstadoSocio.ANULADO;
        this.fechaBaja = LocalDate.now();
    }

    public void habilitar() {
        this.estado = EstadoSocio.ACTIVO;
        this.fechaBaja = null;
    }

    public boolean esActivo() {
        return this.estado == EstadoSocio.ACTIVO;
    }
}
