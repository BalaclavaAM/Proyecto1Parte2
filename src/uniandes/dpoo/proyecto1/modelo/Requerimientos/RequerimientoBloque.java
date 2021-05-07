package uniandes.dpoo.proyecto1.modelo.Requerimientos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;

import java.util.ArrayList;

public class RequerimientoBloque extends Requerimiento {
    private final ArrayList<String> codigos;


    public RequerimientoBloque(String nombre, Nivel nivel, int semestresugerido, String tipologia, int creditos, String materia,
                               ArrayList<String> codigos , int items) {
        super(nombre, nivel, semestresugerido, tipologia, creditos);
        this.items = items;
        this.codigos = codigos;
        this.tipo = "Bloque";
    }

    @Override
    public int validar(Curso curso) {
        if(codigos.contains(curso.getCodigo())){
            return 1;
        }
        return 0;
    }


}
