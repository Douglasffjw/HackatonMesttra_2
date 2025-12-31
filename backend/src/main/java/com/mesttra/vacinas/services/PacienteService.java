package com.mesttra.vacinas.services;

import spark.Request;
import spark.Response;
import spark.Route;


import com.mesttra.vacinas.models.Paciente;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mesttra.vacinas.dao.PacienteDAO;
import com.mesttra.vacinas.models.Paciente.Sexo;

public class PacienteService {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Cria paciente aceitando JSON no corpo ou form params (compatibilidade)
    public static Route createPaciente(){
        return new Route() {
            @Override
            public Object handle(Request req, Response res){
                res.type("application/json");
                try {
                    String nome = null;
                    String cpf = null;
                    String sexoParam = null;
                    String nascimentoParam = null;

                    String body = req.body();
                    if (body != null && !body.isEmpty() && req.contentType() != null && req.contentType().contains("application/json")) {
                        JsonElement el = JsonParser.parseString(body);
                        if (el != null && el.isJsonObject()) {
                            JsonObject obj = el.getAsJsonObject();
                            if (obj.has("nome")) nome = obj.get("nome").getAsString();
                            if (obj.has("cpf")) cpf = obj.get("cpf").getAsString();
                            if (obj.has("sexo")) sexoParam = obj.get("sexo").getAsString();
                            if (obj.has("nascimento")) nascimentoParam = obj.get("nascimento").getAsString();
                        }
                    } else {
                        nome = req.queryParams("nome");
                        cpf = req.queryParams("cpf");
                        sexoParam = req.queryParams("sexo");
                        nascimentoParam = req.queryParams("nascimento");
                    }

                    // Validando os parâmetros
                    if (nome == null || cpf == null || sexoParam == null || nascimentoParam == null) {
                        res.status(400);
                        JsonObject err = new JsonObject();
                        err.addProperty("error", "Todos os campos são obrigatórios.");
                        return gson.toJson(err);
                    }

                    Sexo sexo;
                    try {
                        sexo = Sexo.valueOf(sexoParam.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        res.status(400);
                        JsonObject err = new JsonObject();
                        err.addProperty("error", "Sexo inválido. Use 'M' ou 'F'.");
                        return gson.toJson(err);
                    }

                    java.sql.Date dataNascimento = java.sql.Date.valueOf(nascimentoParam);

                    // Criando o novo paciente
                    Paciente newPaciente = new Paciente(0, nome, cpf, sexo, dataNascimento);

                    // Adicionando o paciente ao banco de dados
                    PacienteDAO.adicionarPaciente(newPaciente);
                    res.status(201);

                    JsonObject ok = new JsonObject();
                    ok.addProperty("message", "Paciente " + newPaciente.getNome() + " inserido com sucesso.");
                    return gson.toJson(ok);
                } catch (IllegalArgumentException e) {
                    res.status(400);
                    JsonObject err = new JsonObject();
                    err.addProperty("error", "Data de nascimento inválida. Use o formato 'yyyy-MM-dd'.");
                    return gson.toJson(err);
                } catch (Exception e) {
                    res.status(500);
                    JsonObject err = new JsonObject();
                    err.addProperty("error", e.getMessage());
                    return gson.toJson(err);
                }
            };
        };
    }

    public static Route readPaciente() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                res.type("application/json");
                try {
                    List<Paciente> pacientes = PacienteDAO.consultarTodosPacientes();
                    res.status(200);

                    return gson.toJson(pacientes);
                } catch (Exception e) {
                    res.status(500);
                    JsonObject err = new JsonObject();
                    err.addProperty("error", e.getMessage());
                    return gson.toJson(err);
                }
            }
        };
    }

    public static Route readPacienteById() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                res.type("application/json");
                try {
                    String idParam = req.queryParams("id");

                    if (idParam == null) {
                        res.status(400);
                        JsonObject err = new JsonObject();
                        err.addProperty("error", "ID é obrigatório.");
                        return gson.toJson(err);
                    }

                    int id = Integer.parseInt(idParam);
                    Paciente paciente = PacienteDAO.consultarPacientePorId(id);

                    if (paciente == null) {
                        res.status(404);
                        JsonObject msg = new JsonObject();
                        msg.addProperty("message", "Paciente não encontrado.");
                        return gson.toJson(msg);
                    }

                    res.status(200);
                    return gson.toJson(paciente);
                } catch (NumberFormatException e) {
                    res.status(400);
                    JsonObject err = new JsonObject();
                    err.addProperty("error", "ID inválido.");
                    return gson.toJson(err);
                } catch (Exception e) {
                    res.status(500);
                    JsonObject err = new JsonObject();
                    err.addProperty("error", e.getMessage());
                    return gson.toJson(err);
                }
            }
        };
    }

    public static Route updatePaciente() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                res.type("application/json");
                try {
                    String idParam = req.params(":id");

                    if (idParam == null) {
                        res.status(400);
                        JsonObject err = new JsonObject();
                        err.addProperty("error", "ID é obrigatório.");
                        return gson.toJson(err);
                    }

                    int id = Integer.parseInt(idParam);
                    Paciente pacienteDb = PacienteDAO.consultarPacientePorId(id);

                    if (pacienteDb == null) {
                        res.status(404);
                        JsonObject msg = new JsonObject();
                        msg.addProperty("message", "Paciente não encontrado.");
                        return gson.toJson(msg);
                    }

                    // Suporta JSON ou form params para atualização
                    String body = req.body();
                    String nome = req.queryParams("nome");
                    String cpf = req.queryParams("cpf");
                    String sexoParam = req.queryParams("sexo");
                    String nascimentoParam = req.queryParams("nascimento");

                    if (body != null && !body.isEmpty() && req.contentType() != null && req.contentType().contains("application/json")) {
                        JsonElement el = JsonParser.parseString(body);
                        if (el != null && el.isJsonObject()) {
                            JsonObject obj = el.getAsJsonObject();
                            if (obj.has("nome")) nome = obj.get("nome").getAsString();
                            if (obj.has("cpf")) cpf = obj.get("cpf").getAsString();
                            if (obj.has("sexo")) sexoParam = obj.get("sexo").getAsString();
                            if (obj.has("nascimento")) nascimentoParam = obj.get("nascimento").getAsString();
                        }
                    }

                    if (nome != null && !nome.isEmpty()) {
                        pacienteDb.setNome(nome);
                    }
                    if (cpf != null && !cpf.isEmpty()) {
                        pacienteDb.setCpf(cpf);
                    }
                    if (sexoParam != null && !sexoParam.isEmpty()) {
                        try {
                            Sexo sexo = Sexo.valueOf(sexoParam.toUpperCase());
                            pacienteDb.setSexo(sexo);
                        } catch (IllegalArgumentException e) {
                            res.status(400);
                            JsonObject err = new JsonObject();
                            err.addProperty("error", "Sexo inválido. Use 'M' ou 'F'.");
                            return gson.toJson(err);
                        }
                    }
                    if (nascimentoParam != null && !nascimentoParam.isEmpty()) {
                        try {
                            java.sql.Date dataNascimento = java.sql.Date.valueOf(nascimentoParam);
                            pacienteDb.setDataNascimento(dataNascimento);
                        } catch (IllegalArgumentException e) {
                            res.status(400);
                            JsonObject err = new JsonObject();
                            err.addProperty("error", "Data de nascimento inválida. Use o formato 'yyyy-MM-dd'.");
                            return gson.toJson(err);
                        }
                    }

                    PacienteDAO.alterarPaciente(pacienteDb);
                    res.status(200);
                    JsonObject ok = new JsonObject();
                    ok.addProperty("message", "Paciente atualizado com sucesso.");
                    return gson.toJson(ok);
                } catch (NumberFormatException e) {
                    res.status(400);
                    JsonObject err = new JsonObject();
                    err.addProperty("error", "ID inválido.");
                    return gson.toJson(err);
                } catch (Exception e) {
                    res.status(500);
                    JsonObject err = new JsonObject();
                    err.addProperty("error", e.getMessage());
                    return gson.toJson(err);
                }
            }
        };
    }

    public static Route deletePaciente() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res){ 
                res.type("application/json");
                try {
                    int id = Integer.parseInt(req.params(":id"));

                    PacienteDAO.excluirPaciente(id);
                    res.status(200);

                    JsonObject ok = new JsonObject();
                    ok.addProperty("message", "Paciente excluído com sucesso.");
                    return gson.toJson(ok);
                } catch (NumberFormatException e) {
                    res.status(400);
                    JsonObject err = new JsonObject();
                    err.addProperty("error", "ID inválido.");
                    return gson.toJson(err);
                } catch (Exception e) {
                    res.status(500);
                    JsonObject err = new JsonObject();
                    err.addProperty("error", e.getMessage());
                    return gson.toJson(err);
                }
            }
        };
    }
}