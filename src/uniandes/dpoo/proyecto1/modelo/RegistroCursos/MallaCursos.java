package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;
import uniandes.dpoo.proyecto1.modelo.Registro.PeriodoS;
import uniandes.dpoo.proyecto1.modelo.Registro.RequerimientoRegistrado;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public abstract class MallaCursos {
    protected static final long serialVersionUID = -491840464239633611L;
    protected Pensum pensum;
    protected Periodo periodo;
    protected   int creditos = 0;
    protected Map<String, ArrayList<Periodo>> periodosMap; //es una mapa que agrupa los periodos en periodos simples
    protected Map<String, Curso> cursos;
    protected Map<String, RequerimientoRegistrado> reqsRegistrados;
    protected Map<String, Curso> cursosValidados;
    protected Map<Periodo, Map<String, Curso>> infoPeriodos; //informacion por periodos

    public void agregarPeriodo(Periodo periodo){
        String periodoS = PeriodoS.periodoS(periodo.getAnio(),periodo.getSemestre());
        ArrayList<Periodo> periodosL = periodosMap.computeIfAbsent(periodoS, k -> new ArrayList<>());
        if(periodosL.contains(periodo)){
            periodosL.add(periodo);
        }
        if(periodo.compare(this.periodo) == 1){
            this.periodo = periodo;
        }
    }

    public void modificarHistoria(Curso curso, Periodo periodo){
        String codigo = curso.getCodigo();
        String reqAsociado = pensum.getCursosValidacionAuto().get(codigo);
        if(reqAsociado != null){
            validarRequerimiento(codigo,reqAsociado);
        }
        Map<String,Curso> infoPerido =  infoPeriodos.computeIfAbsent(periodo, k-> new Hashtable<>());
        infoPerido.putIfAbsent(codigo, curso);
        creditos += curso.getCreditos();
    }

    public int validarRequerimiento(String codigo, String reqN){
        Requerimiento req = pensum.getRequerimientos().get(reqN);

        if(cursos.get(codigo) == null || req == null){
            return -1;
        }
        Curso curso = cursos.get(codigo);
        return validarRequerimientoAux(curso, req);

    }


    public int validarRequerimientoAux(Curso curso, Requerimiento req){
        String codigo = curso.getCodigo();

        if(cursosValidados.containsKey(codigo)){
            return 2;
        }
        RequerimientoRegistrado reqR = reqsRegistrados.computeIfAbsent(req.getNombre(), k ->new RequerimientoRegistrado(req));
        int valid = reqR.agregarCurso(curso);
        if(valid == 1){
            cursosValidados.put(codigo,curso);
        }
        return valid;
    }


    static void formatAux(Map<Periodo, ArrayList<Curso>> cursosPeriodos, ArrayList<Periodo> Lperiodos, Periodo acperiodo,
                          Curso accurso) {
        ArrayList<Curso> cursosP = cursosPeriodos.get(acperiodo);
        if(cursosP == null){
            cursosP = new ArrayList<>();
            cursosPeriodos.put(acperiodo,cursosP);
            Lperiodos.add(acperiodo);
        }
        cursosP.add(accurso);
    }

    public EstadoAgregar formatoAgregar(ArrayList<Curso> cursos, ArrayList<Periodo> periodos,
                                        Map<Periodo, ArrayList<Curso>> cursosPeriodos,
                                        Map<Curso, Integer> infoCursos, ArrayList<Periodo> Lperiodos){

        Periodo acperiodo = null;
        Curso accurso = null;

        for (int i = 0; i < cursos.size(); i++) {
            acperiodo = periodos.get(i);
            accurso =  cursos.get(i);
            if(infoCursos.containsKey(accurso)){
                return new EstadoAgregar(3,acperiodo,accurso);
            }
            infoCursos.put(accurso,i);
            MallaCursos.formatAux(cursosPeriodos, Lperiodos, acperiodo, accurso);
        }
        Lperiodos.sort(Periodo::compare);
        return new EstadoAgregar(1, acperiodo,accurso);
    }


    public abstract int revisarRestriciones(Curso curso, ArrayList<Curso> cursosP, Periodo periodo);

    public Pensum getPensum() {
        return pensum;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public Map<String, ArrayList<Periodo>> getPeriodosMap() {
        return periodosMap;
    }

    public Map<String, Curso> getCursos() {
        return cursos;
    }

    public Map<String, RequerimientoRegistrado> getReqsRegistrados() {
        return reqsRegistrados;
    }

    public Map<String, Curso> getCursosValidados() {
        return cursosValidados;
    }

    public Map<Periodo, Map<String, Curso>> getInfoPeriodos() {
        return infoPeriodos;
    }
}
