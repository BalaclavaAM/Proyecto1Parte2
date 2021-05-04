package uniandes.dpoo.proyecto1.modelo.Restricciones;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;
import uniandes.dpoo.proyecto1.modelo.Registro.RequerimientoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import java.util.ArrayList;
import java.util.Map;

public class RestriccionNivel implements Restriccion{
    private final Nivel nivelS;

    public RestriccionNivel(Nivel nivelS){
        this.nivelS = nivelS;
    }


    @Override
    public boolean cumple(MallaCursos malla) {
        ArrayList<Requerimiento> Lreqs = malla.getPensum().getReqsXNivelTipo().get(nivelS).get("Obligatorio");
        for (Requerimiento req: Lreqs){
            if(!RestriccionReq.cumpleReq(malla, req.getNombre())){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean cumple(MallaCursos malla, Map<String, CursoRegistrado> cursosP) {
        return cumple(malla);
    }

    @Override
    public boolean cumple(MallaCursos malla, Periodo periodo) {
        ArrayList<Requerimiento> Lreqs = malla.getPensum().getReqsXNivelTipo().get(nivelS).get("Obligatorio");
        for (Requerimiento req: Lreqs){
            if(!RestriccionReq.cumpleReq(malla, periodo, req.getNombre())){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean cumple(MallaCursos malla, Map<String, CursoRegistrado> cursosP, Periodo periodo) {
        return cumple(malla,periodo);
    }

    @Override
    public String tipo() {
        return "Nivel";
    }
}
