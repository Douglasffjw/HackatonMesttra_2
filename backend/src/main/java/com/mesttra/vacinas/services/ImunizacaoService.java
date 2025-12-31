package com.mesttra.vacinas.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mesttra.vacinas.dao.ImunizacoesDAO;
import com.mesttra.vacinas.dto.DTOImunizacaoDosePaciente;
import com.mesttra.vacinas.models.Imunizacoes;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ImunizacaoService {

     private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Route createImunizacao() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                try {
                    String pacienteIdParam = req.queryParams("id_paciente");
                    String doseIdParam = req.queryParams("id_dose");
                    String dataAplicacaoParam = req.queryParams("data_aplicacao");
                    String fabricante = req.queryParams("fabricante");
                    String lote = req.queryParams("lote");
                    String localAplicacao = req.queryParams("local_aplicacao");
                    String profissionalAplicador = req.queryParams("profissional_aplicador");

                    if (pacienteIdParam == null || doseIdParam == null || dataAplicacaoParam == null) {
                        res.status(400);
                        return "{\"error\": \"Os campos id_paciente, id_dose e data_aplicacao são obrigatórios.\"}";
                    }

                    int pacienteId = Integer.parseInt(pacienteIdParam);
                    int doseId = Integer.parseInt(doseIdParam);
                    Date dataAplicacao = Date.valueOf(dataAplicacaoParam);

                    Imunizacoes newImunizacao = new Imunizacoes(
                            0, pacienteId, doseId, dataAplicacao, fabricante, lote, localAplicacao, profissionalAplicador);

                    ImunizacoesDAO.adicionarImunizacao(newImunizacao);
                    res.status(201);
                    return "{\"message\": \"Imunização criada com sucesso.\"}";
                } catch (NumberFormatException e) {
                    res.status(400);
                    return "{\"error\": \"ID do paciente ou ID da dose inválido.\"}";
                } catch (SQLException e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
            }
        };
    }

    public static Route readImunizacoes() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                try {
                    List<DTOImunizacaoDosePaciente> imunizacoes = ImunizacoesDAO.consultarTodasImunizacoes();
                    res.status(200);
                    return gson.toJson(imunizacoes);
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
            }
        };
    }

    public static Route readImunizacaoByDoseId() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                try {
                    String doseIdParam = req.queryParams("id_dose");

                    if (doseIdParam == null) {
                        res.status(400);
                        return "{\"error\": \"ID da dose é obrigatório.\"}";
                    }

                    int doseId = Integer.parseInt(doseIdParam);
                    DTOImunizacaoDosePaciente imunizacao = ImunizacoesDAO.consultarImunizacaoPorIdDose(doseId);

                    if (imunizacao == null) {
                        res.status(404);
                        return "{\"message\": \"Imunização não encontrada.\"}";
                    }

                    res.status(200);
                    return gson.toJson(imunizacao);
                } catch (NumberFormatException e) {
                    res.status(400);
                    return "{\"error\": \"ID da dose inválido.\"}";
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
            }
        };
    }

    public static Route readImunizacoesByPaciente() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                try {
                    String pacienteIdParam = req.params(":id");

                    if (pacienteIdParam == null) {
                        res.status(400);
                        return "{\"error\": \"ID do paciente é obrigatório.\"}";
                    }

                    int pacienteId = Integer.parseInt(pacienteIdParam);
                    List<DTOImunizacaoDosePaciente> imunizacoes = ImunizacoesDAO.consultarImunizacoesPorIdPaciente(pacienteId);

                    if (imunizacoes.isEmpty()) {
                        res.status(404);
                        return "{\"message\": \"Imunizações não encontradas.\"}";
                    }

                    res.status(200);
                    return gson.toJson(imunizacoes);
                } catch (NumberFormatException e) {
                    res.status(400);
                    return "{\"error\": \"ID do paciente inválido.\"}";
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
            }
        };
    }

    public static Route readImunizacoesByPacienteAndDate() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                try {
                    String pacienteIdParam = req.params(":id");
                    String dataInicioParam = req.params(":dt_ini");
                    String dataFimParam = req.params(":dt_fim");

                    if (pacienteIdParam == null || dataInicioParam == null || dataFimParam == null) {
                        res.status(400);
                        return "{\"error\": \"ID do paciente, data de início e data de fim são obrigatórios.\"}";
                    }

                    int pacienteId = Integer.parseInt(pacienteIdParam);
                    java.sql.Date dataInicio = java.sql.Date.valueOf(dataInicioParam);
                    java.sql.Date dataFim = java.sql.Date.valueOf(dataFimParam);

                    List<DTOImunizacaoDosePaciente> imunizacoes = ImunizacoesDAO.consultarImunizacoesPorIdPacienteEPeriodo(pacienteId, dataInicio, dataFim);

                    if (imunizacoes.isEmpty()) {
                        res.status(404);
                        return "{\"message\": \"Imunizações não encontradas.\"}";
                    }

                    res.status(200);
                    return gson.toJson(imunizacoes);
                } catch (NumberFormatException e) {
                    res.status(400);
                    return "{\"error\": \"ID do paciente ou datas inválidas.\"}";
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
            }
        };
    }

    public static Route deleteImunizacaoByDoseId() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                try {
                    String doseIdParam = req.params(":id_dose");

                    if (doseIdParam == null) {
                        res.status(400);
                        return "{\"error\": \"ID da dose é obrigatório.\"}";
                    }

                    int doseId = Integer.parseInt(doseIdParam);
                    ImunizacoesDAO.excluirImunizacaoPorDoseId(doseId);
                    res.status(200);
                    return "{\"message\": \"Imunização excluída com sucesso.\"}";
                } catch (NumberFormatException e) {
                    res.status(400);
                    return "{\"error\": \"ID da dose inválido.\"}";
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
            }
        };
    }

    public static Route deleteImunizacoesByPaciente() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                try {
                    int pacienteId = Integer.parseInt(req.params(":id"));
                    ImunizacoesDAO.excluirTodasImunizacoesDoPaciente(pacienteId);
                    res.status(204);
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
                                return res;
            }
        };
    }

    public static Route updateImunizacao() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                try {
                    String idParam = req.params(":id");
                    String pacienteIdParam = req.queryParams("id_paciente");
                    String doseIdParam = req.queryParams("id_dose");
                    String dataAplicacaoParam = req.queryParams("data_aplicacao");
                    String fabricante = req.queryParams("fabricante");
                    String lote = req.queryParams("lote");
                    String localAplicacao = req.queryParams("local_aplicacao");
                    String profissionalAplicador = req.queryParams("profissional_aplicador");

                    if (idParam == null || pacienteIdParam == null || doseIdParam == null || dataAplicacaoParam == null) {
                        res.status(400);
                        return "{\"error\": \"Os campos id, id_paciente, id_dose e data_aplicacao são obrigatórios.\"}";
                    }

                    int id = Integer.parseInt(idParam);
                    int pacienteId = Integer.parseInt(pacienteIdParam);
                    int doseId = Integer.parseInt(doseIdParam);
                    java.sql.Date dataAplicacao = java.sql.Date.valueOf(dataAplicacaoParam);

                    DTOImunizacaoDosePaciente imunizacao = new  DTOImunizacaoDosePaciente(
                            id, pacienteId, doseId, dataAplicacao, fabricante, lote, localAplicacao, profissionalAplicador);

                    ImunizacoesDAO.alterarImunizacao(imunizacao);
                    res.status(200);
                    return "{\"message\": \"Imunização atualizada com sucesso.\"}";
                } catch (NumberFormatException e) {
                    res.status(400);
                    return "{\"error\": \"ID inválido.\"}";
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
            }
        };
    }
}
