package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Pensum;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Registro.*;
import uniandes.dpoo.proyecto1.modelo.Restricciones.Correquisito;
import uniandes.dpoo.proyecto1.modelo.Restricciones.PreRestriccion;

import java.util.*;

public abstract class MallaCursos {
    protected static final long serialVersionUID = -491840464239633611L;
    protected Pensum pensum;
    protected final Periodo peridoSistema;

    protected int creditos = 0;

    protected Map<String, CursoRegistrado> cursosRegistrados;
    protected Map<String, RequerimientoRegistrado> reqsRegistrados;
    protected Map<String, String> cursosValidados; // <CodigoCurso,NombreRequerimiento>
    protected Map<String, Map<String, CursoRegistrado>> infoSemestre; //dentro de un Semestre pueden haber dos periodos por los ciclos
    //se cuenta a semestre a los intersemestrales

    public MallaCursos(Periodo periodoSis){
        this.peridoSistema = periodoSis;
        this.cursosRegistrados = new Hashtable<>();
        this.reqsRegistrados = new Hashtable<>();
        this.cursosValidados = new Hashtable<>();
        this.infoSemestre = new Hashtable<>();
    }

    public ArrayList<EstadoAgregar> agregarCursos(ArrayList<CursoRegistrado> cursosR) {
        ArrayList<EstadoAgregar> estado = new ArrayList<>();
        Map<Periodo, Map<String,CursoRegistrado>> cursosPeriodos = new Hashtable<>();
        ArrayList<Periodo> Lperiodos = new ArrayList<>();
        formatoAgregar(cursosR, cursosPeriodos, Lperiodos, estado);
        for (Periodo p : Lperiodos) {
            Map<String,CursoRegistrado> cursosP = cursosPeriodos.get(p);
            if(dentroPeriodo(p)){
                agregarPeriodo(p);
                agregarCursosPeriodo(cursosP,p,estado);
            }else{
            estado.add(new EstadoAgregar(p, EstadoRegistro.Inconsistente));
            return estado;
            }
        }
        return estado;
    }



    public ArrayList<CursoRegistrado> agregarCursosPeriodo(Map<String, CursoRegistrado> cursosP, Periodo periodo, ArrayList<EstadoAgregar> estado) {
        Map<CursoRegistrado, ArrayList<Correquisito>> cursosCorreq = new HashMap<>();
        Map<String, EstadoRegistro> estadosRegistros = new HashMap<>();
        for (CursoRegistrado cr : cursosP.values()) {
            EstadoRegistro er = revisarConsistencia(cr, periodo);
            String codigo = cr.getCurso().getCodigo();
            if (er == EstadoRegistro.Ok || er == EstadoRegistro.Previo) {
                estadosRegistros.put(codigo, er);
                PreRestriccion rest = revisarRestriciones(cr, periodo);
                if (rest == null) {
                    ArrayList<Correquisito> corFaltantes = Correquisito.CorrequisitosEnRegistro(this, cr, periodo);
                    cursosCorreq.put(cr,corFaltantes);
                } else {
                    estado.add(new EstadoAgregar(cr, rest.nombre()));
                }
            } else {
                estado.add(new EstadoAgregar(cr, er));
            }
        }
        ArrayList<CursoRegistrado> cursosAniadir = Correquisito.cumpleEnInscripcion(cursosCorreq, estado);
        for (CursoRegistrado cr: cursosAniadir) {
            agregarCurso(cr, estadosRegistros.get(cr.getCurso().getCodigo()));
        }
        return cursosAniadir;
    }





    public void agregarCurso(CursoRegistrado cursoR, EstadoRegistro er){
        if (er == EstadoRegistro.Previo) {
            modificarHistoria(cursoR, EstadoRegistro.Previo);
        }
        if (er== EstadoRegistro.Ok){
            modificarHistoria(cursoR, EstadoRegistro.Ok);
        }
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
                estado.add(new EstadoAgregar(cr,EstadoRegistro.Repetido));
            }else{
                cursosP.put(codigo,cr);
                Lperiodos.add(acperiodo);
            }
        }
        Lperiodos.sort(Periodo::compare);
    }


    public EstadoRegistro revisarConsistencia(CursoRegistrado cursoR, Periodo periodo) {
        CursoRegistrado cr = getCurReg(cursoR.getCurso().getCodigo());
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
            if (!cr.getNota().aprobo()) { //perdio el curso y lo va a volver a ver
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
                validarRequerimiento(cursoR, reqAsociado);
            }
        }
        cursoR.Agregado();
        infoSemestre.get(cursoR.getPeriodo().periodoS()).put(codigo, cursoR);
        creditos += curso.getCreditos();
    }



    public EstadoRegistro validarRequerimiento(CursoRegistrado cursoR, Requerimiento req){
        if(cursoR == null || req == null){
            return EstadoRegistro.Inexistente;
        }
        String reqN = req.getNombre();
        String codigo = cursoR.getCurso().getCodigo();
        RequerimientoRegistrado reqR = reqsRegistrados.getOrDefault(reqN,new RequerimientoRegistrado(req));
        if(aprovado(cursoR)){
            if(cursosValidados.get(cursoR.getCurso().getCodigo()).equals(reqN)){
                return EstadoRegistro.Conflicto;
            }
            EstadoRegistro valid = reqR.agregarCurso(cursoR);
            if(valid == EstadoRegistro.Ok){
                reqsRegistrados.putIfAbsent(req.getNombre(),reqR);
                cursosValidados.put(codigo,reqN);
            }
            return valid;
        }else{
            return EstadoRegistro.Restriccion;
        }
    }

    public PreRestriccion revisarRestriciones(CursoRegistrado cursoR, Periodo periodo) {
        ArrayList<PreRestriccion> restriccions = cursoR.getCurso().getRestricciones();
        for(PreRestriccion rst: restriccions){
            if(!rst.cumple(this, periodo)){
                return rst;
            }
        }
        return null;
    }



    public abstract void agregarPeriodo(Periodo periodo);


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

    public Map<String, String> getCursosValidados() {
        return cursosValidados;
    }

    public Map<String, Map<String, CursoRegistrado>> getInfoPeriodos() {
        return infoSemestre;
    }



}

