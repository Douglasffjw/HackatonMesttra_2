# 📌 Hackaton 1000Devs - Sistema de Gerenciamento de Vacinas 

## 📖 Sobre o Projeto
Desenvolvimento de um software para gerenciamento das vacinas. O sistema permite visualizar o calendário vacinal por idade recomendada e cadastrar as vacinas aplicadas a cada membro da família, além davisualização de vacinas em atraso.

## 🎯 Objetivo
O principal objetivo deste projeto é fornecer um meio eficiente para registrar e acompanhar a vacinação dos membros da família, garantindo que todas as doses recomendadas sejam aplicadas no tempo certo.

## 🚀 Tecnologias Utilizadas
- Java com Spark Web Framework 🌟
- Banco de Dados MySQL 🛢️
- API Restful 🌐
- Cliente API Thunder Client  🧑‍💻

## 📌 Funcionalidades Principais
✅ Cadastro de pacientes e suas informações pessoais    

✅ Registro de vacinas aplicadas

✅ Consulta do calendário vacinal

✅ Estatísticas sobre imunizações

✅ Suporte a diferentes públicos-alvo (Criança, Adolescente, Adulto, Gestante)

## 🗄️ Modelo de Dados
### 🔹 Tabelas Principais
1. **Vacina**: Nome, descrição, limite de aplicação, público-alvo
2. **Dose**: Dose da vacina, idade recomendada para aplicação
3. **Paciente**: Nome, CPF, sexo, data de nascimento
4. **Imunização**: Dados da aplicação da vacina

## 🎯 Como Rodar o Projeto
1. Clone o repositório
```bash
 git clone https://github.com/douglasffjw/gerenciadordevacinas.git
```
2. Configure o banco de dados MySQL conforme o modelo de dados
3. Execute a aplicação
```bash
 java -jar nome-do-projeto.jar
```

### **Instruções rápidas — Backend (Java / Maven)**

- **Requisitos:** JDK 21+, Maven, PostgreSQL (ou Docker).
- Entre na pasta `backend` e gere o JAR:

```bash
cd backend
mvn clean package
```

- No Git Bash (MINGW) exporte as variáveis de ambiente para a sessão atual (opção 1):

```bash
export DB_URL="jdbc:postgresql://localhost:5432/gerenciador_vacinas"
export DB_USER="postgres"
export DB_PASS="12345678"
```

- Ou execute o JAR passando variáveis apenas para essa execução (opção 2):

```bash
DB_URL="jdbc:postgresql://localhost:5432/gerenciador_vacinas" DB_USER="postgres" DB_PASS="12345678" java -jar target/vacinas-1.0-SNAPSHOT-jar-with-dependencies.jar
```

- Se preferir PowerShell (sessão atual):

```powershell
$env:DB_URL = 'jdbc:postgresql://localhost:5432/gerenciador_vacinas'
$env:DB_USER = 'postgres'
$env:DB_PASS = '12345678'
java -jar target\\vacinas-1.0-SNAPSHOT-jar-with-dependencies.jar
```

- Banco de dados (Postgres): crie o banco e rode o schema (se tiver `psql`):

```bash
psql -U postgres -c "CREATE DATABASE gerenciador_vacinas;"
psql -U postgres -d gerenciador_vacinas -f database/schema-app.sql
```

- Alternativa com Docker (se não quiser instalar Postgres):

```bash
docker run --name pg-vacinas -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=12345678 -e POSTGRES_DB=gerenciador_vacinas -p 5432:5432 -d postgres:15
docker cp database/schema-app.sql pg-vacinas:/tmp/schema.sql
docker exec -i pg-vacinas psql -U postgres -d gerenciador_vacinas -f /tmp/schema.sql
```

O backend inicia por padrão na porta `4567` (Spark). Exemplo de verificação de rota:

```bash
curl http://localhost:4567/pacientes
```

### **Instruções rápidas — Frontend (Vite / React)**

- Requisitos: Node.js 18+ e npm.
- Entre na pasta `frontend` e instale dependências:

```bash
cd frontend
npm install
npm run dev
```

- O Vite roda por padrão em `http://localhost:5173`.

### **Observações**
- As credenciais padrão usadas pelo projeto estão em `backend/src/main/java/com/mesttra/vacinas/config/ConexaoBanco.java` e podem ser sobrescritas por variáveis de ambiente `DB_URL`, `DB_USER`, `DB_PASS`.
- O backend já habilita CORS nas rotas para que o frontend consiga consumir a API.


## 📌 Contribuidores 


| [![Ana Vitoria](https://github.com/Bella-my.png?size=100)](https://github.com/Bella-my) | [![Fernando Alv](https://github.com/Fernando-Alv.png?size=100)](https://github.com/Fernando-Alv) | [![Zé Fernando](https://github.com/ze-fernando.png?size=100)](https://github.com/ze-fernando) |
|:---:|:---:|:---:|
| **[Ana Vitoria](https://github.com/Bella-my)** | **[Fernando Alv](https://github.com/Fernando-Alv)** | **[Zé Fernando](https://github.com/ze-fernando)** |

| [![Rodrigo TP](https://github.com/rodrigo-tp.png?size=100)](https://github.com/rodrigo-tp) | [![Miri12345](https://github.com/miri12345.png?size=100)](https://github.com/miri12345) |  [![Douglasffjw](https://github.com/Douglasffjw.png?size=100)](https://github.com/Douglasffjw) | 
|:---:|:---:|:---:|
| **[Rodrigo TP](https://github.com/rodrigo-tp)** | **[Miri12345](https://github.com/miri12345)** | **[Douglasffjw](https://github.com/Douglasffjw)** |
