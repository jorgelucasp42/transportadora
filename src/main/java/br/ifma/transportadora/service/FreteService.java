package br.ifma.transportadora.service;

import br.ifma.transportadora.model.Cidade;
import br.ifma.transportadora.model.Cliente;
import br.ifma.transportadora.model.Frete;
import br.ifma.transportadora.repository.CidadeRepository;
import br.ifma.transportadora.repository.ClienteRepository;
import br.ifma.transportadora.repository.FreteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FreteService {

    private final FreteRepository freteRepository;
    private final ClienteRepository clienteRepository;
    private final CidadeRepository cidadeRepository;

    private static final BigDecimal VALOR_FIXO = new BigDecimal("10.00");

    public FreteService(FreteRepository freteRepository, ClienteRepository clienteRepository, CidadeRepository cidadeRepository) {
        this.freteRepository = freteRepository;
        this.clienteRepository = clienteRepository;
        this.cidadeRepository = cidadeRepository;
    }

    @Transactional
    public Frete cadastrarFrete(Frete frete) {
        Cliente cliente = clienteRepository.findById(frete.getCliente().getId())
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado"));

        Cidade cidade = cidadeRepository.findById(frete.getCidade().getId())
                .orElseThrow(() -> new NoSuchElementException("Cidade não encontrada"));

        // Calcular valor do frete
        BigDecimal valor = frete.getPeso()
                .multiply(VALOR_FIXO)
                .add(cidade.getTaxaEntrega());

        frete.setCliente(cliente);
        frete.setCidade(cidade);
        frete.setValor(valor);

        return freteRepository.save(frete);
    }

    public Optional<Frete> freteMaisCaro() {
        return freteRepository.findAll().stream()
                .max(Comparator.comparing(Frete::getValor));
    }

    public Optional<Cidade> cidadeComMaisFretes() {
        List<Frete> fretes = freteRepository.findAll();

        return fretes.stream()
                .map(Frete::getCidade)
                .filter(cidade -> cidade != null)
                .collect(java.util.stream.Collectors.groupingBy(cidade -> cidade, java.util.stream.Collectors.counting()))
                .entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey);
    }
}
