package br.ifma.transportadora.controller;

import br.ifma.transportadora.model.Cidade;
import br.ifma.transportadora.repository.CidadeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private final CidadeRepository cidadeRepository;

    public CidadeController(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    // Cadastro de cidade
    @PostMapping
    public ResponseEntity<Cidade> cadastrar(@RequestBody @Valid Cidade cidade) {
        Cidade salva = cidadeRepository.save(cidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    // Listar todas as cidades
    @GetMapping
    public List<Cidade> listarTodas() {
        return cidadeRepository.findAll();
    }

    // Buscar por nome
    @GetMapping("/nome/{nome}")
    public ResponseEntity<Cidade> buscarPorNome(@PathVariable String nome) {
        Optional<Cidade> cidade = cidadeRepository.findByNome(nome);
        return cidade.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
