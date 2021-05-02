package uniandes.dpoo.proyecto1.modelo.Cursos_Req.Requerimientos;


import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Requerimientos.Requerimiento;

import java.util.ArrayList;

public class RequerimientoCreditos extends Requerimiento {
    //se cumple aprovando una cantidad de creditos, con cursos dentro de la lista dentro la lista
    private final ArrayList<Curso> cursos;

    public RequerimientoCreditos(String nombre, Nivel nivel, int semestresugerido, String tipologia, int creditos,
                                  ArrayList<Curso> cursos) {
        super(nombre, nivel, semestresugerido, tipologia, creditos);
        this.cursos = cursos;
        this.items = creditos;
        this.tipo = "Creditos";
    }


    @Override
    public int validar(Curso curso){
        if (cursos.contains(curso)){
            return curso.getCreditos();
        }
        return  0;
    }
}
