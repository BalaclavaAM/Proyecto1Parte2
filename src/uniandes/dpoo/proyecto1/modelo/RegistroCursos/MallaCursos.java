package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Pensum;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Registro.*;
import uniandes.dpoo.proyecto1.modelo.Restricciones.*;

import java.util.*;

public abstract class MallaCursos {
    protected static final long serialVersionUID = -491840464239633611L;
    protected Pensum pensum;
    protected Periodo peridoSistema;
    protected double creditos = 0;
    protected double creditosAprovados = 0;
    protected Map<String, CursoRegistrado> cursosRegistrados;
    protected Map<String, RequerimientoRegistrado> reqsRegistrados;
    protected Map<String, String> cursosValidados;// <CodigoCurso,NombreRequerimiento>
    protected Map<String, Double> conteoSemestres;
    protected Map<String,  ArrayList<CursoRegistrado>> infoSemestres; //dentro de un Semestre pueden haber dos periodos por los ciclos
    //se cuenta a semestre a los intersemestrales

    public MallaCursos(Periodo periodoSis){
        this.peridoSistema = periodoSis;
        this.conteoSemestres = new HashMap<>();
        this.cursosRegistrados = new Hashtable<>();
        this.reqsRegistrados = new Hashtable<>();
        this.cursosValidados = new Hashtable<>();
        this.infoSemestres = new Hashtable<>();
    }

    public ArrayList<EstadoAgregar> agregarCursos(ArrayList<CursoRegistrado> cursosR) {
        return agregarCursos(cursosR,false);
    }


    protected ArrayList<EstadoAgregar> agregarCursos(ArrayList<CursoRegistrado> cursosR, boolean ins) {
        ArrayList<EstadoAgregar> estado = new ArrayList<>();
        Map<String, ArrayList<CursoRegistrado>> cursosPeriodos = new Hashtable<>();
        ArrayList<String> Lperiodos = new ArrayList<>();
        formatoAgregar(cursosR, cursosPeriodos, Lperiodos, estado);
        for (String p : Lperiodos) {
            ArrayList<CursoRegistrado> cursosP = cursosPeriodos.get(p);

            Periodo pc = Periodo.stringToPeriodo(p);
            if (dentroPeriodo(pc) || (ins && dentroIns(pc))) {
                agregarPeriodo(Periodo.stringToPeriodo(p));
                agregarCursosPeriodo(cursosP, pc, estado);
                if (infoSemestres.get(pc.periodoS()).size() == 0) {
                    infoSemestres.remove(pc.periodoS());
                }
            } else {
                estado.add(new EstadoAgregar(pc, EstadoRegistro.Inconsistente));
                return estado;
            }
        }
        return estado;
    }

    private boolean dentroIns(Periodo p){
        if(p.periodoS().equals(peridoSistema.periodoS())){
            return p.getCiclo() >= peridoSistema.getCiclo();
        }
        return false;
    }

    public ArrayList<CursoRegistrado> agregarCursosPeriodo(ArrayList<CursoRegistrado> cursosP, Periodo periodo, ArrayList<EstadoAgregar> estado) {
        Map<CursoRegistrado, ArrayList<Correquisito>> cursosCorreq = new HashMap<>();
        Map<CursoRegistrado, EstadoRegistro> estadosRegistros = revisarConsistenciaPeriodo(cursosP, estado, periodo);
        Prerrequisito.cursosCumple(cursosP, this, periodo, estado);
        RestriccionReq.cursosCumple(cursosP, this, periodo, estado);
        RestriccionNivel.cursosCumple(cursosP, this, periodo, estado);
        Correquisito.cursosCumple(cursosP, this, periodo, estado);
        for(CursoRegistrado cr: cursosP){
            if(limiteValido(cr.getCurso().getCreditos(),periodo)) {
                double conteo =conteoSemestres.get(periodo.periodoS());
                conteoSemestres.put(periodo.periodoS(), conteo +cr.getCurso().getCreditos());
                agregarCurso(cr, estadosRegistros.get(cr));
            }else{
                estado.add(new EstadoAgregar(cr,EstadoRegistro.SobreInscripcion));
            }
        }
        return cursosP;
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
        cursoR.getPeriodo().periodoS();
        infoSemestres.get(cursoR.getPeriodo().periodoS()).add(cursoR);
        creditos += curso.getCreditos();
        if(cursoR.getNota().aprobo()){
            creditosAprovados += curso.getCreditos();
        }
    }

