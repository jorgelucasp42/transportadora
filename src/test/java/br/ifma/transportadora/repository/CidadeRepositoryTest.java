package br.ifma.transportadora.repository;

import br.ifma.transportadora.model.Cidade;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CidadeRepositoryTest {

    @Autowired
    CidadeRepository cidadeRepository;

    @Test
    void deveSalvarCidadeComSucesso() {
        Cidade cidade = new Cidade(null, "São Luís", "MA", new BigDecimal("12.50"));
        Cidade salva = cidadeRepository.save(cidade);
        assertNotNull(salva.getId());
    }

    @Test
    void deveBuscarCidadePorNome() {
        cidadeRepository.save(new Cidade(null, "Imperatriz", "MA", new BigDecimal("10.00")));

        Optional<Cidade> resultado = cidadeRepository.findByNome("Imperatriz");
        assertTrue(resultado.isPresent());
        assertEquals("MA", resultado.get().getEstado());
    }

    @Test
    void naoDeveSalvarCidadeSemEstado() {
        Cidade cidade = new Cidade(null, "Timon", "", new BigDecimal("8.00"));
        assertThrows(ConstraintViolationException.class, () -> {
            cidadeRepository.saveAndFlush(cidade);
        });
    }
}
