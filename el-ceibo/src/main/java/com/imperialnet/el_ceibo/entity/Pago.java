package com.imperialnet.el_ceibo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "La fecha de pago no puede ser nula.")
    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "La fecha de registro no puede ser nula.")
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;


    @NotNull(message = "El monto no puede ser nulo.")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que 0.")
    @Digits(integer = 10, fraction = 2, message = "El monto debe tener un formato válido (máximo 10 dígitos enteros y 2 decimales).")
    @Column(nullable = false)
    private BigDecimal monto;

    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres.")
    @Column(length = 255)
    private String descripcion;

    @NotNull(message = "La cuota no puede ser nula.")
    @ManyToOne
    @JoinColumn(name = "cuota_id", nullable = false)
    private Cuota cuota;

    @ManyToOne
    @JoinColumn(name = "jugador_id", nullable = true)
    private Jugador jugador;

   @ManyToOne
   @JoinColumn(name = "socio_id", nullable = true)
   private Socio socio;
   @ManyToOne
   @JoinColumn(name = "usuario_id", nullable = false)
   private User usuario;
}
