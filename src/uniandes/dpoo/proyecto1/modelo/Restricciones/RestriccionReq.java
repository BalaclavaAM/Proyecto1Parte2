package uniandes.dpoo.proyecto1.modelo.Restricciones;

import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;

public class RestriccionReq implements PreRestriccion {
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
    public boolean cumple(MallaCursos malla){
        return cumpleReq(malla,reqN);
    }

    @Override
    public boolean cumple(CursoRegistrado cursoR,MallaCursos malla, Periodo periodo) {
        return cumpleReq(malla,periodo,reqN);
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
