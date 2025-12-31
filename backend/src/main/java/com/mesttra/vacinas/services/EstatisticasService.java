package com.mesttra.vacinas.services;

import spark.Request;
import spark.Response;
import spark.Route;


import com.mesttra.vacinas.dao.EstatisticaDAO;


public class EstatisticasService {

    public static Route readEstatisticasImunizacoesPorPaciente() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                int idPaciente = Integer.parseInt(req.params(":id"));

                try {
                    int qtdVacinas = EstatisticaDAO.qtdeVacinasAplicadasPorPaciente(idPaciente);
                    int qtdVacinasProxMes = EstatisticaDAO.qtdeProximasImunizacoes(idPaciente);
                    int qtdVacinasAtrasadas = EstatisticaDAO.consultarQtdeVacinasAtrasadasPorPaciente(idPaciente);
                    int qtdVacinasAcimaIdade = EstatisticaDAO.consultarVacinasAcimaDeIdade(idPaciente);
                    int qtdVacinasNotApplicable = EstatisticaDAO.consultarVacinasNaoAplicaveis(idPaciente);

                    res.status(200);
                    return String.format("{\"qtdVacinas\": %d, \"qtdVacinasProxMes\": %d, \"qtdVacinasAtrasadas\": %d, \"qtdVacinasAcimaIdade\": %d, \"qtdVacinasNotApplicable\": %d}",
                            qtdVacinas, qtdVacinasProxMes, qtdVacinasAtrasadas, qtdVacinasAcimaIdade, qtdVacinasNotApplicable);
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
            }
        };
    }

    public static Route readQtdeProximasImunizacoes() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                int idPaciente = Integer.parseInt(req.params(":id"));

                try {
                    int qtdVacinasProxMes = EstatisticaDAO.qtdeProximasImunizacoes(idPaciente);

                    res.status(200);
                    return String.format("{\"qtdVacinasProxMes\": %d}", qtdVacinasProxMes);
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
            }
        };
    }

    public static Route readQtdeVacinasAtrasadas() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                int idPaciente = Integer.parseInt(req.params(":id"));

                try {
                    int qtdVacinasAtrasadas = EstatisticaDAO.consultarQtdeVacinasAtrasadasPorPaciente(idPaciente);

                    res.status(200);
                    return String.format("{\"qtdVacinasAtrasadas\": %d}", qtdVacinasAtrasadas);
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
            }
        };
    }

    public static Route readQtdeVacinasAcimaDeIdade() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                int meses = Integer.parseInt(req.params(":meses"));

                try {
                    int qtdVacinasAcimaIdade = EstatisticaDAO.consultarVacinasAcimaDeIdade(meses);

                    res.status(200);
                    return String.format("{\"qtdVacinasAcimaIdade\": %d}", qtdVacinasAcimaIdade);
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
            }
        };
    }

    public static Route readQtdeVacinasNaoAplicaveis() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                int idPaciente = Integer.parseInt(req.params(":id"));

                try {
                    int qtdVacinasNaoAplicaveis = EstatisticaDAO.consultarVacinasNaoAplicaveis(idPaciente);

                    res.status(200);
                    return String.format("{\"qtdVacinasNaoAplicaveis\": %d}", qtdVacinasNaoAplicaveis);
                } catch (Exception e) {
                    res.status(500);
                    return "{\"error\": \"" + e.getMessage() + "\"}";
                }
            }
        };
    }
}