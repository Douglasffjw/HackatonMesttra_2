package com.mesttra.vacinas.controllers;

import static spark.Spark.*;

import com.mesttra.vacinas.services.VacinaService;

public class VacinaController {

    public static void getControllers(){

        //Consultar todas as vacinas
        get("/vacinas/consultar", VacinaService.readVacinas());

        //Consultar todas as vacinas para uma determinada faixa etária
        get("/vacinas/consultar/faixa_etaria/:faixa", VacinaService.readVacinasPorFaixaEtaria());

        //Consultar todas as vacinas recomendadas acima de uma idade
        get("/vacinas/consultar/idade_maior/:meses", VacinaService.readVacinasPorIdadeMaior());

        //Consultar todas as vacinas não aplicáveis para um determinado paciente
        get("/vacinas/consultar/nao_aplicaveis/paciente/:id", VacinaService.readVacinasNaoAplicaveis());
        
    }

}


