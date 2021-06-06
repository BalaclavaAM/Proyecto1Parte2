package uniandes.dpoo.proyecto1.modelo.Restricciones;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.ReqTipologia;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.EstadoAgregar;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RestriccionNivel implements Restriccion {
    private final Nivel nivelS;

    public RestriccionNivel(Nivel nivelS){
        this.nivelS = nivelS;
    }



    public boolean cumple(CursoRegistrado cursoR,MallaCursos malla, Periodo periodo) {
        if(nivelS != Nivel.CERO) {
            ArrayList<Requerimiento> Lreqs = malla.getPensum().getReqsXNivelTipo().get(nivelS).get(ReqTipologia.Obligatorio);
            for (Requerimiento req : Lreqs) {
                if (!RestriccionReq.cumpleReq(malla, periodo, req.getNombre())) {
                    return false;
                }
            }
        }
        //hacer dependecia
        return true;
    }


    public static ArrayList<CursoRegistrado> cursosCumple(ArrayList<CursoRegistrado> cursosP, MallaCursos malla,
                                                   Periodo periodo,ArrayList<EstadoAgregar> estado) {

        for (int i = cursosP.size() - 1; i > -1; i--) {
            CursoRegistrado cursoR = cursosP.get(i);
            RestriccionNivel rn = cursoR.getCurso().getRestriccionNivel();
            if(! rn.cumple(cursoR,malla,periodo)){
                cursosP.remove(i);
                estado.add(new EstadoAgregar(cursoR, rn.nombre()));
            }
        }
        return cursosP;
    }



    @Override
    public String tipo() {
        return "Nivel";
    }

    @Override
    public String nombre() {
        return "restriccion " + nivelS;
    }
}