    public void formatoAgregar(ArrayList<CursoRegistrado> cursosR, Map<String, ArrayList<CursoRegistrado>> cursosPeriodos,
                               ArrayList<String> Lperiodos,
                               ArrayList<EstadoAgregar> estado){

        String acperiodo;
        for(CursoRegistrado cr: cursosR){
            acperiodo = cr.getPeriodo().toString();
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
            }
        }
        Lperiodos.sort(String::compareTo);
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

    private Map<CursoRegistrado,EstadoRegistro> revisarConsistenciaPeriodo(ArrayList<CursoRegistrado> cursosP,
                                                                           ArrayList<EstadoAgregar> estado,
                                                                           Periodo periodo) {
        Map<CursoRegistrado,EstadoRegistro> mapCrEr = new HashMap<>();
        for (int i = cursosP.size()-1; i > -1 ; i--) {
            CursoRegistrado cr = cursosP.get(i);
            EstadoRegistro ea = revisarConsistencia(cr,periodo);
            if(ea == EstadoRegistro.Ok || ea == EstadoRegistro.Previo){
                mapCrEr.put(cr,ea);
            }else{
                cursosP.remove(i);
                estado.add(new EstadoAgregar(cr, ea));
            }
        }
        return mapCrEr;
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
            String reqNR = cursosValidados.get(cursoR.getCurso().getCodigo());
            if( reqNR != null && reqNR.equals(reqN)){
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



    public Periodo quitarCursos(ArrayList<CursoRegistrado> cursosQuitar){
        Periodo primerCambio = null;
        for (CursoRegistrado cr: cursosQuitar) {
            Periodo p = cr.getPeriodo();
            if (primerCambio == null || primerCambio.compare(p) == 1) {
                primerCambio = p;
            }
            String semestre = p.periodoS();
            infoSemestres.get(semestre).remove(cr);
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
        return  agregarCursos(queue,false);
    }

    public ArrayList<CursoRegistrado> vaciarDesde(Periodo primerCambio){
        ArrayList<CursoRegistrado> queue = new ArrayList<>();
        ArrayList<String> semestres = new ArrayList<>(infoSemestres.keySet());
        semestres.sort(String::compareTo);
        int inicio = semestres.indexOf(primerCambio.periodoS());
        for(String semestre: semestres.subList(inicio,semestres.size()-1)){
            ArrayList<CursoRegistrado> cursosP = infoSemestres.get(semestre);
            queue.addAll(cursosP);
            infoSemestres.remove(semestre);
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

    public Map<Requerimiento, Double> reqFaltantes(){
        Map<Requerimiento,Double> mr = new HashMap<>();
        for(Requerimiento rq: pensum.getRequerimientos().values()){
            double avance = itemsCumplidos(rq.getNombre())/(double)(rq.getItems());
            if(avance <1){
                mr.put(rq,avance);
            }
        }
        return mr;
    }

    public Map<Requerimiento, Double> avanceReq(){
        Map<Requerimiento,Double> mr = new HashMap<>();
        for(Requerimiento rq: pensum.getRequerimientos().values()){
            mr.put(rq,itemsCumplidos(rq.getNombre())/(double)(rq.getItems()));
        }
        return mr;
    }

    public abstract boolean limiteValido(double creditos, Periodo periodo);


    public abstract void agregarPeriodo(Periodo periodo);
    public abstract CursoRegistrado getCurReg(String codigo);
    public abstract boolean dentroPeriodo(Periodo p);
    public abstract Periodo getPHis();
    public abstract boolean aprovado(CursoRegistrado cursoR);
    public abstract int itemsCumplidos(String reqN, Periodo periodo);
    public abstract int itemsCumplidos(String reqN);
    public double getCreditos() {
        return creditos;
    }
    public double getCreditosAprovados() {
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
        return infoSemestres;
    }

}

