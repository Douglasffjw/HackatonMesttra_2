package com.mesttra.vacinas.dto;

import java.sql.Date;

public class DTOImunizacaoDosePaciente {
	public int id;
	public String nome;
	public String vacina;
	public String dose;
	public Date data_aplicacao;
	public String fabricante;
    public String lote;
    public String local_aplicacao;
    public String profissional_aplicador;
    
	public DTOImunizacaoDosePaciente(int id, String nome, String vacina, String dose, Date data_aplicacao,
			String fabricante, String lote, String local_aplicacao, String profissional_aplicador) {
		
		this.id = id;
		this.nome = nome;
		this.vacina = vacina;
		this.dose = dose;
		this.data_aplicacao = data_aplicacao;
		this.fabricante = fabricante;
		this.lote = lote;
		this.local_aplicacao = local_aplicacao;
		this.profissional_aplicador = profissional_aplicador;
	}

 public DTOImunizacaoDosePaciente(int id2, int pacienteId, int doseId, Date dataAplicacao, String fabricante2,
   String lote2, String localAplicacao, String profissionalAplicador) {
  //TODO Auto-generated constructor stub
 }

 public void setPacienteId(String queryParams) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'setPacienteId'");
 }

 public void setVacinaId(String queryParams) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'setVacinaId'");
 }

 public void setDataAplicacao(String queryParams) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'setDataAplicacao'");
 }

 public String getData_aplicacao() {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'getData_aplicacao'");
 }

 public String getFabricante() {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'getFabricante'");
 }

 public String getLote() {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'getLote'");
 }

 public String getProfissional_aplicador() {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'getProfissional_aplicador'");
 }

 public int getId() {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'getId'");
 }

 public int getIdPaciente() {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'getIdPaciente'");
 }	
    
    
}
