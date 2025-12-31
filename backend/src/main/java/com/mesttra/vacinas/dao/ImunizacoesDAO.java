package com.mesttra.vacinas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.mesttra.vacinas.config.ConexaoBanco;
import com.mesttra.vacinas.dto.DTOImunizacaoDosePaciente;
import com.mesttra.vacinas.models.Imunizacoes;

public class ImunizacoesDAO {
	public static void adicionarImunizacao(Imunizacoes imunizacao) throws SQLException{
								String sql = "INSERT INTO imunizacoes (id_paciente, id_dose, data_aplicacao, fabricante, lote, "
										+ "local_aplicacao, profissional_aplicador) VALUES (?, ?, ?, ?, ?, ?, ?)";
								
								try (Connection conexao = ConexaoBanco.getConnection();
													PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
									comando.setInt(1, imunizacao.getId_paciente());  
												comando.setInt(2, imunizacao.getId_dose());     
												comando.setDate(3, new java.sql.Date(imunizacao.getData_aplicacao().getTime()));
												comando.setString(4, imunizacao.getFabricante());
												comando.setString(5, imunizacao.getLote()); 
												comando.setString(6, imunizacao.getLocal_aplicacao());
												comando.setString(7, imunizacao.getProfissional_aplicador());
												
												comando.executeUpdate();
												
												try (ResultSet resultado = comando.getGeneratedKeys()) {
																if (resultado.next()) {
																				int idGerado = resultado.getInt(1);
																				imunizacao.setId(idGerado);
																				
																				System.out.println("Imunização inserida com ID: " + idGerado);
																}
												}
								}
				}
	
				public static void alterarImunizacao(DTOImunizacaoDosePaciente imunizacaoDb) throws SQLException, ParseException {
					String sql = "UPDATE imunizacoes SET data_aplicacao = ?, fabricante = ?, lote = ?, local_aplicacao = ?, profissional_aplicador = ? WHERE id = ?";

					try (Connection conexao = ConexaoBanco.getConnection();
										PreparedStatement comando = conexao.prepareStatement(sql)) {

									java.util.Date dataAplicacao = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(imunizacaoDb.getData_aplicacao());
									comando.setDate(1, new java.sql.Date(dataAplicacao.getTime()));
									comando.setString(2, imunizacaoDb.getFabricante());
									comando.setString(3, imunizacaoDb.getLote());
									comando.setString(4, imunizacaoDb.getData_aplicacao());
									comando.setString(5, imunizacaoDb.getProfissional_aplicador());
									comando.setInt(6, imunizacaoDb.getId());

									comando.executeUpdate();
					}
	}
	
