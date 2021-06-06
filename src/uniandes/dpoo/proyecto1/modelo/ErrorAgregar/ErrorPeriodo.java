package uniandes.dpoo.proyecto1.modelo.ErrorAgregar;

import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;

import java.util.ArrayList;

public class ErrorPeriodo implements ErrorAgregar{
    private Periodo periodo;
    private ArrayList<CursoRegistrado> cursosP;

    public ErrorPeriodo(Periodo periodo, ArrayList<CursoRegistrado> cursosP){
        this.periodo = periodo;
        this.cursosP = cursosP;
    }

    @Override
    public String getTipe() {
        return "Periodo";
    }

    @Override
    public String getMesage() {
        return String.format("El periodo %s es incosistente no se pudo agregar %s",periodo.periodoS(),listNC());
    }

    private String listNC(){
        StringBuilder text = new StringBuilder();
        text.append(cursosP.get(0).getCurso().getCodigo());
        for (int i = 0; i <cursosP.size()-1; i++) {
            text.append(",").append(cursosP.get(i).getCurso().getCodigo());
        }
        return text.toString();
    }
}
