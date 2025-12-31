DROP DATABASE IF EXISTS api;
CREATE DATABASE api;
\c api

CREATE TABLE usuario (
  id serial PRIMARY KEY,
  nome varchar(45) NOT NULL,
  email varchar(45) NOT NULL
);

CREATE TABLE produto (
  id serial PRIMARY KEY,
  descricao varchar(45) NOT NULL,
  preco_unitario numeric(10,2) NOT NULL
);

CREATE TABLE pedido (
  id serial PRIMARY KEY,
  id_usuario int NOT NULL,
  data_compra timestamp NOT NULL,
  CONSTRAINT fk_usuario_id FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

CREATE TABLE item_pedido (
  id serial PRIMARY KEY,
  id_pedido int NOT NULL,
  id_produto int NOT NULL,
  qtde int NOT NULL,
  CONSTRAINT fk_pedido_id FOREIGN KEY (id_pedido) REFERENCES pedido(id),
  CONSTRAINT fk_produto_id FOREIGN KEY (id_produto) REFERENCES produto(id)
);

INSERT INTO produto (id, descricao, preco_unitario) VALUES
  (1,'Sabão em pó 1KG',5.2),
  (2,'Sabão Líquido Neutro',1.2),
  (3,'Arroz 1KG',4.8),
  (4,'Arroz 5KG',20.1),
  (5,'Coca-Cola 1LT',12);

INSERT INTO usuario (id, nome, email) VALUES
  (1,'Rogerio de Freitas Ribeiro','rogeriofr@gmail.com'),
  (2,'Maria da Silva','maria@gmail.com');

INSERT INTO pedido (id, id_usuario, data_compra) VALUES
  (3,1,'2025-01-30 15:20:12'),
  (4,2,'2025-02-02 10:00:00');

INSERT INTO item_pedido (id, id_pedido, id_produto, qtde) VALUES
  (1,3,1,1),
  (2,3,4,2),
  (3,3,2,5),
  (4,4,5,1),
  (5,4,1,2),
  (6,4,2,1);
