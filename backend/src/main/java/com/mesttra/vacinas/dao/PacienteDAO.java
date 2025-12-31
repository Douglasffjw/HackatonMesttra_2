package com.mesttra.vacinas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mesttra.vacinas.config.ConexaoBanco;
import com.mesttra.vacinas.models.Paciente;
import com.mesttra.vacinas.models.Paciente.Sexo;

public class PacienteDAO {
	public static void adicionarPaciente(Paciente paciente) throws SQLException{
        String sql = "INSERT INTO paciente (nome, cpf, sexo, data_nascimento) VALUES (?, ?, ?, ?)";
        
        try (Connection conexao = ConexaoBanco.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            comando.setString(1, paciente.getNome());
            comando.setString(2, paciente.getCpf());
            comando.setString(3, paciente.getSexo().name()); 
            comando.setDate(4, new java.sql.Date(paciente.getDataNascimento().getTime()));
            
            comando.executeUpdate();
            
            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()) {
                    int idGerado = resultado.getInt(1);
                    paciente.setId(idGerado);
                    
                    System.out.println("Paciente inserido com ID: " + idGerado);
                }
            }
        }
    }	

	public static void alterarPaciente(Paciente paciente) throws SQLException {
		String sql = "UPDATE paciente SET nome = ?, cpf = ?, sexo = ?, data_nascimento = ? WHERE id = ?";

		try (Connection conexao = ConexaoBanco.getConnection();
							PreparedStatement comando = conexao.prepareStatement(sql)) {

						comando.setString(1, paciente.getNome());
						comando.setString(2, paciente.getCpf());
						comando.setString(3, paciente.getSexo().name());
						comando.setDate(4, new java.sql.Date(paciente.getDataNascimento().getTime()));
						comando.setInt(5, paciente.getId());

						comando.executeUpdate();
		}
}
	
	public static void excluirPaciente(int id) throws SQLException {
    	String sql = "DELETE FROM paciente WHERE id = ?";
    	
    	try (Connection conexao = ConexaoBanco.getConnection();
			 PreparedStatement comando = conexao.prepareStatement(sql)){
			
    		comando.setInt(1, id);
    		
    		comando.executeUpdate();
		}
    }
	
	public static List<Paciente> consultarTodosPacientes() throws SQLException{
    	List<Paciente> lista = new ArrayList<>();
    	
    	String sql = "SELECT * FROM paciente";
    	
    	try (Connection conexao = ConexaoBanco.getConnection();
    		 Statement comando = conexao.createStatement();
			 ResultSet resultado = comando.executeQuery(sql)) {
    		
    		while (resultado.next()) {
    			
    			lista.add(new Paciente(	
					resultado.getInt("id"),
					resultado.getString("nome"),
					resultado.getString("cpf"),
					Sexo.valueOf(resultado.getString("sexo")),
					resultado.getDate("data_nascimento")));   								   									
    		}    		    			    					
		}
    	return lista;
    }
	
				public static Paciente consultarPacientePorId(int id) throws SQLException {
					String sql = "SELECT * FROM paciente WHERE Id = ?";
					
					Paciente paciente = null;
					
					try (Connection conexao = ConexaoBanco.getConnection();
										PreparedStatement comando = conexao.prepareStatement(sql)) {
									
									comando.setInt(1, id);
									
									try (ResultSet resultado = comando.executeQuery()) {
													if (resultado.next()) {
																	Integer pacienteId = resultado.getInt("id");
																	String nome = resultado.getString("nome");
																	String cpf = resultado.getString("cpf");
																	String sexoStr = resultado.getString("sexo");
																	java.sql.Date dataNascimento = resultado.getDate("data_nascimento");
	
																	Sexo sexo = null;
																	if (sexoStr != null) {
																					try {
																									sexo = Sexo.valueOf(sexoStr);
																					} catch (IllegalArgumentException e) {
																									throw new SQLException("Sexo inválido no banco de dados: " + sexoStr);
																					}
																	}
	
																	paciente = new Paciente(pacienteId, nome, cpf, sexo, dataNascimento);
													}
									}
					}
					return paciente;
	}
}


