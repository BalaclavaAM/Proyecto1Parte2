package uniandes.dpoo.proyecto1.modelo.Restricciones;

import uniandes.dpoo.proyecto1.modelo.ErrorAgregar.ErrorAgregar;
import uniandes.dpoo.proyecto1.modelo.ErrorAgregar.ErrorRestriccion;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.EstadoAgregar;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;

import java.util.ArrayList;

public class Prerrequisito implements Restriccion{
    private final ArrayList<String> opciones; // esto es un supuesto

    public Prerrequisito(ArrayList<String> opciones){
        this.opciones = opciones;
    }

    public boolean cumple(MallaCursos malla) {
        for(String codigo: opciones) {
            CursoRegistrado cursoR = malla.getCurReg(codigo);
            if (cursoR != null && (malla.aprovado(cursoR))) {
                return true;
            }
        }
        return false;
    }


    public CursoRegistrado cumple(MallaCursos malla, Periodo periodo) {
        for(String codigo: opciones) {
            CursoRegistrado registro = malla.getCurReg(codigo);
            if (registro != null && (malla.aprovado(registro) && registro.getPeriodo().compare(periodo) < 0)) {
                return registro;
            }
        }
        return null;
    }

    public static void cursosCumple(ArrayList<CursoRegistrado> cursosP, MallaCursos malla,Periodo periodo,
                                    ArrayList<ErrorAgregar> estado) {

        for (int i = cursosP.size() - 1; i > -1; i--) {
            CursoRegistrado cr = cursosP.get(i);
            for (Prerrequisito pre : cr.getCurso().getPrerrequisitos()) {
                CursoRegistrado dependencia = pre.cumple(malla, periodo);
                if (dependencia != null) {
                    //vincular cursos
                } else {
                    cursosP.remove(i);
                    estado.add(new ErrorRestriccion(pre,cr));
                    break;
                }
            }
        }
    }

    @Override
    public String tipo() {
        return "Prerrequisito";
    }

    @Override
    public String nombre() {
        return null;
    }

}
