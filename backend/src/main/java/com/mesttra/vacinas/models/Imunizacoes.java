package com.mesttra.vacinas.models;

import java.sql.Date;

public class Imunizacoes {
    private int id;
    private int id_paciente;
    private int id_dose;
    private Date data_aplicacao;
    private String fabricante;
    private String lote;
    private String local_aplicacao;
    private String profissional_aplicador;

    public Imunizacoes(int id, int id_paciente, int id_dose, Date data_aplicacao, String fabricante, String lote, String local_aplicacao, String profissional_aplicador) {
        this.id = id;
        this.id_paciente = id_paciente;
        this.id_dose = id_dose;
        this.data_aplicacao = data_aplicacao;
        this.fabricante = fabricante;
        this.lote = lote;
        this.local_aplicacao = local_aplicacao;
        this.profissional_aplicador = profissional_aplicador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    // -----

    public int getId_paciente(){
        return id_paciente;
    }

    public void setId_paciente(int id_paciente){
        this.id_paciente = id_paciente;
    }
    // ------

    public int getId_dose(){
        return id_dose;
    }

    public void setId_dose(int id_dose){
        this.id_dose = id_dose;
    }
    // -----

    public Date getData_aplicacao() {
        return data_aplicacao;
    }

    public void setData_aplicacao(Date data_aplicacao){
        this.data_aplicacao = data_aplicacao;
    }
    // -----

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
    // ------

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }
    // ----

    public String getLocal_aplicacao() {
        return local_aplicacao;
    }

    public void setLocal_aplicacao(String local_aplicacao) {
        this.local_aplicacao = local_aplicacao;
    }
    // -----

    public String getProfissional_aplicador() {
        return profissional_aplicador;
    }

    public void setProfissional_aplicador(String profissional_aplicador) {
        this.profissional_aplicador = profissional_aplicador;
    }
}