package com.mesttra.vacinas.controllers;

import static spark.Spark.*;

import com.mesttra.vacinas.services.EstatisticasService;


public class EstatisticasController {

    public static void getControllers(){

        //Qtde de vacinas aplicadas por paciente 
        get("/estatisticas/imunizacoes/paciente/:id", EstatisticasService.readEstatisticasImunizacoesPorPaciente());

        //Qtde de vacinas aplicáveis no próximo mês por paciente
        get("/estatisticas/proximas_imunizacoes/paciente/:id", EstatisticasService.readQtdeProximasImunizacoes());

        //Qtde de vacinas atrasadas 
        get("/estatisticas/imunizacoes_atrasadas/paciente/:id", EstatisticasService.readQtdeVacinasAtrasadas());

        //Qtde de vacinas acima de uma determinda idade
        get("/estatisticas/imunizacoes/idade_maior/:meses", EstatisticasService.readQtdeVacinasAcimaDeIdade());

        //Qtde de vacinas não aplicáveis
        get("/estatisticas/vacinas/nao_aplicaveis/paciente/:id", EstatisticasService.readQtdeVacinasNaoAplicaveis());

    }
}