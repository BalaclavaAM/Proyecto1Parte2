package uniandes.dpoo.proyecto1.modelo.Restricciones;

import uniandes.dpoo.proyecto1.modelo.ErrorAgregar.ErrorAgregar;
import uniandes.dpoo.proyecto1.modelo.ErrorAgregar.ErrorRestriccion;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;

import java.util.ArrayList;

public class RestriccionReq implements Restriccion {
    private final String reqN;

    public RestriccionReq(String reqN){
        this.reqN = reqN;
    }


    public static boolean cumpleReq(MallaCursos malla, Periodo periodo, String reqN){
        return malla.itemsCumplidos(reqN,periodo) > malla.getPensum().getRequerimientos().get(reqN).getItems();
    }

    public boolean cumple(CursoRegistrado cursoR,MallaCursos malla, Periodo periodo) {
        return cumpleReq(malla,periodo,reqN);
    }

    private static RestriccionReq cumpleTodos(CursoRegistrado cursoR,MallaCursos malla, Periodo periodo){
        for(RestriccionReq rn: cursoR.getCurso().getRestriccionesReqs()){
            if(!rn.cumple(cursoR,malla,periodo)){
                return rn;
            }
        }
        return null;
    }


    public static void cursosCumple(ArrayList<CursoRegistrado> cursosP, MallaCursos malla,
                                    Periodo periodo, ArrayList<ErrorAgregar> estado) {

        for (int i = cursosP.size() - 1; i > -1; i--) {
            CursoRegistrado cursoR = cursosP.get(i);
            RestriccionReq incumplida = cumpleTodos(cursoR,malla,periodo);
            if(incumplida != null){
                cursosP.remove(i);
                estado.add(new ErrorRestriccion(incumplida,cursoR));
            }
        }
    }

    @Override
    public String tipo() {
        return "Restriccion Requerimiento";
    }

    @Override
    public String nombre() {
        return tipo() + " " + reqN ;
    }
}
