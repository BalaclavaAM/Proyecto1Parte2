package uniandes.dpoo.proyecto1.modelo.Cursos_Req.Restricciones;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import java.util.ArrayList;

public class Prerrequisito implements Restriccion {
    private final ArrayList<String> opciones; // esto es un supuesto

    public Prerrequisito(ArrayList<String> opciones){
        this.opciones = opciones;
    }


    @Override
    public boolean cumple(Plan plan) {

        HistoriaAcademica h = plan.getHistoria();
        if(cumple(h)){
            return true;
        }
        for(String codigo: opciones){
            if (h.getCursosInscritos().containsKey(codigo)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean cumple(Plan plan, ArrayList<Curso> cursos) {
        return cumple(plan);
    }


    @Override
    public boolean cumple(Plan plan, Periodo periodo) {
        if(cumple(plan.getHistoria())){
            return true;
        }
        for(String codigo: opciones) {
            CursoRegistrado cursoR = plan.getCursosRegistrados().get(codigo);
            if (cursoR != null && cursoR.getPeriodo().compare(periodo) < 0) {
                return true;
                }
        }
        return false;
    }


    @Override
    public boolean cumple(Plan plan, ArrayList<Curso> cursos, Periodo periodo) {
        return cumple(plan,periodo);
    }


    @Override
    public boolean cumple(HistoriaAcademica historia) { //para inscripcion
        for (String codigo : opciones) {
            CursoRegistrado cursoR = historia.getCursosRegistrados().get(codigo);
            if (cursoR != null && cursoR.getNota().aprobo()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean cumple(HistoriaAcademica historia, ArrayList<Curso> cursos) {
        return cumple(historia);
    }

    @Override
    public boolean cumple(HistoriaAcademica historia, Periodo periodo) {
        for (String codigo : opciones) {
            CursoRegistrado cursoR = historia.getCursosRegistrados().get(codigo);
            if (cursoR != null && cursoR.getNota().aprobo() && cursoR.getPeriodo().compare(periodo) < 0) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean cumple(HistoriaAcademica historiaAcademica, ArrayList<Curso> cursos, Periodo periodo) {
        return cumple(historiaAcademica, periodo);
    }

    @Override
    public String tipo() {
        return "Prerrequisito";
    }


}
