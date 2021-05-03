package uniandes.dpoo.proyecto1.modelo.Restricciones;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;
import uniandes.dpoo.proyecto1.modelo.Registro.RequerimientoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import java.util.ArrayList;

public class RestriccionNivel implements Restriccion{
    private final Nivel nivelS;

    public RestriccionNivel(Nivel nivelS){
        this.nivelS = nivelS;
    }

    @Override
    public boolean cumple(Plan plan) {
        return false;
    }

    @Override
    public boolean cumple(Plan plan, ArrayList<Curso> cursos) {
        return false;
    }

    @Override
    public boolean cumple(Plan plan, Periodo periodo) {
        ArrayList<Requerimiento> Lreqs = plan.getHistoria().getPensum().getReqsXNivelTipo().get(nivelS).get("Obligatorio");
        for (Requerimiento req: Lreqs){
            if(!RestriccionReq.cumpleReq(plan,periodo, req.getNombre())){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean cumple(Plan plan, ArrayList<Curso> cursos, Periodo periodo) {
        return cumple(plan,periodo);
    }

    @Override
    public boolean cumple(HistoriaAcademica historia) {
        return false;
    }

    @Override
    public boolean cumple(HistoriaAcademica historia, ArrayList<Curso> cursos) {
        return false;
    }

    @Override
    public boolean cumple(HistoriaAcademica historia, Periodo periodo) {
        ArrayList<Requerimiento> Lreqs = historia.getPensum().getReqsXNivelTipo().get(nivelS).get("Obligatorio");
        for (Requerimiento req: Lreqs){
            RequerimientoRegistrado rR =  historia.getReqsRegistrados().get(req.getNombre());
            if(rR == null || rR.ultimoPeriodo().compare(periodo) != -1 || !rR.cumplio()){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean cumple(HistoriaAcademica historiaAcademica, ArrayList<Curso> cursos, Periodo periodo) {
        return false;
    }

    @Override
    public String tipo() {
        return "Nivel";
    }
}
