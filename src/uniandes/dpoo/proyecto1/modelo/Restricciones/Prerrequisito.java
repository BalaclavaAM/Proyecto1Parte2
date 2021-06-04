package uniandes.dpoo.proyecto1.modelo.Restricciones;

import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;

import java.util.ArrayList;

public class Prerrequisito implements PreRestriccion {
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
    public boolean cumple(CursoRegistrado cursoR, MallaCursos malla, Periodo periodo) {
        for(String codigo: opciones) {
            CursoRegistrado registro = malla.getCurReg(codigo);
            if (registro != null && (malla.aprovado(registro) && registro.getPeriodo().compare(periodo) < 0)) {
                return true;
            }
        }
        return false;
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
