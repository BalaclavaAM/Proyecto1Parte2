package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Registro.*;
import uniandes.dpoo.proyecto1.modelo.Restricciones.Restriccion;

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
    protected Map<String, CursoRegistrado> cursosValidados;
    protected Map<Periodo, Map<String, CursoRegistrado>> infoPeriodos; //informacion por periodos


    public ArrayList<EstadoAgregar> agregarCursos(ArrayList<CursoRegistrado> cursosR) {
        ArrayList<EstadoAgregar> estado = new ArrayList<>();
        Map<Periodo, Map<String,CursoRegistrado>> cursosPeriodos = new Hashtable<>();
        ArrayList<Periodo> Lperiodos = new ArrayList<>();
        formatoAgregar(cursosR, cursosPeriodos, Lperiodos, estado);
        for (Periodo p : Lperiodos) {
            Map<String,CursoRegistrado> cursosP = cursosPeriodos.get(p);
            if(dentroPeriodo(p)){
                agregarCursosPeriodo(cursosP,p,estado);
            }else{
            estado.add(new EstadoAgregar(EstadoRegistro.Inconsistente, p));
            return estado;
            }
        }
        return estado;
    }


    public void agregarCursosPeriodo(Map<String, CursoRegistrado> cursosP, Periodo periodo, ArrayList<EstadoAgregar> estado){
        agregarPeriodo(periodo);
        for (CursoRegistrado cr : cursosP.values()) {
            cursosP.remove(cr.getCurso().getCodigo());
            agregarCurso(cr, cursosP);
            EstadoAgregar ea = cr.getEstadoAgregar();
            if(ea.getError() != EstadoRegistro.Ok){
                estado.add(ea);
            }
        }
    }

    public void formatoAgregar(ArrayList<CursoRegistrado> cursosR, Map<Periodo, Map<String,CursoRegistrado>> cursosPeriodos,
                               ArrayList<Periodo> Lperiodos,
                               ArrayList<EstadoAgregar> estado){

        Periodo acperiodo;
        Curso accurso;
        for(CursoRegistrado cr: cursosR){
            acperiodo = cr.getPeriodo();
            String codigo = cr.getCurso().getCodigo();
            Map<String,CursoRegistrado> cursosP= cursosPeriodos.get(acperiodo);
            if(cursosP == null) {
                cursosP = new Hashtable<>();
                cursosPeriodos.put(acperiodo, cursosP);
                Lperiodos.add(acperiodo);
            }
            if(cursosP.containsKey(codigo)){
                EstadoAgregar ea = cr.getEstadoAgregar();
                ea.setError(EstadoRegistro.Repetido);
                estado.add(ea);
            }else{
                cursosP.put(codigo,cr);
                Lperiodos.add(acperiodo);
            }
        }
        Lperiodos.sort(Periodo::compare);
    }

    public EstadoRegistro revisarConsistencia(CursoRegistrado cursoR) {
        EstadoRegistro pa;
        if((pa=cursoR.getEstadoAgregar().getError()) != EstadoRegistro.Pendiente){
            return pa;
        }
        CursoRegistrado cr = getCurReg(cursoR.getCurso().getCodigo());
        Periodo periodo = cursoR.getPeriodo();
        if (cr != null) {
            int comp = periodo.compare(cr.getPeriodo());
            if (comp == 0) {
                return EstadoRegistro.Repetido;
            }
            if (comp == -1) {
                if (!cursoR.getNota().aprobo()) { // le dejamos planear dos veces el mismo curso
                    return EstadoRegistro.Previo;
                }
                return EstadoRegistro.Inconsistente; // incosistencia
            }
            if (!cr.getNota().aprobo()) {
                return EstadoRegistro.Ok;
            }
            return EstadoRegistro.Repetido;
        }
        return EstadoRegistro.Ok;
    }

    public boolean agregarCurso(CursoRegistrado cursoR, Map<String, CursoRegistrado> cursosP){
        if(cursoR.isAgregado()){
            return true;
        }
        if(cursoR.getEstado() == EstadoCurso.Pendiente) {
            EstadoAgregar ea = cursoR.getEstadoAgregar();
            if (revisarRestAndCos(cursoR, cursosP)) {
                if (ea.getError() == EstadoRegistro.Previo) {
                    ea.setError(EstadoRegistro.Ok);
                    modificarHistoria(cursoR, EstadoRegistro.Previo);
                }
                if (ea.getError()== EstadoRegistro.Ok){
                    modificarHistoria(cursoR, EstadoRegistro.Ok);
                }
                return true;
            }
        }
        return false;
    }

    public boolean revisarRestAndCos(CursoRegistrado cursoR, Map<String,CursoRegistrado> cursosP) {
        EstadoRegistro er = revisarConsistencia(cursoR);
        EstadoAgregar ea = cursoR.getEstadoAgregar();
        if (er == EstadoRegistro.Previo || er == EstadoRegistro.Ok) {
            Restriccion rest;
            if ((rest = revisarRestriciones(cursoR, cursosP, cursoR.getPeriodo())) != null) {
                ea.setError(EstadoRegistro.Restriccion);
                ea.setRest(rest);
                return false;
            }
            return true;
        }
        return false;
    }



    public void agregarPeriodo(Periodo periodo) {
        String periodoS = PeriodoS.periodoS(periodo.getAnio(), periodo.getSemestre());
        ArrayList<Periodo> periodosL = periodosMap.computeIfAbsent(periodoS, k -> new ArrayList<>());
        infoPeriodos.putIfAbsent(periodo,new Hashtable<>());
        if (!periodosL.contains(periodo)) {
            periodosL.add(periodo);
        }
        if (periodo.compare(this.periodo) == 1) {
            this.periodo = periodo;
        }
    }


    public void modificarHistoria(CursoRegistrado cursoR, EstadoRegistro eC) {
        Curso curso = cursoR.getCurso();
        String codigo = curso.getCodigo();

        if(eC == EstadoRegistro.Ok) {
            cursosRegistrados.put(curso.getCodigo(),cursoR);
            String reqAsociado = pensum.getCursosValidacionAuto().get(codigo);
            if (reqAsociado != null) {
                if (cursoR.getNota().aprobo()) {
                    validarRequerimiento(codigo, reqAsociado);
                }
            }
        }
        cursoR.Agregado();
        Map<String,CursoRegistrado> infoPerido = infoPeriodos.get(cursoR.getPeriodo());
        infoPerido.putIfAbsent(codigo, cursoR);
        creditos += curso.getCreditos();
    }




    public int validarRequerimiento(String codigo, String reqN){
        Requerimiento req = pensum.getRequerimientos().get(reqN);
        CursoRegistrado cursoR = cursosRegistrados.get(codigo);
        if(cursoR == null || req == null){
            return -1;
        }
        RequerimientoRegistrado reqR;
        if(cursoR.getEstado() != EstadoCurso.Planeado){
            if(!cursoR.getNota().aprobo()){
                return 0;
            }
            if(cursosValidados.containsKey(codigo) ){
                return 2;
            }
            reqR = reqsRegistrados.computeIfAbsent(req.getNombre(), k ->new RequerimientoRegistrado(req));

        }else{
            reqR = reqsRegistrados.computeIfAbsent(req.getNombre(), k ->new RequerimientoRegistrado(req));
            CursoRegistrado cRR = reqR.getCursosR().get(codigo);
            if(cRR != null && cRR.getPeriodo().compare(cursoR.getPeriodo()) == -1){
                reqR.quitarCurso(codigo);
            }

        }
        int valid = reqR.agregarCurso(cursoR, cursoR.getPeriodo());
        if(valid == 1){
            cursosValidados.put(codigo,cursoR);
        }
        return valid;
    }

    public Restriccion revisarRestriciones(CursoRegistrado cursoR,Map<String,CursoRegistrado> cursosP, Periodo periodo) {
        ArrayList<Restriccion> restriccions = cursoR.getCurso().getRestricciones();
        for(Restriccion rst: restriccions){
            if(!rst.cumple(this, cursosP, periodo)){
                return rst;
            }
        }
        return null;
    }

    public abstract CursoRegistrado getCurReg(String codigo);
    public abstract boolean dentroPeriodo(Periodo p);
    public abstract Periodo getPHis();
    public abstract Nota getauxNota(int i, ArrayList<Nota> notas);
    public abstract boolean getauxEps(int i, ArrayList<Boolean> epsilons);
    public abstract EstadoCurso getauxEsC(Nota nota);
    public abstract boolean aprovado(CursoRegistrado cursoR);
    public abstract int itemsCumplidos(String reqN, Periodo periodo);
    public abstract int itemsCumplidos(String reqN);

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

    public Map<String, CursoRegistrado> getCursosValidados() {
        return cursosValidados;
    }

    public Map<Periodo, Map<String, CursoRegistrado>> getInfoPeriodos() {
        return infoPeriodos;
    }

}

