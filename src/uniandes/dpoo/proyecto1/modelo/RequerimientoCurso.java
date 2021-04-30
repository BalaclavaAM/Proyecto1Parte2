package uniandes.dpoo.proyecto1.modelo;

import java.util.ArrayList;
import java.util.Map;

public class RequerimientoCurso extends Requerimiento{
    private static final long serialVersionUID = -7762431642880730439L;
    private String nombre;
    private nivel nivel;
    private int semestresugerido;
    private String tipologia;
    private ArrayList<Curso> cursos;
    private int creditos;

    public RequerimientoCurso(String nombre, int nivel, int semestresug, String tipologia, ArrayList<Curso> cursos, int creditos) {
        super(nombre, nivel, semestresug, tipologia, cursos, creditos);
    }


    @Override
    public boolean validar(Curso cursoOp, Map<String, Integer> cursosVreq) {
        if (cursos.contains(cursoOp) & !cursosVreq.containsKey(cursoOp.getCodigo())){
            cursosVreq.put(cursoOp.getCodigo(),1);
            return  true;
        }
        return false;
    }

    @Override
    public boolean cumplio(Curso cursoOp, RegistroRequerimiento regisReq) {
        return regisReq.getCreditosCumplidos() >= creditos;
    }
}
