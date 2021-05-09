package uniandes.dpoo.proyecto1.modelo.Requerimientos;


import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.ReqTipologia;

import java.util.ArrayList;

public class RequerimientoCreditos extends Requerimiento {
    //se cumple aprovando una cantidad de creditos, con cursos dentro de la lista dentro la lista
    private final ArrayList<String> codigos;

    public RequerimientoCreditos(String nombre, Nivel nivel, int semestresugerido, ReqTipologia tipologia, int creditos,
                                 ArrayList<String> codigos, String[] mains) {
        super(nombre, nivel, semestresugerido, tipologia, creditos, mains);
        this.codigos = codigos;
        this.items = creditos;
        this.tipo = "Creditos";
    }


    @Override
    public int validar(Curso curso){
        if (codigos.contains(curso.getCodigo())){
            return curso.getCreditos();
        }
        return  0;
    }
}
