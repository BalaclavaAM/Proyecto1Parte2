package uniandes.dpoo.proyecto1.modelo.Cursos_Req.Requerimientos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Requerimientos.Requerimiento;

public class RequerimietoMateria extends Requerimiento {

    public RequerimietoMateria(String nombre, Nivel nivel, int semestresugerido, String tipologia, int creditos,
                               String materia, int items) {
        super(nombre, nivel, semestresugerido, tipologia, creditos,materia);
        this.tipo = "Materia";
        this.items = items;
    }

    @Override
    public int validar(Curso curso) {
        if(materia.equals(curso.getMateria())){
            return 1;
        }
        return 0;
    }
}
