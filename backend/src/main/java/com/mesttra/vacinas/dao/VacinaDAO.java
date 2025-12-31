package com.mesttra.vacinas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mesttra.vacinas.config.ConexaoBanco;
import com.mesttra.vacinas.dto.DTOVacinaDose;
import com.mesttra.vacinas.models.Vacina;
import com.mesttra.vacinas.models.enums.PublicoAlvo;

public class VacinaDAO {
	public static List<Vacina> consultarTodasVacinas() throws SQLException {
        String sql = "SELECT id, vacina, descricao, limite_aplicacao, publico_alvo FROM vacina";

        List<Vacina> vacinas = new ArrayList<>();

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                Vacina vacina = new Vacina(
                        resultado.getInt("id"),
                        resultado.getString("vacina"),
                        resultado.getString("descricao"),
                        resultado.getInt("limite_aplicacao"),
                        PublicoAlvo.valueOf(resultado.getString("publico_alvo"))
                );
                vacinas.add(vacina);
            }
        }

        return vacinas;
    }
	
    public static List<Vacina> consultarVacinasPorFaixaEtaria(PublicoAlvo publicoAlvo) throws SQLException {
        String sql = "SELECT id, vacina, descricao, limite_aplicacao, publico_alvo FROM vacina WHERE publico_alvo = ?";

        List<Vacina> vacinas = new ArrayList<>();

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, publicoAlvo.name());

            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    Vacina vacina = new Vacina(
                            resultado.getInt("id"),
                            resultado.getString("vacina"),
                            resultado.getString("descricao"),
                            resultado.getInt("limite_aplicacao"),
                            PublicoAlvo.valueOf(resultado.getString("publico_alvo"))
                    );
                    vacinas.add(vacina);
                }
            }
        }

        return vacinas;
    }

    public static List<DTOVacinaDose> consultarVacinasPorIdadeMaior(int meses) throws SQLException {
        String sql = "SELECT v.id, v.vacina, v.descricao, d.dose, d.idade_recomendada_aplicacao "
                   + "FROM vacina v "
                   + "JOIN dose d ON d.id_vacina = v.id "
                   + "WHERE d.idade_recomendada_aplicacao > ?";

        List<DTOVacinaDose> vacinas = new ArrayList<>();

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, meses);

            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    DTOVacinaDose vacina = new DTOVacinaDose(
                            resultado.getInt("id"),
                            resultado.getString("vacina"),
                            resultado.getString("descricao"),
                            resultado.getString("dose"),
                            resultado.getInt("idade_recomendada_aplicacao")
                    );
                    vacinas.add(vacina);
                }
            }
        }

        return vacinas;
    }
	
    public static List<DTOVacinaDose> consultarVacinasNaoAplicaveis(int idPaciente) throws SQLException {
        String sql = "SELECT v.id, v.vacina, v.descricao, d.dose, d.idade_recomendada_aplicacao "
                   + "FROM vacina v "
                   + "JOIN dose d ON d.id_vacina = v.id "
                   + "JOIN paciente p ON p.id = ? "
                   + "WHERE TIMESTAMPDIFF(MONTH, p.data_nascimento, CURDATE()) > d.idade_recomendada_aplicacao";

        List<DTOVacinaDose> vacinas = new ArrayList<>();

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, idPaciente);

            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    DTOVacinaDose vacina = new DTOVacinaDose(
                            resultado.getInt("id"),
                            resultado.getString("vacina"),
                            resultado.getString("descricao"),
                            resultado.getString("dose"),
                            resultado.getInt("idade_recomendada_aplicacao")
                    );
                    vacinas.add(vacina);
                }
            }
        }

        return vacinas;
    }

}

