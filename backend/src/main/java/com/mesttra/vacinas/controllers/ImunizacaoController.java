package com.mesttra.vacinas.controllers;

import static spark.Spark.*;

import com.mesttra.vacinas.services.ImunizacaoService;

public class ImunizacaoController {

    public static void getControllers() {

        // Criar uma nova imunização
        post("/imunizacoes/inserir", ImunizacaoService.createImunizacao());

        // Alterar uma imunização ja existente buscando pelo ID
        put("/imunizacao/alterar/:id", ImunizacaoService.updateImunizacao());

        // Consultar todas as imunizações cadastradas
        get("/imunizacao/consultar", ImunizacaoService.readImunizacoes());

        // Consultar uma imunização específica por ID
        get("/imunizacoes/consultarPorDose", ImunizacaoService.readImunizacaoByDoseId());
       
        // Consultar imunizações de um paciente específico
        get("/imunizacoes/consultar/paciente/:id", ImunizacaoService.readImunizacoesByPaciente());

        // Consultar imunizações por ID do paciente e intervalo de datas
        get("/imunizacoes/consultar/paciente/:id/aplicacao/:dt_ini/:dt_fim", ImunizacaoService.readImunizacoesByPacienteAndDate());

        // Excluir uma imunização em  específico
        delete("/imunizacoes/excluirPorDose/:id_dose", ImunizacaoService.deleteImunizacaoByDoseId());

        // Excluir todas as imunizações de um paciente específico pelo ID
        delete("/imunizacao/excluir/paciente/:id", ImunizacaoService.deleteImunizacoesByPaciente());
    }
}
