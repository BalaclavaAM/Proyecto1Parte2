package uniandes.dpoo.proyecto1.modelo.Cursos_Req.Requerimientos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Requerimientos.Requerimiento;

import java.util.ArrayList;

public class RequerimientoBloque extends Requerimiento {
    private final ArrayList<Curso> cursos;


    public RequerimientoBloque(String nombre, Nivel nivel, int semestresugerido, String tipologia, int creditos, String materia,
                               ArrayList<Curso> cursos , int items) {
        super(nombre, nivel, semestresugerido, tipologia, creditos, materia);
        this.items = items;
        this.cursos = cursos;
        this.tipo = "Bloque";
    }

    @Override
    public int validar(Curso curso) {
        if(cursos.contains(curso)){
            return 1;
        }
        return 0;
    }
}
