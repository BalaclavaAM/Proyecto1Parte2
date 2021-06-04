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
    protected int creditosAprovados = 0;
    protected Map<String, CursoRegistrado> cursosRegistrados;
    protected Map<String, RequerimientoRegistrado> reqsRegistrados;
    protected Map<String, String> cursosValidados; // <CodigoCurso,NombreRequerimiento>
    protected Map<String,  ArrayList<CursoRegistrado>> infoSemestre; //dentro de un Semestre pueden haber dos periodos por los ciclos
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
        Map<Periodo, ArrayList<CursoRegistrado>> cursosPeriodos = new Hashtable<>();
        ArrayList<Periodo> Lperiodos = new ArrayList<>();
        formatoAgregar(cursosR, cursosPeriodos, Lperiodos, estado);
        for (Periodo p : Lperiodos) {
            ArrayList<CursoRegistrado> cursosP = cursosPeriodos.get(p);
            if (dentroPeriodo(p)) {
                agregarPeriodo(p);
                agregarCursosPeriodo(cursosP, p, estado);
                if (infoSemestre.get(p.periodoS()).size() == 0) {
                    infoSemestre.remove(p.periodoS());
                }
            } else {
                estado.add(new EstadoAgregar(p, EstadoRegistro.Inconsistente));
                return estado;
            }
        }
        return estado;
    }


    public ArrayList<CursoRegistrado> agregarCursosPeriodo(ArrayList<CursoRegistrado> cursosP, Periodo periodo, ArrayList<EstadoAgregar> estado) {
        Map<CursoRegistrado, ArrayList<Correquisito>> cursosCorreq = new HashMap<>();
        Map<String, EstadoRegistro> estadosRegistros = new HashMap<>();
        for (CursoRegistrado cr : cursosP) {
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
        Curso curso = cursoR.getCurso();
        String codigo = curso.getCodigo();
        if(er == EstadoRegistro.Ok) {
            cursosRegistrados.put(curso.getCodigo(),cursoR);
            Requerimiento reqAsociado = pensum.getCursosValidacionAuto().get(codigo);
            if (reqAsociado != null) {
                validarRequerimiento(cursoR, reqAsociado.getNombre());
            }
        }
        cursoR.Agregado();
        infoSemestre.get(cursoR.getPeriodo().periodoS()).add(cursoR);
        creditos += curso.getCreditos();
        if(cursoR.getNota().aprobo()){
            creditosAprovados += curso.getCreditos();
        }
    }

    public void formatoAgregar(ArrayList<CursoRegistrado> cursosR, Map<Periodo, ArrayList<CursoRegistrado>> cursosPeriodos,
                               ArrayList<Periodo> Lperiodos,
                               ArrayList<EstadoAgregar> estado){

        Periodo acperiodo;
        for(CursoRegistrado cr: cursosR){
            acperiodo = cr.getPeriodo();
            ArrayList<CursoRegistrado> cursosP= cursosPeriodos.get(acperiodo);
            if(cursosP == null) {
                cursosP = new ArrayList<>();
                cursosPeriodos.put(acperiodo, cursosP);
                Lperiodos.add(acperiodo);
            }
            if(existeList(cursosP,cr)) {
                estado.add(new EstadoAgregar(cr, EstadoRegistro.Repetido));
            }else{
                cursosP.add(cr);
                Lperiodos.add(acperiodo);
            }
        }
        Lperiodos.sort(Periodo::compare);
    }

    private boolean existeList(ArrayList<CursoRegistrado> cursosP, CursoRegistrado cursoR){
        for(CursoRegistrado cr: cursosP){
            if(cr.getCurso().getCodigo().equals(cursoR.getCurso().getCodigo())
                    && cr.getPeriodo().compare(cursoR.getPeriodo())== 0){
                return true;
            }
        }
        return false;
    }

    public EstadoRegistro revisarConsistencia(CursoRegistrado cursoR, Periodo periodo) {
        CursoRegistrado registro = getCurReg(cursoR.getCurso().getCodigo());
        if (registro != null) {
            int comp = periodo.compare(registro.getPeriodo());
            if (comp == 0) {
                return EstadoRegistro.Repetido;
            }
            if (comp == -1) {
                if (!aprovado(cursoR)) { // ya no puede planear dos veces
                    registro.setAnterior(cursoR);
                    return EstadoRegistro.Previo;
                }
                return EstadoRegistro.Inconsistente; // incosistencia, si ya paso el curso no debe porque verlo en el periodo reciente
            }
            if (!aprovado(registro)) { //perdio el curso y lo va a volver a ver
                cursoR.setAnterior(registro);
                return EstadoRegistro.Ok;
            }
            return EstadoRegistro.Repetido;
        }
        return EstadoRegistro.Ok;
    }


    public EstadoRegistro validarRequerimiento(CursoRegistrado cursoR, String reqN){
        Requerimiento req = pensum.getRequerimientos().get(reqN);
        if(cursoR == null || req == null){
            return EstadoRegistro.Inexistente;
        }
        if(aprovado(cursoR)){
            if(cursosValidados.get(cursoR.getCurso().getCodigo()).equals(reqN)){
                return EstadoRegistro.Conflicto;
            }
            RequerimientoRegistrado reqR = reqsRegistrados.getOrDefault(reqN,new RequerimientoRegistrado(req));
            EstadoRegistro valid = reqR.agregarCurso(cursoR);
            if(valid == EstadoRegistro.Ok){
                reqsRegistrados.putIfAbsent(reqN,reqR);
                cursosValidados.put(cursoR.getCurso().getCodigo(),reqN);
            }
            return valid;
        }else{
            return EstadoRegistro.Restriccion;
        }
    }

    public PreRestriccion revisarRestriciones(CursoRegistrado cursoR, Periodo periodo) {
        ArrayList<PreRestriccion> restriccions = cursoR.getCurso().getRestricciones();
        for(PreRestriccion rst: restriccions){
            if(!rst.cumple(cursoR,this, periodo)){
                return rst;
            }
        }
        return null;
    }


    public Periodo quitarCursos(ArrayList<CursoRegistrado> cursosQuitar){
        Periodo primerCambio = null;
        for (CursoRegistrado cr: cursosQuitar) {
            Periodo p = cr.getPeriodo();
            if (primerCambio == null || primerCambio.compare(p) == 1) {
                primerCambio = p;
            }
            String semestre = p.periodoS();
            infoSemestre.get(semestre).remove(cr);
        }
        return primerCambio;
    }

    public ArrayList<EstadoAgregar>  actulizarMalla(ArrayList<CursoRegistrado> cursosAgregar,
                                                   ArrayList<CursoRegistrado> cursosQuitar){
        ArrayList<CursoRegistrado> queue = new ArrayList<>(cursosAgregar);
        Periodo primerCambio = quitarCursos(cursosQuitar);
        if(primerCambio != null){
            queue.addAll(vaciarDesde(primerCambio));
        }
        return  agregarCursos(queue);
    }

    public ArrayList<CursoRegistrado> vaciarDesde(Periodo primerCambio){
        ArrayList<CursoRegistrado> queue = new ArrayList<>();
        ArrayList<String> semestres = new ArrayList<>(infoSemestre.keySet());
        semestres.sort(String::compareTo);
        int inicio = semestres.indexOf(primerCambio.periodoS());
        for(String semestre: semestres.subList(inicio,semestres.size()-1)){
            ArrayList<CursoRegistrado> cursosP = infoSemestre.get(semestre);
            queue.addAll(cursosP);
            infoSemestre.remove(semestre);
            for(CursoRegistrado cr: cursosP) {
                String codigo = cr.getCurso().getCodigo();
                CursoRegistrado registro = cursosRegistrados.get(codigo);
                creditos -= cr.getCurso().getCreditos();
                if (registro != null && registro == cr) {
                    cursosRegistrados.remove(codigo);
                    if (cr.getNota().aprobo()) {
                        creditosAprovados -= cr.getCurso().getCreditos();
                    }
                    if (registro.isRepetido()) {
                        CursoRegistrado anterior = registro.getAnterior();
                        if(anterior.getPeriodo().compare(primerCambio) == -1){
                            cursosRegistrados.put(codigo,anterior);
                        }
                    }
                }
            }
        }
        return queue;
    }

    public ArrayList<Requerimiento> reqFaltantes(){
        ArrayList<Requerimiento> lr = new ArrayList<>();
        for(Requerimiento rq: pensum.getRequerimientos().values()){
            if(!cumplioReq(rq)){
                lr.add(rq);
            }
        }
        return lr;
    }

    public boolean cumplioReq(Requerimiento rq){
        return itemsCumplidos(rq.getNombre(),peridoSistema) >= rq.getItems();
    }

    public abstract void agregarPeriodo(Periodo periodo);
    public abstract CursoRegistrado getCurReg(String codigo);
    public abstract boolean dentroPeriodo(Periodo p);
    public abstract Periodo getPHis();
    public abstract boolean aprovado(CursoRegistrado cursoR);
    public abstract int itemsCumplidos(String reqN, Periodo periodo);
    public abstract int itemsCumplidos(String reqN);
    public int getCreditos() {
        return creditos;
    }
    public int getCreditosAprovados() {
        return creditosAprovados;
    }
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
    public Map<String, ArrayList<CursoRegistrado>> getInfoSemestres() {
        return infoSemestre;
    }

}

