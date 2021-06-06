package uniandes.dpoo.proyecto1.modelo.Requerimientos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.ReqTipologia;

public class RequerimietoMateria extends Requerimiento {
    private final String materia;

    public RequerimietoMateria(String nombre, Nivel nivel, int semestresugerido, ReqTipologia tipologia, int creditos,
                               String materia, int items, String[]mains) {
        super(nombre, nivel, semestresugerido, tipologia, creditos, mains);
        this.tipo = "Materia";
        this.items = items;
        this.materia = materia;
    }

    @Override
    public double validar(Curso curso) {
        if(materia.equals(curso.getMateria())){
            return 1;
        }
        return 0;
    }
}
