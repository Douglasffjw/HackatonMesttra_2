package com.mesttra.vacinas.services;

import spark.Request;
import spark.Response;
import spark.Route;
import com.mesttra.vacinas.config.ConexaoBanco;
import java.sql.Connection;
import java.sql.SQLException;

public class ConexaoService {

    public static Route testarConexao() {
        return new Route() {
            @Override
            public Object handle(Request req, Response res) {
                try (Connection connection = ConexaoBanco.getConnection()) {
                    if (connection != null) {
                        res.status(200);
                        return "{\"message\": \"Conexão com o banco de dados estabelecida com sucesso!\"}";
                    } else {
                        res.status(500);
                        return "{\"error\": \"Falha ao estabelecer conexão com o banco de dados.\"}";
                    }
                } catch (SQLException e) {
                    res.status(500);
                    return "{\"error\": \"Erro ao conectar com o banco de dados: " + e.getMessage() + "\"}";
                }
            }
        };
    }
}