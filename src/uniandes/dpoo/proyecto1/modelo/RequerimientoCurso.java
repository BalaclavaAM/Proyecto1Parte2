package uniandes.dpoo.proyecto1.modelo;

import java.util.ArrayList;

public class RequerimientoCurso extends Requerimiento {
    private String nombre;
    private int semestresugerido;
    private ArrayList<Curso> cursos;
    private int creditos;
    public RequerimientoCurso(String nombre, int semestresug, ArrayList<Curso> cursos) {
        super(nombre, semestresug, cursos);
    }
    public boolean validar(Curso cursoOp){
        for (Curso opcion: cursos) {
            if (opcion.equals(cursoOp)) {
                return true;
            }
        }
        return false;
    }
    
}
