-- Schema esperado pela aplicação
-- Cria tabelas: paciente, vacina, dose, imunizacoes

CREATE TABLE IF NOT EXISTS paciente (
  id serial PRIMARY KEY,
  nome varchar(150) NOT NULL,
  cpf varchar(20) NOT NULL,
  sexo varchar(1),
  data_nascimento date
);

CREATE TABLE IF NOT EXISTS vacina (
  id serial PRIMARY KEY,
  vacina varchar(150) NOT NULL,
  descricao text,
  limite_aplicacao int,
  publico_alvo varchar(50)
);

CREATE TABLE IF NOT EXISTS dose (
  id serial PRIMARY KEY,
  id_vacina int NOT NULL REFERENCES vacina(id) ON DELETE CASCADE,
  dose varchar(50) NOT NULL,
  idade_recomendada_aplicacao int
);

CREATE TABLE IF NOT EXISTS imunizacoes (
  id serial PRIMARY KEY,
  id_paciente int NOT NULL REFERENCES paciente(id) ON DELETE CASCADE,
  id_dose int NOT NULL REFERENCES dose(id) ON DELETE CASCADE,
  data_aplicacao date,
  fabricante varchar(100),
  lote varchar(100),
  local_aplicacao varchar(150),
  profissional_aplicador varchar(150)
);

-- Dados de exemplo mínimos
INSERT INTO paciente (nome, cpf, sexo, data_nascimento) VALUES
  ('Ana Maria', '11122233344', 'F', '1990-05-12'),
  ('Carlos Silva', '22233344455', 'M', '1985-08-20');

INSERT INTO vacina (vacina, descricao, limite_aplicacao, publico_alvo) VALUES
  ('Hepatite B', 'Vacina contra hepatite B', 3, 'CRIANCA'),
  ('Tríplice Viral', 'Sarampo, caxumba e rubéola', 2, 'CRIANCA');

INSERT INTO dose (id_vacina, dose, idade_recomendada_aplicacao) VALUES
  (1, 'Dose 1', 0),
  (1, 'Dose 2', 1),
  (2, 'Dose única', 12);

-- Exemplo de imunização
INSERT INTO imunizacoes (id_paciente, id_dose, data_aplicacao, fabricante, lote, local_aplicacao, profissional_aplicador) VALUES
  (1, 1, '2024-01-10', 'Fabricante A', 'L123', 'UBS Centro', 'Enfermeira X');
