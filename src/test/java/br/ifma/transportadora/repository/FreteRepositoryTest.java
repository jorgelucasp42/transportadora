package br.ifma.transportadora.repository;

import br.ifma.transportadora.model.Cidade;
import br.ifma.transportadora.model.Cliente;
import br.ifma.transportadora.model.Frete;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FreteRepositoryTest {

    @Autowired
    FreteRepository freteRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    CidadeRepository cidadeRepository;

    @Test
    void deveBuscarFretesOrdenadosPorValor() {
        Cliente cliente = clienteRepository.save(new Cliente(null, "João", "988887777"));
        Cidade cidade = cidadeRepository.save(new Cidade(null, "São Luís", "MA", new BigDecimal("10.00")));

        freteRepository.save(new Frete(null, "F01", "Frete barato", new BigDecimal("5"), new BigDecimal("60.00"), cliente, cidade));
        freteRepository.save(new Frete(null, "F02", "Frete médio", new BigDecimal("5"), new BigDecimal("70.00"), cliente, cidade));
        freteRepository.save(new Frete(null, "F03", "Frete caro", new BigDecimal("5"), new BigDecimal("80.00"), cliente, cidade));

        List<Frete> lista = freteRepository.findAllByClienteOrderByValorAsc(cliente);

        assertEquals(3, lista.size());
        assertEquals("F01", lista.get(0).getCodigo());
        assertEquals("F03", lista.get(2).getCodigo());
    }
}
