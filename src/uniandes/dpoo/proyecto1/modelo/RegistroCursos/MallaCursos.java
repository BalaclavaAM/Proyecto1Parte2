package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Nota.calCual;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Registro.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public abstract class MallaCursos {
    protected static final long serialVersionUID = -491840464239633611L;
    protected Pensum pensum;
    protected Periodo periodo;
    protected int creditos = 0;

    protected Map<String, ArrayList<Periodo>> periodosMap; //es una mapa que agrupa los periodos en periodos simples
    protected Map<String, CursoRegistrado> cursosRegistrados;
    protected Map<String, RequerimientoRegistrado> reqsRegistrados;
    protected Map<String, Curso> cursosValidados;
    protected Map<Periodo, Map<String, CursoRegistrado>> infoPeriodos; //informacion por periodos


    public ArrayList<EstadoAgregar> agregarCursos(ArrayList<Curso> cursos, ArrayList<Periodo> periodos,
                                                  ArrayList<Nota> notas, ArrayList<Boolean> epsilons) {
        ArrayList<EstadoAgregar> estado = new ArrayList<>();
        Map<Periodo, ArrayList<Curso>> cursosPeriodos = new Hashtable<>();
        Map<Periodo, Map<String,Integer>> infoCursos = new Hashtable<>();
        ArrayList<Periodo> Lperiodos = new ArrayList<>();
        formatoAgregar(cursos, periodos, cursosPeriodos, infoCursos, Lperiodos, estado);
        for (Periodo p : Lperiodos) {
            ArrayList<Curso> cursosP = cursosPeriodos.get(p);
            if(dentroPeriodo(p)){
                agregarPeriodo(p);
                for (Curso c : cursosP) {
                    int i = infoCursos.get(p).get(c.getCodigo());
                    Nota nota = getauxNota(i,notas);
                    cursosP.remove(0);
                    int val = agregarCursoxPeriodo(c, nota, getauxEsC(nota),getauxEps(i,epsilons),cursosP,p);
                    if (val != 1) {
                        estado.add(new EstadoAgregar(val,p,c));
                    }
                }
            }else{
                estado.add(new EstadoAgregar(5,p));
                return estado;
            }
        }
        return estado;
    }

    public int agregarCursoxPeriodo(Curso curso, Nota nota, EstadoCurso estadoC, boolean epsilon, ArrayList<Curso> cursosP, Periodo periodo) {
        String codigo = curso.getCodigo();
        CursoRegistrado cr = getCurReg(codigo);
        if (cr != null) {
            int comp = periodo.compare(cr.getPeriodo());
            if (comp == 0) {
                if(cr.getNota().notaCual() == calCual.pendiente){
                    cr.setNota(nota);
                    cr.setEpsilon(epsilon);   // repeticion en inscripcion y planeacion
                    return 1;
                }
                return 2;
            }
            if (comp == -1) {
                if (!nota.aprobo()) { // le dejamos planear dos veces el mismo curso
                    int val = revisarRestriciones(curso,cursosP,periodo);
                    if(val == 1) {
                        cr = new CursoRegistrado(curso, nota, estadoC, epsilon, periodo);
                        modificarHistoria(cr, periodo);
                        return 1;
                    }
                    return val;
                }
                return -3; // incosistencia
            }
            if (!cr.getNota().aprobo()) {
                return auxCursoXPeriodo(curso,nota,estadoC,epsilon,cursosP,periodo);
            }
            return 2;
        }
        return auxCursoXPeriodo(curso,nota,estadoC,false, cursosP, periodo);
    }

    public void agregarPeriodo(Periodo periodo) {
        String periodoS = PeriodoS.periodoS(periodo.getAnio(), periodo.getSemestre());
        ArrayList<Periodo> periodosL = periodosMap.computeIfAbsent(periodoS, k -> new ArrayList<>());
        if (!periodosL.contains(periodo)) {
            periodosL.add(periodo);
        }
        if (periodo.compare(this.periodo) == 1) {
            this.periodo = periodo;
        }
    }


    public void modificarHistoria(CursoRegistrado cursoR, Periodo periodo) {
        Curso curso = cursoR.getCurso();
        String codigo = cursoR.getCurso().getCodigo();

        String reqAsociado = pensum.getCursosValidacionAuto().get(codigo);
        if (reqAsociado != null) {
            if(cursoR.getNota().aprobo()) {
                validarRequerimiento(codigo, reqAsociado);
            }
        }
        Map<String, CursoRegistrado> infoPerido = infoPeriodos.computeIfAbsent(periodo, k -> new Hashtable<>());
        infoPerido.putIfAbsent(codigo, cursoR);
        creditos += curso.getCreditos();
    }


    public int auxCursoXPeriodo(Curso curso, Nota nota, EstadoCurso estadoC, boolean epsilon, ArrayList<Curso> cursosP, Periodo periodo){
        int val = revisarRestriciones(curso,cursosP,periodo);
        if(val == 1){
            CursoRegistrado cr = new CursoRegistrado(curso, periodo, estadoC, nota, epsilon);
            cursosRegistrados.put(curso.getCodigo(),cr);
            modificarHistoria(cr, periodo);
            return 1;
        }
        return val;
    }

    public void formatoAgregar(ArrayList<Curso> cursos, ArrayList<Periodo> periodos,
                                        Map<Periodo, ArrayList<Curso>> cursosPeriodos, Map<Periodo,
            Map<String, Integer>> infocursos,ArrayList<Periodo> Lperiodos,
                                        ArrayList<EstadoAgregar> estado){

        Periodo acperiodo;
        Curso accurso;
        for (int i = 0; i < cursos.size(); i++) {
            acperiodo = periodos.get(i);
            accurso =  cursos.get(i);
            String codigo = accurso.getCodigo();
            Map<String, Integer> infoP = infocursos.get(acperiodo);
            ArrayList<Curso> cursosP = cursosPeriodos.get(acperiodo);
            if(infoP == null) {
                infoP = new Hashtable<>();
                cursosP = new ArrayList<>();
                infocursos.put(acperiodo, infoP);
                cursosPeriodos.put(acperiodo,cursosP);
                Lperiodos.add(acperiodo);
            }
            if(infoP.containsKey(codigo)){
                estado.add(new EstadoAgregar(3,acperiodo,accurso));
            }else{
                infoP.put(codigo,i);
                cursosPeriodos.put(acperiodo,cursosP);
                Lperiodos.add(acperiodo);
            }
        }
        Lperiodos.sort(Periodo::compare);
    }

    public int validarRequerimiento(String codigo, String reqN){
        Requerimiento req = pensum.getRequerimientos().get(reqN);
        CursoRegistrado cursoR = cursosRegistrados.get(codigo);
        if(cursoR == null || req == null){
            return -1;
        }
        if(!cursoR.getNota().aprobo() && !(cursoR.getEstado() == EstadoCurso.Planeado)){
            return 0;
        }
        Periodo periodo = cursoR.getPeriodo();
        if(cursosValidados.containsKey(codigo)){
            return 2;
        }
        RequerimientoRegistrado reqR = reqsRegistrados.computeIfAbsent(req.getNombre(), k ->new RequerimientoRegistrado(req));
        int valid = reqR.agregarCurso(cursoR, periodo);
        if(valid == 1){
            cursosValidados.put(codigo,cursoR.getCurso());
        }
        return valid;
    }

    public abstract int revisarRestriciones(Curso curso, ArrayList<Curso> cursosP, Periodo periodo);
    public abstract CursoRegistrado getCurReg(String codigo);
    public abstract boolean dentroPeriodo(Periodo p);
    public abstract Periodo getPHis();
    public abstract Nota getauxNota(int i, ArrayList<Nota> notas);
    public abstract boolean getauxEps(int i, ArrayList<Boolean> epsilons);
    public abstract EstadoCurso getauxEsC(Nota nota);

    public Pensum getPensum() {
        return pensum;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public Map<String, ArrayList<Periodo>> getPeriodosMap() {
        return periodosMap;
    }

    public Map<String, CursoRegistrado> getCursosRegistrados() {
        return cursosRegistrados;
    }

    public Map<String, RequerimientoRegistrado> getReqsRegistrados() {
        return reqsRegistrados;
    }

    public Map<String, Curso> getCursosValidados() {
        return cursosValidados;
    }

    public Map<Periodo, Map<String, CursoRegistrado>> getInfoPeriodos() {
        return infoPeriodos;
    }

}

