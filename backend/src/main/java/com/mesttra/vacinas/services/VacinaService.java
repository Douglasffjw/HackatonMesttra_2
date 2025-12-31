package com.mesttra.vacinas.services;

import spark.Request;
import spark.Response;
import spark.Route;

import com.mesttra.vacinas.models.Vacina;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mesttra.vacinas.dao.VacinaDAO;
import com.mesttra.vacinas.dto.DTOVacinaDose;
import com.mesttra.vacinas.models.enums.PublicoAlvo;

import java.util.List;

public class VacinaService {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Route readVacinas() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                try {
                    List<Vacina> vacinas = VacinaDAO.consultarTodasVacinas();
                    res.status(200);
                    return gson.toJson(vacinas);
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
            }
        };
    }

    public static Route readVacinasPorFaixaEtaria() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                try {
                    String faixaEtariaParam = req.params(":faixa");

                    if (faixaEtariaParam == null) {
                        res.status(400);
                        return "{\"error\": \"Faixa etária é obrigatória.\"}";
                    }

                    PublicoAlvo publicoAlvo;
                    try {
                        publicoAlvo = PublicoAlvo.valueOf(faixaEtariaParam.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        res.status(400);
                        return "{\"error\": \"Faixa etária inválida.\"}";
                    }

                    List<Vacina> vacinas = VacinaDAO.consultarVacinasPorFaixaEtaria(publicoAlvo);

                    if (vacinas.isEmpty()) {
                        res.status(404);
                        return "{\"message\": \"Nenhuma vacina encontrada para a faixa etária especificada.\"}";
                    }

                    res.status(200);
                    return gson.toJson(vacinas);
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
            }
        };
    }

    public static Route readVacinasPorIdadeMaior() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                try {
                    String mesesParam = req.params(":meses");

                    if (mesesParam == null) {
                        res.status(400);
                        return "{\"error\": \"Número de meses é obrigatório.\"}";
                    }

                    int meses;
                    try {
                        meses = Integer.parseInt(mesesParam);
                    } catch (NumberFormatException e) {
                        res.status(400);
                        return "{\"error\": \"Número de meses inválido.\"}";
                    }

                    List<DTOVacinaDose> vacinas = VacinaDAO.consultarVacinasPorIdadeMaior(meses);

                    if (vacinas.isEmpty()) {
                        res.status(404);
                        return "{\"message\": \"Nenhuma vacina encontrada para a idade especificada.\"}";
                    }

                    res.status(200);
                    return gson.toJson(vacinas);
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
            }
        };
    }

    public static Route readVacinasNaoAplicaveis() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                try {
                    String pacienteIdParam = req.params(":id");

                    if (pacienteIdParam == null) {
                        res.status(400);
                        return "{\"error\": \"ID do paciente é obrigatório.\"}";
                    }

                    int pacienteId;
                    try {
                        pacienteId = Integer.parseInt(pacienteIdParam);
                    } catch (NumberFormatException e) {
                        res.status(400);
                        return "{\"error\": \"ID do paciente inválido.\"}";
                    }

                    List<DTOVacinaDose> vacinas = VacinaDAO.consultarVacinasNaoAplicaveis(pacienteId);

                    if (vacinas.isEmpty()) {
                        res.status(404);
                        return "{\"message\": \"Nenhuma vacina não aplicável encontrada para o paciente especificado.\"}";
                    }

                    res.status(200);
                    return gson.toJson(vacinas);
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
            }
        };
    }

    public static Route consultarTodasVacinas() {
        
        throw new UnsupportedOperationException("Unimplemented method 'consultarTodasVacinas'");
    }
}
