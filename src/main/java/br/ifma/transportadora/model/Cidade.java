package br.ifma.transportadora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da cidade é obrigatório")
    private String nome;

    @NotBlank(message = "O estado é obrigatório")
    private String estado;

    @NotNull(message = "A taxa de entrega é obrigatória")
    private BigDecimal taxaEntrega;
}
