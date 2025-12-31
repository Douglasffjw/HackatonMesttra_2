# Frontend (Vite + React)

Este frontend consome o backend Spark Java presente na pasta `vacinas`.

Pré-requisitos:
- Node.js (v16+ recomendado)

Instalação e execução em desenvolvimento:

```bash
cd frontend
npm install
npm run dev
```

O vite está configurado para rodar em `http://localhost:3000` e proxyar chamadas para `/api` → `http://localhost:4567` (Spark padrão).

Exemplos de endpoints usados pelo frontend:
- `GET /api/pacientes` → lista de pacientes
- `POST /api/paciente` → cria paciente com JSON: `{ "nome": "...", "cpf": "...", "sexo": "M|F", "nascimento": "yyyy-MM-dd" }`

Observações:
- Certifique-se que o backend (`vacinas`) esteja rodando em `localhost:4567`.
- Se mudar a porta do Spark, atualize `vite.config.js` ou as chamadas do frontend.
