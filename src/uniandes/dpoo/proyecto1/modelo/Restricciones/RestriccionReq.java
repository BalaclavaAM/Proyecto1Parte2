package uniandes.dpoo.proyecto1.modelo.Restricciones;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;
import uniandes.dpoo.proyecto1.modelo.Registro.RequerimientoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import java.util.ArrayList;
import java.util.Map;

public class RestriccionReq implements Restriccion{
    private String reqN;

    public RestriccionReq(String reqN){
        this.reqN = reqN;
    }


    public static boolean cumpleReq(MallaCursos malla, Periodo periodo, String reqN){
        return malla.itemsCumplidos(reqN,periodo) > malla.getPensum().getRequerimientos().get(reqN).getItems();
    }

    public static boolean cumpleReq(MallaCursos malla, String reqN){
        return malla.itemsCumplidos(reqN) > malla.getPensum().getRequerimientos().get(reqN).getItems();
    }


    @Override
    public boolean cumple(MallaCursos malla) {
        return cumpleReq(malla,reqN);
    }

    @Override
    public boolean cumple(MallaCursos malla, Map<String, CursoRegistrado> cursosP) {
        return cumpleReq(malla,reqN);
    }

    @Override
    public boolean cumple(MallaCursos malla, Periodo periodo) {
        return cumpleReq(malla,periodo,reqN);
    }

    @Override
    public boolean cumple(MallaCursos malla, Map<String, CursoRegistrado> cursosP, Periodo periodo) {
        return cumpleReq(malla,reqN);
    }

    @Override
    public String tipo() {
        return null;
    }
}
