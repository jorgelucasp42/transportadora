package br.ifma.transportadora.controller;

import br.ifma.transportadora.model.Cidade;
import br.ifma.transportadora.model.Cliente;
import br.ifma.transportadora.model.Frete;
import br.ifma.transportadora.repository.CidadeRepository;
import br.ifma.transportadora.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FreteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;
    private Cidade cidade;

    @BeforeEach
    void setup() {
        cidadeRepository.deleteAll();
        clienteRepository.deleteAll();

        cliente = clienteRepository.save(new Cliente(null, "Carlos", "Rua do Sol, 123", "999888777"));
        cidade = cidadeRepository.save(new Cidade(null, "Timon", "MA", new BigDecimal("25.00")));
    }

    @Test
    void deveCadastrarFreteComSucesso() throws Exception {
        Frete frete = new Frete();
        frete.setCodigo("ABC123");
        frete.setDescricao("Teste de frete");
        frete.setPeso(new BigDecimal("10.0"));
        frete.setCliente(cliente);
        frete.setCidade(cidade);

        mockMvc.perform(post("/fretes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(frete)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigo").value("ABC123"));
    }

    @Test
    void deveRetornarFretesDoCliente() throws Exception {
        // Primeiro, cadastrar um frete manualmente
        Frete frete = new Frete(null, "COD1", "Teste", new BigDecimal("2.0"), BigDecimal.ZERO, cliente, cidade);
        mockMvc.perform(post("/fretes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(frete)))
                .andExpect(status().isCreated());

        // Agora testar GET por cliente
        mockMvc.perform(get("/fretes/cliente/" + cliente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void deveRetornarErroSeClienteNaoExiste() throws Exception {
        Frete frete = new Frete();
        frete.setCodigo("ERR123");
        frete.setDescricao("Teste erro cliente");
        frete.setPeso(new BigDecimal("5.0"));

        // Cliente e cidade inexistentes
        frete.setCliente(new Cliente(999L, "Fake", "Sem endereço", "000000000"));
        frete.setCidade(cidade);

        mockMvc.perform(post("/fretes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(frete)))
                .andExpect(status().isInternalServerError());
    }
}
