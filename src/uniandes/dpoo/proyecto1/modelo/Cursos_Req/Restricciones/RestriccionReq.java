package uniandes.dpoo.proyecto1.modelo.Cursos_Req.Restricciones;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;
import uniandes.dpoo.proyecto1.modelo.Registro.RequerimientoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import java.util.ArrayList;

public class RestriccionReq implements Restriccion{
    private String reqN;

    public RestriccionReq(String reqN){
        this.reqN = reqN;
    }

    @Override
    public boolean cumple(Plan plan) {
        HistoriaAcademica h = plan.getHistoria();
        RequerimientoRegistrado rR =  h.getReqsRegistrados().get(reqN);
        int itemsC = 0;
        if(rR != null){
            if(rR.cumplio()){
                return true;
            }
            itemsC += rR.getItemsCumplidos();
        }
        rR =  plan.getReqsRegistrados().get(reqN);
        if(rR != null){
            if(rR.cumplio()){
                return true;
            }
            itemsC += rR.getItemsCumplidos();
            return itemsC >= rR.getReq().getItems();
        }
        return false;
    }

    @Override
    public boolean cumple(Plan plan, ArrayList<Curso> cursos) {
        return false;
    }

    @Override
    public boolean cumple(Plan plan, Periodo periodo) {
        return cumpleReq(plan,periodo,reqN);
    }

    public static boolean cumpleReq(Plan plan, Periodo periodo, String reqN){
        HistoriaAcademica h = plan.getHistoria();
        RequerimientoRegistrado rR =  h.getReqsRegistrados().get(reqN);
        int itemsC = 0;
        if(rR != null && rR.ultimoPeriodo().compare(periodo)== -1){
            if(rR.cumplio()){
                return true;
            }
            itemsC += rR.getItemsCumplidos();
        }
        rR =  plan.getReqsRegistrados().get(reqN);
        if(rR != null && rR.ultimoPeriodo().compare(periodo)== -1){
            if(rR.cumplio()){
                return true;
            }
            itemsC += rR.getItemsCumplidos();
            return itemsC >= rR.getReq().getItems();
        }
        return false;
    }

    @Override
    public boolean cumple(Plan plan, ArrayList<Curso> cursos, Periodo periodo) {
        return cumple(plan,periodo);
    }

    @Override
    public boolean cumple(HistoriaAcademica historia) {
        RequerimientoRegistrado rR =  historia.getReqsRegistrados().get(reqN);
        return rR != null && rR.cumplio();
    }

    @Override
    public boolean cumple(HistoriaAcademica historia, ArrayList<Curso> cursos) {
        return false;
    }

    @Override
    public boolean cumple(HistoriaAcademica historia, Periodo periodo) {
        RequerimientoRegistrado rR =  historia.getReqsRegistrados().get(reqN);
        return rR != null && rR.ultimoPeriodo().compare(periodo) == -1 && rR.cumplio();
    }


    @Override
    public boolean cumple(HistoriaAcademica historiaAcademica, ArrayList<Curso> cursos, Periodo periodo) {
        return cumple(historiaAcademica,periodo);
    }

    @Override
    public String tipo() {
        return null;
    }
}
