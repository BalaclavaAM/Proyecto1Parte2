package uniandes.dpoo.proyecto1.modelo.Restricciones;

import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;

import java.util.ArrayList;
import java.util.Map;

public class Correquisito implements Restriccion {
    private final ArrayList<String> opciones;

    public Correquisito(ArrayList<String> opciones) {
        this.opciones = opciones;
    }


    @Override
    public boolean cumple(MallaCursos malla) {
        for (String codigo : opciones) {
            CursoRegistrado cursoR = malla.getCurReg(codigo);
            System.out.println(codigo);
            if (cursoR != null){
                System.out.println(codigo);
                if(malla.aprovado(cursoR)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public boolean cumple(MallaCursos malla, Map<String, CursoRegistrado> cursosP) {
        if (cumple(malla)) {
            return true;
        }
        for (String codigo : opciones) {
            if (cursosP.containsKey(codigo)) {
                if (malla.agregarCurso(cursosP.get(codigo), cursosP)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean cumple(MallaCursos malla, Periodo periodo) {
        for (String codigo : opciones) {
            CursoRegistrado cursoR = malla.getCurReg(codigo);
            if (cursoR != null){
                if((malla.aprovado(cursoR) && cursoR.getPeriodo().compare(periodo) < 0) ||
                        (cursoR.getPeriodo().compare(periodo) == 0)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public boolean cumple(MallaCursos malla, Map<String, CursoRegistrado> cursosP, Periodo periodo) {
        if(periodo == malla.getPeridoSistema()){
            return cumple(malla, cursosP);
        }
        if(cumple(malla, periodo)){
            return true;
        }
        for(String codigo: opciones){
            if(cursosP.containsKey(codigo)){
                if(malla.agregarCurso(cursosP.get(codigo), cursosP)){
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