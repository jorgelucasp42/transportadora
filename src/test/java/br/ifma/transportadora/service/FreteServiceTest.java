package br.ifma.transportadora.service;

import br.ifma.transportadora.model.Cidade;
import br.ifma.transportadora.model.Cliente;
import br.ifma.transportadora.model.Frete;
import br.ifma.transportadora.repository.CidadeRepository;
import br.ifma.transportadora.repository.ClienteRepository;
import br.ifma.transportadora.repository.FreteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FreteServiceTest {

    @Autowired
    FreteService freteService;

    @Autowired
    FreteRepository freteRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    CidadeRepository cidadeRepository;

    Cliente cliente;
    Cidade cidade;

    @BeforeEach
    void setup() {
        freteRepository.deleteAll();
        clienteRepository.deleteAll();
        cidadeRepository.deleteAll();

        cliente = clienteRepository.save(
                new Cliente(null, "Joana", "Rua do Sol, 123", "989898888")
        );
        cidade = cidadeRepository.save(
                new Cidade(null, "Caxias", "MA", new BigDecimal("15.00"))
        );
    }

    @Test
    void deveCadastrarFreteComValorCalculado() {
        Frete frete = new Frete();
        frete.setCodigo("FRTX");
        frete.setDescricao("Entrega expressa");
        frete.setPeso(new BigDecimal("3.0"));
        frete.setCliente(cliente);
        frete.setCidade(cidade);

        Frete salvo = freteService.cadastrarFrete(frete);

        BigDecimal valorEsperado = new BigDecimal("3.0").multiply(new BigDecimal("10.00")).add(new BigDecimal("15.00"));
        assertEquals(valorEsperado, salvo.getValor());
        assertNotNull(salvo.getId());
    }

    @Test
    void deveLancarExcecaoSeClienteInexistente() {
        Cliente fake = new Cliente();
        fake.setId(999L);

        Frete frete = new Frete(null, "FERR", "Desc", new BigDecimal("5"), null, fake, cidade);

        assertThrows(NoSuchElementException.class, () -> {
            freteService.cadastrarFrete(frete);
        });
    }

    @Test
    void deveLancarExcecaoSeCidadeInexistente() {
        Cidade fake = new Cidade();
        fake.setId(888L);

        Frete frete = new Frete(null, "FERR", "Desc", new BigDecimal("5"), null, cliente, fake);

        assertThrows(NoSuchElementException.class, () -> {
            freteService.cadastrarFrete(frete);
        });
    }

    @Test
    void deveRetornarFreteMaisCaro() {
        freteRepository.save(new Frete(null, "F1", "desc", new BigDecimal("5"), new BigDecimal("100"), cliente, cidade));
        freteRepository.save(new Frete(null, "F2", "desc", new BigDecimal("5"), new BigDecimal("120"), cliente, cidade));

        Frete resultado = freteService.freteMaisCaro().orElseThrow();
        assertEquals("F2", resultado.getCodigo());
    }

    @Test
    void deveRetornarCidadeComMaisFretes() {
        Cidade outraCidade = cidadeRepository.save(
                new Cidade(null, "Imperatriz", "MA", new BigDecimal("20"))
        );

        freteRepository.save(new Frete(null, "A", "desc", new BigDecimal("1"), new BigDecimal("10"), cliente, cidade));
        freteRepository.save(new Frete(null, "B", "desc", new BigDecimal("1"), new BigDecimal("10"), cliente, cidade));
        freteRepository.save(new Frete(null, "C", "desc", new BigDecimal("1"), new BigDecimal("10"), cliente, outraCidade));

        Cidade resultado = freteService.cidadeComMaisFretes().orElseThrow();
        assertEquals("Caxias", resultado.getNome());
    }
}
