package br.ifma.transportadora.controller;

import br.ifma.transportadora.model.Cliente;
import br.ifma.transportadora.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Cadastro de cliente
    @PostMapping
    public ResponseEntity<Cliente> cadastrar(@RequestBody @Valid Cliente cliente) {
        Cliente salvo = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // Buscar todos os clientes
    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    // Buscar por telefone
    @GetMapping("/telefone/{telefone}")
    public ResponseEntity<Cliente> buscarPorTelefone(@PathVariable String telefone) {
        Optional<Cliente> cliente = clienteRepository.findByTelefone(telefone);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
