package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
<<<<<<< HEAD
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Restricciones.Restriccion;
=======
import uniandes.dpoo.proyecto1.modelo.Restricciones.Restriccion;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.EstadoCurso;
import uniandes.dpoo.proyecto1.modelo.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Nota.calCual;
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Plan extends MallaCursos {
<<<<<<< HEAD

    private HistoriaAcademica historia;
    private Map<String, Periodo> cursosRegistrados;
=======
    public final EstadoCurso estadoPl = EstadoCurso.Planeado;
    public final NotaCual  notaPlan = new NotaCual(calCual.planeado);
    private HistoriaAcademica historia;
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e

    public Plan(HistoriaAcademica historia) {
        this.pensum = historia.pensum;
        this.periodo = historia.periodo;
        this.historia = historia;
<<<<<<< HEAD
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
=======
    }



    public Map<String, CursoRegistrado> getCursosRegistrados() {
        return cursosRegistrados;
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
    }



<<<<<<< HEAD
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
=======
    public ArrayList<EstadoAgregar> agregarCursos(ArrayList<Curso> cursos, ArrayList<Periodo> periodos) {
        ArrayList<EstadoAgregar> estado = new ArrayList<>();
        Map<Periodo, ArrayList<Curso>> cursosPeriodos = new Hashtable<>();
        ArrayList<Periodo> Lperiodos = new ArrayList<>();
        Map<Curso, Integer> infoCursos = new Hashtable<>();
        formatoAgregar(cursos, periodos, cursosPeriodos, infoCursos, Lperiodos, estado);
        agregarCursosAux(Lperiodos, cursosPeriodos,estado);

        return estado;
    }

    public void agregarCursosAux(ArrayList<Periodo> Lperiodos, Map<Periodo, ArrayList<Curso>> cursosPeriodos,
                             ArrayList<EstadoAgregar> estado){
        for (Periodo p : Lperiodos) {
            ArrayList<Curso> cursosP = cursosPeriodos.get(p);
            if(historia.getPeriodo().compare(p) == -1){
                agregarPeriodo(p);
                for (Curso c : cursosP) {
                    int val = agregarCursoxPeriodo(c,estadoPl,cursosP, p);
                    if (val != 1) {
                        estado.add(new EstadoAgregar(val,p,c));
                    }
                }
            }else{
                estado.add(new EstadoAgregar(5,p));
                break;
            }
        }
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
    }


    @Override
<<<<<<< HEAD
=======
    public CursoRegistrado getCurReg(String codigo) {
        CursoRegistrado c1 = historia.cursosRegistrados.get(codigo);
        if (c1 != null){
            CursoRegistrado c2 = cursosRegistrados.get(codigo);
            if(c2 != null){
                return c2;
            }
            return c1;
        }
        return null;
    }

    @Override
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
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


