package br.ifma.transportadora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Frete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O código do frete é obrigatório")
    private String codigo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "O peso é obrigatório")
    @Positive(message = "O peso deve ser positivo")
    private BigDecimal peso;

    @NotNull(message = "O valor do frete é obrigatório")
    @Positive(message = "O valor do frete deve ser positivo")
    private BigDecimal valor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;
}

