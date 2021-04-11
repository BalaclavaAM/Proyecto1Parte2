package uniandes.dpoo.proyecto1.modelo;

import java.util.ArrayList;
import java.util.Map;

public class HistoriaAcademica {
    private Pensum plan;
    private Map<String, ArrayList<CursoInscrito>> cursosPorSemestre;
    private int semestre;

    public HistoriaAcademica(Pensum plan){
        this.plan = plan;
        semestre = 1;
    }



    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }
}
