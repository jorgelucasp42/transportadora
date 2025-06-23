package br.ifma.transportadora.controller;

import br.ifma.transportadora.model.Frete;
import br.ifma.transportadora.model.Cidade;
import br.ifma.transportadora.repository.FreteRepository;
import br.ifma.transportadora.repository.ClienteRepository;
import br.ifma.transportadora.service.FreteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fretes")
public class FreteController {

    private final FreteService freteService;
    private final FreteRepository freteRepository;
    private final ClienteRepository clienteRepository;

    public FreteController(FreteService freteService, FreteRepository freteRepository, ClienteRepository clienteRepository) {
        this.freteService = freteService;
        this.freteRepository = freteRepository;
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    public ResponseEntity<Frete> cadastrar(@RequestBody Frete frete) {
        Frete novoFrete = freteService.cadastrarFrete(frete);
        return ResponseEntity.status(201).body(novoFrete);
    }

    @GetMapping
    public List<Frete> listarTodos() {
        return freteRepository.findAll();
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<Frete>> listarPorCliente(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> ResponseEntity.ok(freteRepository.findAllByClienteOrderByValorAsc(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/maior")
    public ResponseEntity<Frete> freteMaisCaro() {
        return freteService.freteMaisCaro()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/cidade-top")
    public ResponseEntity<Cidade> cidadeComMaisFretes() {
        return freteService.cidadeComMaisFretes()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}
