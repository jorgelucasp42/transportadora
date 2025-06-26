package br.ifma.transportadora.config;

import br.ifma.transportadora.model.Cidade;
import br.ifma.transportadora.model.Cliente;
import br.ifma.transportadora.model.Frete;
import br.ifma.transportadora.repository.CidadeRepository;
import br.ifma.transportadora.repository.ClienteRepository;
import br.ifma.transportadora.repository.FreteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(
            ClienteRepository clienteRepo,
            CidadeRepository cidadeRepo,
            FreteRepository freteRepo) {
        return args -> {

            // Criar e salvar clientes
            Cliente cliente1 = new Cliente(null, "Maria da Silva", "Rua A, 123", "981112233");
            Cliente cliente2 = new Cliente(null, "João Souza", "Av. B, 456", "982223344");
            cliente1 = clienteRepo.save(cliente1);
            cliente2 = clienteRepo.save(cliente2);

            // Criar e salvar cidades
            Cidade cidade1 = new Cidade(null, "São Luís", "MA", new BigDecimal("15.50"));
            Cidade cidade2 = new Cidade(null, "Imperatriz", "MA", new BigDecimal("12.00"));
            cidade1 = cidadeRepo.save(cidade1);
            cidade2 = cidadeRepo.save(cidade2);

            // Criar e salvar fretes
            Frete frete1 = new Frete(null, "F001", "Entrega rápida",
                    new BigDecimal("10.5"), new BigDecimal("30.00"), cliente1, cidade1);

            Frete frete2 = new Frete(null, "F002", "Entrega comum",
                    new BigDecimal("5.0"), new BigDecimal("20.00"), cliente2, cidade2);

            freteRepo.save(frete1);
            freteRepo.save(frete2);
        };
    }
}
