package com.mesttra.vacinas.models;

import com.mesttra.vacinas.models.enums.PublicoAlvo;

public class Vacina {

    private int id;
    private String vacina;
    private String descricao;
    private int limite_aplicacao;
    private PublicoAlvo  publico_alvo;

    public Vacina(int id, String vacina, String descricao, int limite_aplicacao, PublicoAlvo publico_alvo){
        this.id = id;
        this.vacina = vacina;
        this.descricao = descricao;
        this.limite_aplicacao = limite_aplicacao;
        this.publico_alvo = publico_alvo;
    }

    public Vacina(int int1, String string, String string2, String string3) {
     
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVacina() {
        return vacina;
    }

    public void setVacina(String vacina) {
        this.vacina = vacina;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getLimite_aplicacao() {
        return limite_aplicacao;
    }

    public void setLimite_aplicacao(int limite_aplicacao) {
        this.limite_aplicacao = limite_aplicacao;
    }

    public PublicoAlvo getPublico_alvo(){
        return publico_alvo;
    }

    public void setPublico_alvo(PublicoAlvo publico_alvo){
        this.publico_alvo = publico_alvo;
    }
}