	public static void excluirImunizacaoPorDoseId(int idDose) throws SQLException {
        String sql = "DELETE FROM imunizacoes WHERE id_dose = ?";

        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, idDose);
            comando.executeUpdate();
        }
    }
	
	public static void excluirTodasImunizacoesDoPaciente(int idPaciente) throws SQLException {
    	String sql = "DELETE FROM imunizacoes WHERE id_paciente = ?";
    	
    	try (Connection conexao = ConexaoBanco.getConnection();
			 PreparedStatement comando = conexao.prepareStatement(sql)){
			
    		comando.setInt(1, idPaciente);
    		
    		comando.executeUpdate();
		}
    }
	
	public static List<DTOImunizacaoDosePaciente> consultarTodasImunizacoes() throws SQLException{
    	List<DTOImunizacaoDosePaciente> lista = new ArrayList<>();
    	
    	String sql = "SELECT "
    			+ "i.id, "
                + "p.nome, "
                + "v.vacina, "
                + "d.dose, "
                + "i.data_aplicacao, "
                + "i.fabricante, "
                + "i.lote, "
                + "i.local_aplicacao, "
                + "i.profissional_aplicador "
                + "FROM imunizacoes i "
                + "JOIN paciente p ON i.id_paciente = p.id "
                + "JOIN dose d ON i.id_dose = d.id "
                + "JOIN vacina v ON d.id_vacina = v.id";
    	
    	try (Connection conexao = ConexaoBanco.getConnection();
    		 Statement comando = conexao.createStatement();
			 ResultSet resultado = comando.executeQuery(sql)) {
    		
    		while (resultado.next()) {
    			
    			lista.add(new DTOImunizacaoDosePaciente(	
					resultado.getInt("id"),
					resultado.getString("nome"),
					resultado.getString("vacina"),
					resultado.getString("dose"),
					resultado.getDate("data_aplicacao"),
					resultado.getString("fabricante"),
					resultado.getString("lote"),
					resultado.getString("local_aplicacao"),
					resultado.getString("profissional_aplicador")));
					   								   									
    		}    		    			    					
		}
    	return lista;
    }
	
				public static DTOImunizacaoDosePaciente consultarImunizacaoPorIdDose(int idDose) throws SQLException {
					DTOImunizacaoDosePaciente imunizacao = null;
					
					String sql = "SELECT "
													+ "i.id, "
													+ "p.nome, "
													+ "v.vacina, "
													+ "d.dose, "
													+ "i.data_aplicacao, "
													+ "i.fabricante, "
													+ "i.lote, "
													+ "i.local_aplicacao, "
													+ "i.profissional_aplicador "
													+ "FROM imunizacoes i "
													+ "JOIN paciente p ON i.id_paciente = p.id "
													+ "JOIN dose d ON i.id_dose = d.id "
													+ "JOIN vacina v ON d.id_vacina = v.id "
													+ "WHERE i.id_dose = ?";
					
					try (Connection conexao = ConexaoBanco.getConnection();
										PreparedStatement comando = conexao.prepareStatement(sql)) {
									
									comando.setInt(1, idDose);
									
									try (ResultSet resultado = comando.executeQuery()) {
													if (resultado.next()) {
																	imunizacao = new DTOImunizacaoDosePaciente(
																									resultado.getInt("id"),
																									resultado.getString("nome"),
																									resultado.getString("vacina"),
																									resultado.getString("dose"),
																									resultado.getDate("data_aplicacao"),
																									resultado.getString("fabricante"),
																									resultado.getString("lote"),
																									resultado.getString("local_aplicacao"),
																									resultado.getString("profissional_aplicador"));
													}
									}
					}
					
					return imunizacao;
	}
	
	public static List<DTOImunizacaoDosePaciente> consultarImunizacoesPorIdPaciente(int idPaciente) throws SQLException {
		String sql = "SELECT "
										+ "i.id, "
										+ "p.nome, "
										+ "v.vacina, "
										+ "d.dose, "
										+ "i.data_aplicacao, "
										+ "i.fabricante, "
										+ "i.lote, "
										+ "i.local_aplicacao, "
										+ "i.profissional_aplicador "
										+ "FROM imunizacoes i "
										+ "JOIN paciente p ON i.id_paciente = p.id "
										+ "JOIN dose d ON i.id_dose = d.id "
										+ "JOIN vacina v ON d.id_vacina = v.id "
										+ "WHERE i.id_paciente = ?";

		List<DTOImunizacaoDosePaciente> imunizacoes = new ArrayList<>();

		try (Connection conexao = ConexaoBanco.getConnection();
							PreparedStatement comando = conexao.prepareStatement(sql)) {

						comando.setInt(1, idPaciente);

						try (ResultSet resultado = comando.executeQuery()) {
										while (resultado.next()) {
														DTOImunizacaoDosePaciente imunizacao = new DTOImunizacaoDosePaciente(
																						resultado.getInt("id"),
																						resultado.getString("nome"),
																						resultado.getString("vacina"),
																						resultado.getString("dose"),
																						resultado.getDate("data_aplicacao"),
																						resultado.getString("fabricante"),
																						resultado.getString("lote"),
																						resultado.getString("local_aplicacao"),
																						resultado.getString("profissional_aplicador")
														);
														imunizacoes.add(imunizacao);
										}
						}
		}

		return imunizacoes;
}
	
public static List<DTOImunizacaoDosePaciente> consultarImunizacoesPorIdPacienteEPeriodo(int idPaciente, java.sql.Date dataInicio, java.sql.Date dataFim) throws SQLException {
	String sql = "SELECT "
									+ "i.id, "
									+ "p.nome, "
									+ "v.vacina, "
									+ "d.dose, "
									+ "i.data_aplicacao, "
									+ "i.fabricante, "
									+ "i.lote, "
									+ "i.local_aplicacao, "
									+ "i.profissional_aplicador "
									+ "FROM imunizacoes i "
									+ "JOIN paciente p ON i.id_paciente = p.id "
									+ "JOIN dose d ON i.id_dose = d.id "
									+ "JOIN vacina v ON d.id_vacina = v.id "
									+ "WHERE i.id_paciente = ? AND i.data_aplicacao BETWEEN ? AND ?";

	List<DTOImunizacaoDosePaciente> imunizacoes = new ArrayList<>();

	try (Connection conexao = ConexaoBanco.getConnection();
						PreparedStatement comando = conexao.prepareStatement(sql)) {

					comando.setInt(1, idPaciente);
					comando.setDate(2, dataInicio);
					comando.setDate(3, dataFim);

					try (ResultSet resultado = comando.executeQuery()) {
									while (resultado.next()) {
													DTOImunizacaoDosePaciente imunizacao = new DTOImunizacaoDosePaciente(
																					resultado.getInt("id"),
																					resultado.getString("nome"),
																					resultado.getString("vacina"),
																					resultado.getString("dose"),
																					resultado.getDate("data_aplicacao"),
																					resultado.getString("fabricante"),
																					resultado.getString("lote"),
																					resultado.getString("local_aplicacao"),
																					resultado.getString("profissional_aplicador")
													);
													imunizacoes.add(imunizacao);
									}
					}
	}

	return imunizacoes;
}

}
