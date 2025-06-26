-- Clientes
INSERT INTO cliente (id, nome, endereco, telefone)
VALUES (1, 'Maria da Silva', 'Rua das Acácias, 100', '989898898');
INSERT INTO cliente (id, nome, endereco, telefone)
VALUES (2, 'João Souza', 'Av. Brasil, 200', '999999999');

-- Cidades
INSERT INTO cidade (id, nome, estado, taxa_entrega)
VALUES (1, 'São Luís', 'MA', 15.50);
INSERT INTO cidade (id, nome, estado, taxa_entrega)
VALUES (2, 'Imperatriz', 'MA', 12.00);

-- Fretes
INSERT INTO frete (id, codigo, descricao, peso, valor, cliente_id, cidade_id)
VALUES (1, 'F001', 'Entrega urgente de documentos', 2.0, 35.50, 1, 1);

INSERT INTO frete (id, codigo, descricao, peso, valor, cliente_id, cidade_id)
VALUES (2, 'F002', 'Caixa com eletrônicos', 5.0, 62.00, 2, 2);

