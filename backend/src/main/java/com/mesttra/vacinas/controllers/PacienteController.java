package com.mesttra.vacinas.controllers;

import static spark.Spark.*;

import com.mesttra.vacinas.services.PacienteService;

public class PacienteController {

    public static void getControllers() {
        // Criar um novo paciente
        post("/paciente/inserir", PacienteService.createPaciente());
        
        // Consultar paciente por ID
        get("/paciente/consultar/:id", PacienteService.readPacienteById());

        // Consultar todos os pacientes
        get("/pacientes", PacienteService.readPaciente());

        // Alterar um paciente 
        put("/paciente/alterar/:id", PacienteService.updatePaciente());

        // Excluir um paciente 
        delete("/paciente/excluir/:id", PacienteService.deletePaciente());
    }   
}