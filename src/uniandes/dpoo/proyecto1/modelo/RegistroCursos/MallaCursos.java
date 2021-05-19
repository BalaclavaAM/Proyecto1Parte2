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
    protected Map<String,Integer> conteoSemestre;
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
                validarRequerimiento(cursoR, reqAsociado);
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

        Map<String,Integer> conteoSemestreLocal = new HashMap<>();

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
                    return EstadoRegistro.Previo;
                }
                return EstadoRegistro.Inconsistente; // incosistencia, si ya paso el curso no debe porque verlo en el periodo reciente
            }
            if (!aprovado(registro)) { //perdio el curso y lo va a volver a ver
                cursoR.setRepetido(true);
                return EstadoRegistro.Ok;
            }
            return EstadoRegistro.Repetido;
        }
        return EstadoRegistro.Ok;
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



    public ArrayList<EstadoAgregar>  actulizarMalla(Map<String, CursoRegistrado> cursosAgregar,
                                                    Map<String,ArrayList<CursoRegistrado>> cursosQuitar){

        ArrayList<CursoRegistrado> queue = new ArrayList<>(cursosAgregar.values());
        Periodo primerCambio = null;

        for (String pString: cursosQuitar.keySet()) {
            Periodo p = Periodo.stringToPeriodo(pString);
            if(primerCambio == null || primerCambio.compare(p) == 1){
                primerCambio = p;
            }
            String semestre = p.periodoS();
            ArrayList<CursoRegistrado> cursoQuitarP = cursosQuitar.get(pString);
            infoSemestre.get(semestre).removeAll(cursoQuitarP);
            for(CursoRegistrado cr: cursoQuitarP){
                String codigo = cr.getCurso().getCodigo();
                CursoRegistrado registro = cursosRegistrados.get(codigo);
                creditos += cr.getCurso().getCreditos();
                if(registro != null && registro.getPeriodo().compare(cr.getPeriodo())== 0) {
                    cursosRegistrados.remove(codigo);
                    if(cr.getNota().aprobo()){
                        creditosAprovados -= cr.getCurso().getCreditos();
                    }
                    if(registro.isRepetido()){
                        cursosRegistrados.put(codigo,buscarAnterior(registro));
                    }
                }
            }
        }
        return  agregarCursos(queue);
    }


    public CursoRegistrado buscarAnterior(CursoRegistrado cursoR) {
        ArrayList<String> semestres = new ArrayList<>(infoSemestre.keySet());
        CursoRegistrado anterior = null;
        semestres.sort(String::compareTo);
        for (int i = semestres.size()-1; i > -1; i--) {
            ArrayList<CursoRegistrado> cursoS =  infoSemestre.get(semestres.get(i));
            for(CursoRegistrado cr : cursoS) {
                if(cr.getCurso().getCodigo().equals(cursoR.getCurso().getNombre())){
                    if(anterior == null || anterior.getPeriodo().getCiclo() > cr.getPeriodo().getCiclo()){
                        anterior = cr;
                    }
                }
            }
            if(anterior != null){
                return anterior;
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

    public Map<String, ArrayList<CursoRegistrado>> getInfoPeriodos() {
        return infoSemestre;
    }



}

