package uniandes.dpoo.proyecto1.modelo.Restricciones;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.ReqTipologia;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;

import java.util.ArrayList;

public class RestriccionNivel implements PreRestriccion {
    private final Nivel nivelS;

    public RestriccionNivel(Nivel nivelS){
        this.nivelS = nivelS;
    }


    @Override
    public boolean cumple(MallaCursos malla) {
        ArrayList<Requerimiento> Lreqs = malla.getPensum().getReqsXNivelTipo().get(nivelS).get(ReqTipologia.Obligatorio);
        for (Requerimiento req: Lreqs){
            if(!RestriccionReq.cumpleReq(malla, req.getNombre())){
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean cumple(MallaCursos malla, Periodo periodo) {
        ArrayList<Requerimiento> Lreqs = malla.getPensum().getReqsXNivelTipo().get(nivelS).get(ReqTipologia.Obligatorio);
        for (Requerimiento req: Lreqs){
            if(!RestriccionReq.cumpleReq(malla, periodo, req.getNombre())){
                return false;
            }
        }
        return true;
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
