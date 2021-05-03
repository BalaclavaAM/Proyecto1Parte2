package uniandes.dpoo.proyecto1.modelo.Restricciones;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.EstadoCurso;
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import java.util.ArrayList;

public class Correquisito implements Restriccion {
    private final ArrayList<String> opciones;

    public  Correquisito(ArrayList<String> opciones){
        this.opciones = opciones;
    }

    @Override
    public boolean cumple(Plan plan) {
            HistoriaAcademica h = plan.getHistoria();
            if (cumple(h)) {
                return true;
            }
            for (String codigo : opciones) {
                if (plan.getCursosRegistrados().containsKey(codigo))
                    return true;
            }
            return false;
    }

    @Override
    public boolean cumple(Plan plan, ArrayList<Curso> cursos) {
        if (cumple(plan)){
            return true;
        }
        for(String codigo: opciones){
            for (Curso c:cursos) {
                String codigoOp = c.getCodigo();
                if(codigoOp.equals(codigo) && plan.agregarCursoxPeriodo(c, plan.notaPlan, plan.estadoPl,false,cursos, plan.getPeriodo()) == 1){
                    // esto peta si por algun razon alguien pone de correquisito al mismo curso
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean cumple (Plan plan, Periodo periodo){
        for (String codigo : opciones) {
            CursoRegistrado cursoR = plan.getCursosRegistrados().get(codigo);
            if (cursoR != null && cursoR.getPeriodo().compare(periodo) <= 0) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean cumple (Plan plan, ArrayList < Curso > cursos, Periodo periodo){
        if(cumple(plan, periodo)){
            return true;
        }
        for(String codigo: opciones){
            for (Curso c:cursos) {
                String codigoOp = c.getCodigo();
                if(codigoOp.equals(codigo) && plan.agregarCursoxPeriodo(c, plan.notaPlan, plan.estadoPl,false ,cursos, periodo) == 1){
                    // esto peta si por algun razon alguien pone de correquisito al mismo curso
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public boolean cumple (HistoriaAcademica historia){ //para Inscripsion
        for (String codigo : opciones) {
            CursoRegistrado cursoR = historia.getCursosRegistrados().get(codigo);
            if (cursoR != null){
                if(cursoR.getNota().aprobo() || historia.getCursosInscritos().containsKey(codigo)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean cumple(HistoriaAcademica historia, ArrayList<Curso> cursos) {
        return false;
    }

    @Override
    public boolean cumple (HistoriaAcademica historia, Periodo periodo){
        for (String codigo : opciones) {
            CursoRegistrado cursoR = historia.getCursosRegistrados().get(codigo);
            if (cursoR != null && ((cursoR.getNota().aprobo() && cursoR.getPeriodo().compare(periodo) > 0) ||
                    (cursoR.getPeriodo().compare(periodo) == 0))) {
                return true;
            }
            if (historia.getCursosInscritos().containsKey(codigo)) {
                return true;
                }
        }
        return false;
    }

    @Override
    public boolean cumple (HistoriaAcademica historia, ArrayList < Curso > cursos, Periodo periodo){
        if(cumple(historia, periodo)){
            return true;
        }
        for(String codigo: opciones){
            for (Curso c:cursos) {
                String codigoOp = c.getCodigo();
                if(codigoOp.equals(codigo) && historia.agregarCursoxPeriodo(c, historia.notaP,EstadoCurso.Pendiente,false,cursos,periodo) == 1){ //se que se repite la revision;
                    // esto peta si por algun razon alguien pone de correquisito al mismo curso
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String tipo () {
        return "Correquisito";
    }

}