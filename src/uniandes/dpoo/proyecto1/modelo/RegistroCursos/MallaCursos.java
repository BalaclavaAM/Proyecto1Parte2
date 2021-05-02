package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Registro.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public abstract class MallaCursos {
    protected static final long serialVersionUID = -491840464239633611L;
    protected Pensum pensum;
    protected Periodo periodo;
    protected   int creditos = 0;

    protected Map<String, ArrayList<Periodo>> periodosMap; //es una mapa que agrupa los periodos en periodos simples
    protected Map<String, CursoRegistrado> cursosRegistrados;
    protected Map<String, RequerimientoRegistrado> reqsRegistrados;
    protected Map<String, Curso> cursosValidados;
    protected Map<Periodo, Map<String, CursoRegistrado>> infoPeriodos; //informacion por periodos


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

    public int agregarCursoxPeriodo(Curso curso, EstadoCurso estadoC ,  ArrayList<Curso> cursosP, Periodo periodo){
        String codigo = curso.getCodigo();
        CursoRegistrado cr = getCurReg(codigo);
        if(cr != null){
            int comp = periodo.compare(cr.getPeriodo());
            if( comp == 0){
                return 2;
            }
            if(comp == -1){
                if(cr.getEstado() == EstadoCurso.Planeado){ // le dejamos planear dos veces el mismo curso
                    return auxCursoXPeriodo(curso,cursosP,estadoC,periodo);
                }
                return -3; // no puedes planear o inscribirlo antes
            }
            if(cr.getEstado() == EstadoCurso.Planeado || !cr.getNota().aprobo()){
                return auxCursoXPeriodo(curso,cursosP,estadoC,periodo);
            }
            return 2;
        }
        return auxCursoXPeriodo(curso,cursosP,estadoC,periodo);
    }


    public void modificarHistoria(CursoRegistrado cursoR, Periodo periodo){
        Curso curso = cursoR.getCurso();
        String codigo = cursoR.getCurso().getCodigo();

        String reqAsociado = pensum.getCursosValidacionAuto().get(codigo);
        if(reqAsociado != null){
            validarRequerimiento(codigo,reqAsociado);
        }
        Map<String,CursoRegistrado> infoPerido =  infoPeriodos.computeIfAbsent(periodo, k-> new Hashtable<>());
        infoPerido.putIfAbsent(codigo, cursoR);
        creditos += curso.getCreditos();
    }


    public abstract CursoRegistrado getCurReg(String codigo);


    public int auxCursoXPeriodo(Curso curso,ArrayList<Curso> cursosP ,EstadoCurso estadoC, Periodo periodo){
        int rest = revisarRestriciones(curso, cursosP, periodo);
        if( rest != 1){
            return rest;
        }
        CursoRegistrado regist = new CursoRegistrado(curso, periodo, estadoC);
        cursosRegistrados.put(curso.getCodigo(),regist);
        modificarHistoria(regist, periodo);
        return 1;
    }




    public int validarRequerimiento(String codigo, String reqN){
        Requerimiento req = pensum.getRequerimientos().get(reqN);
        CursoRegistrado cursoR = cursosRegistrados.get(codigo);
        if(cursoR == null || req == null){
            return -1;
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


    public void formatoAgregar(ArrayList<Curso> cursos, ArrayList<Periodo> periodos,
                                        Map<Periodo, ArrayList<Curso>> cursosPeriodos,
                                        Map<Curso, Integer> infoCursos, ArrayList<Periodo> Lperiodos,
                                        ArrayList<EstadoAgregar> estado){

        Periodo acperiodo = null;
        Curso accurso = null;

        for (int i = 0; i < cursos.size(); i++) {
            acperiodo = periodos.get(i);
            accurso =  cursos.get(i);
            if(infoCursos.containsKey(accurso)){
                estado.add(new EstadoAgregar(3,acperiodo,accurso));
            }else{
                infoCursos.put(accurso,i);
                ArrayList<Curso> cursosP = cursosPeriodos.get(acperiodo);
                if(cursosP == null){
                    cursosP = new ArrayList<>();
                    cursosPeriodos.put(acperiodo,cursosP);
                    Lperiodos.add(acperiodo);
                }
                cursosP.add(accurso);
            }

        }
        Lperiodos.sort(Periodo::compare);
    }


    public abstract int revisarRestriciones(Curso curso, ArrayList<Curso> cursosP, Periodo periodo);

/*
    public abstract int revisarRestriciones(Curso curso, ArrayList<Curso> cursosP);
*/
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
