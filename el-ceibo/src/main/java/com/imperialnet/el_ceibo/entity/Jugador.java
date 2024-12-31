package com.imperialnet.el_ceibo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "jugadores")
@Data

@AllArgsConstructor
public class Jugador {

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

    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pago> pagos;

    @NotNull(message = "La categoría no puede ser nula.")
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    // Nuevo campo: Estado
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoJugador estado;

    // Constructor por defecto que establece el estado inicial como ACTIVO
    public Jugador() {
        this.estado = EstadoJugador.ACTIVO;
    }

    // Métodos auxiliares
    public void anular() {
        this.estado = EstadoJugador.ANULADO;
        this.fechaBaja = LocalDate.now(); // Registrar la fecha de baja
    }
    public void habilitar() {
        this.estado = EstadoJugador.ACTIVO;

    }

    public boolean esActivo() {
        return this.estado == EstadoJugador.ACTIVO;
    }
}
