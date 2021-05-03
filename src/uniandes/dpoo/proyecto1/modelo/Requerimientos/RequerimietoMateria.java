package uniandes.dpoo.proyecto1.modelo.Requerimientos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;

public class RequerimietoMateria extends Requerimiento {
    private String materia;

    public RequerimietoMateria(String nombre, Nivel nivel, int semestresugerido, String tipologia, int creditos,
                               String materia, int items) {
        super(nombre, nivel, semestresugerido, tipologia, creditos);
        this.tipo = "Materia";
        this.items = items;
        this.materia = materia;
    }

    @Override
    public int validar(Curso curso) {
        if(materia.equals(curso.getMateria())){
            return 1;
        }
        return 0;
    }
}
