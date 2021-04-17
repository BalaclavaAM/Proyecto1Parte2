package uniandes.dpoo.proyecto1.modelo;

import java.util.Map;

public class Plan {
    Map<nivel, Map<String, RegistroRequerimiento>> reqOblXnivel;
    private Map<String, CursoVisto> cursosvistos;
    private int semestre;

    public Plan(HistoriaAcademica historia){
        this.reqOblXnivel = historia.getReqOblXnivel();
        this.cursosvistos = historia.getCursosvistos();

    }

}
