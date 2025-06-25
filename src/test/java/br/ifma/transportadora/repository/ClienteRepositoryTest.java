package br.ifma.transportadora.repository;

import br.ifma.transportadora.model.Cliente;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    ClienteRepository clienteRepository;

    @Test
    void deveSalvarClienteComSucesso() {
        Cliente cliente = new Cliente(null, "Maria", "Rua das Flores", "989898898");
        Cliente salvo = clienteRepository.save(cliente);
        assertNotNull(salvo.getId());
    }

    @Test
    void naoDeveSalvarClienteSemNome() {
        Cliente cliente = new Cliente(null, "", "Rua do Campo", "989898898");
        assertThrows(ConstraintViolationException.class, () -> {
            clienteRepository.saveAndFlush(cliente);
        });
    }

    @Test
    void naoDeveSalvarClienteSemEndereco() {
        Cliente cliente = new Cliente(null, "Carlos", "", "989898898");
        assertThrows(ConstraintViolationException.class, () -> {
            clienteRepository.saveAndFlush(cliente);
        });
    }

    @Test
    void deveBuscarClientePorTelefone() {
        Cliente cliente = new Cliente(null, "João", "Av. Central", "999999999");
        clienteRepository.save(cliente);

        Optional<Cliente> encontrado = clienteRepository.findByTelefone("999999999");
        assertTrue(encontrado.isPresent());
        assertEquals("João", encontrado.get().getNome());
    }
}
