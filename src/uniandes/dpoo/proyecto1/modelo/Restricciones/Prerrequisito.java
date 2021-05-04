package uniandes.dpoo.proyecto1.modelo.Restricciones;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import java.util.ArrayList;
import java.util.Map;

public class Prerrequisito implements Restriccion {
    private final ArrayList<String> opciones; // esto es un supuesto

    public Prerrequisito(ArrayList<String> opciones){
        this.opciones = opciones;
    }


    @Override
    public boolean cumple(MallaCursos malla) {
        for(String codigo: opciones) {
            CursoRegistrado cursoR = malla.getCurReg(codigo);
            if (cursoR != null && (malla.aprovado(cursoR))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean cumple(MallaCursos malla, Map<String, CursoRegistrado> cursosP) {
        return cumple(malla);
    }

    @Override
    public boolean cumple(MallaCursos malla, Periodo periodo) {
        for(String codigo: opciones) {
            CursoRegistrado cursoR = malla.getCurReg(codigo);
            if (cursoR != null && (malla.aprovado(cursoR) && cursoR.getPeriodo().compare(periodo) < 0)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean cumple(MallaCursos malla, Map<String, CursoRegistrado> cursosP, Periodo periodo) {
        if(periodo == malla.getPeriodo()){
            return cumple(malla);
        }
        return cumple(malla,periodo);
    }

    @Override
    public String tipo() {
        return "Prerrequisito";
    }

}
