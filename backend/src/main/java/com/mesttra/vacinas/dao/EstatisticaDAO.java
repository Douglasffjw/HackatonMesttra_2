package com.mesttra.vacinas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mesttra.vacinas.config.ConexaoBanco;

public class EstatisticaDAO {
	public static int qtdeVacinasAplicadasPorPaciente(int idPaciente) throws SQLException {
		int estatistica = 0;

		String sql = "SELECT COUNT(*) AS quantidadeVacinasAplicadas "
													+ "FROM imunizacoes "
													+ "WHERE id_paciente = ?";

		try (Connection conexao = ConexaoBanco.getConnection();
							PreparedStatement comando = conexao.prepareStatement(sql)) {

						comando.setInt(1, idPaciente);

						try (ResultSet resultado = comando.executeQuery()) {
										if (resultado.next()) {
														estatistica = resultado.getInt("quantidadeVacinasAplicadas");
										}
						}
		}
		return estatistica;
}
	
public static int qtdeProximasImunizacoes(int idPaciente) throws SQLException {
	int estatistica = 0;

	String sql = "SELECT COUNT(*) AS qtdeVacinasParaProximoMes "
												+ "FROM dose d "
												+ "JOIN vacina v ON d.id_vacina = v.id "
												+ "WHERE d.idade_recomendada_aplicacao = TIMESTAMPDIFF(MONTH, "
												+ "    (SELECT data_nascimento FROM paciente WHERE id = ?), CURDATE()) + 1";

	try (Connection conexao = ConexaoBanco.getConnection();
						PreparedStatement comando = conexao.prepareStatement(sql)) {

					comando.setInt(1, idPaciente);

					try (ResultSet resultado = comando.executeQuery()) {
									if (resultado.next()) {
													estatistica = resultado.getInt("qtdeVacinasParaProximoMes");
									}
					}
	}
	return estatistica;
}

public static int consultarQtdeVacinasAtrasadasPorPaciente(int idPaciente) throws SQLException {
	int estatistica = 0;

	String sql = "SELECT COUNT(*) AS qtdVacinasNaoAplicadas "
												+ "FROM dose d "
												+ "JOIN vacina v ON d.id_vacina = v.id "
												+ "WHERE d.idade_recomendada_aplicacao <= TIMESTAMPDIFF(MONTH, "
												+ "    (SELECT data_nascimento FROM paciente WHERE id = ?), CURDATE()) "
												+ "AND d.id NOT IN (SELECT id_dose FROM imunizacoes WHERE id_paciente = ?)";

	try (Connection conexao = ConexaoBanco.getConnection();
						PreparedStatement comando = conexao.prepareStatement(sql)) {

					comando.setInt(1, idPaciente);
					comando.setInt(2, idPaciente);

					try (ResultSet resultado = comando.executeQuery()) {
									if (resultado.next()) {
													estatistica = resultado.getInt("qtdVacinasNaoAplicadas");
									}
					}
	}
	return estatistica;
}

public static int consultarVacinasAcimaDeIdade(int meses) throws SQLException {
	int estatistica = 0;

	String sql = "SELECT COUNT(*) AS qtdeVacinasAcimaDaIdade "
												+ "FROM dose d "
												+ "JOIN vacina v ON d.id_vacina = v.id "
												+ "WHERE d.idade_recomendada_aplicacao > ?";

	try (Connection conexao = ConexaoBanco.getConnection();
						PreparedStatement comando = conexao.prepareStatement(sql)) {

					comando.setInt(1, meses);

					try (ResultSet resultado = comando.executeQuery()) {
									if (resultado.next()) {
													estatistica = resultado.getInt("qtdeVacinasAcimaDaIdade");
									}
					}
	}
	return estatistica;
}

public static int consultarVacinasNaoAplicaveis(int idPaciente) throws SQLException {
	int estatistica = 0;

	String sql = "SELECT COUNT(*) AS qtdVacinasNaoAplicaveis "
												+ "FROM vacina v "
												+ "JOIN dose d ON v.id = d.id_vacina "
												+ "WHERE v.limite_aplicacao IS NOT NULL "
												+ "AND v.limite_aplicacao < TIMESTAMPDIFF(MONTH, "
												+ "    (SELECT data_nascimento FROM paciente WHERE id = ?), CURDATE())";

	try (Connection conexao = ConexaoBanco.getConnection();
						PreparedStatement comando = conexao.prepareStatement(sql)) {

					comando.setInt(1, idPaciente);

					try (ResultSet resultado = comando.executeQuery()) {
									if (resultado.next()) {
													estatistica = resultado.getInt("qtdVacinasNaoAplicaveis");
									}
					}
	}
	return estatistica;
}
}