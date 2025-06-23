package br.ifma.transportadora.repository;

import br.ifma.transportadora.model.Frete;
import br.ifma.transportadora.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreteRepository extends JpaRepository<Frete, Long> {
    List<Frete> findAllByClienteOrderByValorAsc(Cliente cliente);
}
