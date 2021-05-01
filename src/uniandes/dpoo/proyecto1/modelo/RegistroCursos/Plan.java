package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Restricciones.Restriccion;
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Plan extends MallaCursos {

    private HistoriaAcademica historia;
    private Map<String, Periodo> cursosRegistrados;

    public Plan(HistoriaAcademica historia) {
        this.pensum = historia.pensum;
        this.periodo = historia.periodo;
        this.historia = historia;
        this.cursosRegistrados = new Hashtable<>();
    }

    public Map<String, Periodo> getCursosRegistrados() {
        return cursosRegistrados;
    }

    public int agregarCursoxPeriodo(Curso curso, Periodo periodo, ArrayList<Curso> cursosP){
        if(cursosRegistrados.containsKey(curso.getCodigo())){
            return 2;
        }
        int rest = revisarRestriciones(curso, cursosP, periodo);
        if( rest != 1){
            return rest;
        }
        cursosRegistrados.put(curso.getCodigo(),periodo);
        modificarHistoria(curso,periodo);
        creditos += curso.getCreditos();
        return 1;
    }



    public EstadoAgregar agregarConjunto(ArrayList<Curso> cursos, ArrayList<Periodo> periodos) {
        int largo = cursos.size();
        EstadoAgregar estado;
        if (largo == periodos.size()) {
            return new EstadoAgregar(4, periodo);
        }
        Map<Periodo, ArrayList<Curso>> cursosPeriodos = new Hashtable<>();
        ArrayList<Periodo> Lperiodos = new ArrayList<>();
        Map<Curso, Integer> infoCursos = new Hashtable<>();
        estado = formatoAgregar(cursos, periodos, cursosPeriodos,infoCursos, Lperiodos);
        if (estado.getError() != 1) {
            return estado;
        }
        for (Periodo p : Lperiodos) {
            ArrayList<Curso> cursosP = cursosPeriodos.get(p);
            agregarPeriodo(periodo);
            for (Curso c : cursosP) {
                int val = agregarCursoxPeriodo(c, p, cursosP);
                if (val != 1) {
                    return estado.cambiarEstado(val, p, c);
                }
            }
        }
        return estado.cambiarEstado(1, Lperiodos.get(Lperiodos.size() - 1));
    }


    @Override
    public int revisarRestriciones(Curso curso, ArrayList<Curso> cursosP, Periodo periodo) {
        ArrayList<Restriccion> restriccions = curso.getRestricciones();
        for(Restriccion rst: restriccions){
            if(!rst.cumple(this, cursosP, periodo)){
                return 0;
            }
        }
        return 1;
    }



    public HistoriaAcademica getHistoria() {
        return historia;
    }
}


