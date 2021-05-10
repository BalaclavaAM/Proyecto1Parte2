package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Pensum;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Registro.*;
import uniandes.dpoo.proyecto1.modelo.Restricciones.Restriccion;

import java.util.*;

public abstract class MallaCursos {
    protected static final long serialVersionUID = -491840464239633611L;
    protected Pensum pensum;
    protected final Periodo peridoSistema;
    protected final Periodo periodoInicio;
    protected Periodo ultimoPeriodo;

    protected int creditos = 0;

    protected Map<String, CursoRegistrado> cursosRegistrados;
    protected Map<String, RequerimientoRegistrado> reqsRegistrados;
    protected Map<String, CursoRegistrado> cursosValidados;
    protected Map<String, Map<String, CursoRegistrado>> infoSemestre; //dentro de un Semestre pueden haber dos periodos por los ciclos
    //se cuenta a semestre a los intersemestrales

    public MallaCursos(Periodo periodoSis, Periodo periodoI){
        this.peridoSistema = periodoSis;
        Periodo p = new Periodo(periodoI.getAnio(),periodoI.getSemestre());
        this.periodoInicio = p;
        this.ultimoPeriodo = p;
    }

    public ArrayList<EstadoAgregar> agregarCursos(ArrayList<CursoRegistrado> cursosR) {
        ArrayList<EstadoAgregar> estado = new ArrayList<>();
        Map<Periodo, Map<String,CursoRegistrado>> cursosPeriodos = new Hashtable<>();
        ArrayList<Periodo> Lperiodos = new ArrayList<>();
        formatoAgregar(cursosR, cursosPeriodos, Lperiodos, estado);
        for (Periodo p : Lperiodos) {
            Map<String,CursoRegistrado> cursosP = cursosPeriodos.get(p);
            if(dentroPeriodo(p)){
                infoSemestre.putIfAbsent(p.periodoS(),new Hashtable<>());
                agregarCursosPeriodo(cursosP,estado);
            }else{
            estado.add(new EstadoAgregar(EstadoRegistro.Inconsistente, p));
            return estado;
            }
        }
        return estado;
    }



    public void agregarCursosPeriodo(Map<String, CursoRegistrado> cursosP, ArrayList<EstadoAgregar> estado){
        HashMap<String, CursoRegistrado> cursosPaux = new HashMap<>(cursosP);
        for (CursoRegistrado cr : cursosP.values()) {
            cursosPaux.remove(cr.getCurso().getCodigo());
            agregarCurso(cr, cursosPaux);
            EstadoAgregar ea = cr.getEstadoAgregar();
            if(ea.getError() != EstadoRegistro.Ok){
                estado.add(ea);
            }
        }
    }

    public boolean agregarCurso(CursoRegistrado cursoR, Map<String, CursoRegistrado> cursosP){

        if(cursoR.isAgregado()){
            return true;
        }
        if(cursoR.getEstadoAgregar().getError() == EstadoRegistro.Pendiente) {
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

    public void formatoAgregar(ArrayList<CursoRegistrado> cursosR, Map<Periodo, Map<String,CursoRegistrado>> cursosPeriodos,
                               ArrayList<Periodo> Lperiodos,
                               ArrayList<EstadoAgregar> estado){

        Periodo acperiodo;
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
            ea.setError(er);
            return true;
        }
        ea.setError(er);
        return false;
    }

    public EstadoRegistro revisarConsistencia(CursoRegistrado cursoR) {
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
                return EstadoRegistro.Inconsistente; // incosistencia, si ya paso el curso no debe porque verlo en el periodo reciente
            }
            if (!cr.getNota().aprobo()) {
                return EstadoRegistro.Ok;
            }
            return EstadoRegistro.Repetido;
        }
        return EstadoRegistro.Ok;
    }



    public void modificarHistoria(CursoRegistrado cursoR, EstadoRegistro eC) {
        Curso curso = cursoR.getCurso();
        String codigo = curso.getCodigo();

        if(eC == EstadoRegistro.Ok) {
            cursosRegistrados.put(curso.getCodigo(),cursoR);
            Requerimiento reqAsociado = pensum.getCursosValidacionAuto().get(codigo);
            if (reqAsociado != null) {
                if (cursoR.getNota().aprobo()) {
                    validarRequerimiento(codigo, reqAsociado);
                }
            }
        }


        cursoR.Agregado();
        infoSemestre.get(cursoR.getPeriodo().periodoS()).put(codigo, cursoR);
        creditos += curso.getCreditos();
    }




    public EstadoRegistro validarRequerimiento(String codigo, Requerimiento req){
        CursoRegistrado cursoR = cursosRegistrados.get(codigo);
        if(cursoR == null || req == null){
            return EstadoRegistro.Inexistente;
        }
        RequerimientoRegistrado reqR;
        if(cursoR.getEstado() != EstadoCurso.Planeado){
            if(!cursoR.getNota().aprobo()){
                return EstadoRegistro.Restriccion;
            }
            if(cursosValidados.containsKey(codigo) ){
                return EstadoRegistro.Conflicto;
            }
            reqR = reqsRegistrados.computeIfAbsent(req.getNombre(), k ->new RequerimientoRegistrado(req));

        }else{
            reqR = reqsRegistrados.computeIfAbsent(req.getNombre(), k ->new RequerimientoRegistrado(req));
            CursoRegistrado cRR = reqR.getCursosR().get(codigo);
            if(cRR != null && cRR.getPeriodo().compare(cursoR.getPeriodo()) == -1){
                reqR.quitarCurso(codigo);
            }
        }
        EstadoRegistro valid = reqR.agregarCurso(cursoR, cursoR.getPeriodo());
        if(valid == EstadoRegistro.Ok){
            cursosValidados.put(codigo,cursoR);
        }
        return valid;
    }

    public Restriccion revisarRestriciones(CursoRegistrado cursoR,Map<String,CursoRegistrado> cursosP, Periodo periodo) {
        ArrayList<Restriccion> restriccions = cursoR.getCurso().getRestricciones();
        for(Restriccion rst: restriccions){
            System.out.println("s");
            if(!rst.cumple(this, cursosP, periodo)){
                System.out.println("s");
                return rst;
            }
        }
        return null;
    }

    public void agregarPeriodo(Periodo periodo){

    }

    public abstract CursoRegistrado getCurReg(String codigo);
    public abstract boolean dentroPeriodo(Periodo p);
    public abstract Periodo getPHis();
    public abstract boolean aprovado(CursoRegistrado cursoR);
    public abstract int itemsCumplidos(String reqN, Periodo periodo);
    public abstract int itemsCumplidos(String reqN);

    public Pensum getPensum() {
        return pensum;
    }

    public Periodo getPeridoSistema() {
        return peridoSistema;
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

    public Map<String, Map<String, CursoRegistrado>> getInfoPeriodos() {
        return infoSemestre;
    }

    public Periodo getPeriodoInicio() {
        return periodoInicio;
    }

    public Periodo getUltimoPeriodo() {
        return ultimoPeriodo;
    }
}

