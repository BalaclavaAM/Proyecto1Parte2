package uniandes.dpoo.proyecto1.modelo;

import java.util.ArrayList;

public class RequerimientoBloque extends Requerimiento {

    public RequerimientoBloque(String nombre, int semestresug, ArrayList<Curso> cursos) {
        super(nombre, semestresug, cursos);
    }

    @Override
    public boolean validar(Curso cursoOp) {
        return false;
    }
}
