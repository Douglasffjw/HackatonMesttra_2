package com.mesttra.vacinas;

import spark.Spark;

import com.mesttra.vacinas.controllers.*;
import com.mesttra.vacinas.services.PacienteService;

import spark.Request;
import spark.Response;
import spark.Route;

public class Main {
    
public static void main(String[] args) {
    

    try {
        Spark.options("/*", new Route() {
            @Override
            public Object handle(Request requisicaoHttp, Response respostaHttp) throws Exception {

                String accessControlRequestHeaders = requisicaoHttp.headers("Access-Control-Request-Headers");
                
                if (accessControlRequestHeaders != null) 
                    respostaHttp.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
                
                String accessControlRequestMethod = requisicaoHttp.headers("Access-Control-Request-Method");

                if (accessControlRequestMethod != null) 
                    respostaHttp.header("Access-Control-Allow-Methods", accessControlRequestMethod);
                
                return "OK";
            }
        });

        Spark.before(new spark.Filter() {
            @Override
            public void handle(Request requisicaoHttp, Response respostaHttp) throws Exception {
                respostaHttp.header("Access-Control-Allow-Origin", "*");
                respostaHttp.header("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
                respostaHttp.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
            }
        });

        // Configuração das rotas
        Spark.get("/paciente/consultar", PacienteService.readPacienteById());
        Spark.get("/pacientes", PacienteService.readPaciente());
        Spark.post("/paciente", PacienteService.createPaciente());
        Spark.put("/paciente/:id", PacienteService.updatePaciente());
        Spark.delete("/paciente/:id", PacienteService.deletePaciente());

        ImunizacaoController.getControllers();
        EstatisticasController.getControllers();
        PacienteController.getControllers();
        VacinaController.getControllers();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}