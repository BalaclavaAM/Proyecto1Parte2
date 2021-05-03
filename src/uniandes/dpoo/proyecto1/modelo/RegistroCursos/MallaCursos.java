package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
<<<<<<< Updated upstream
<<<<<<< HEAD
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;
import uniandes.dpoo.proyecto1.modelo.Registro.PeriodoS;
import uniandes.dpoo.proyecto1.modelo.Registro.RequerimientoRegistrado;
=======
import uniandes.dpoo.proyecto1.modelo.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Registro.*;
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
import uniandes.dpoo.proyecto1.modelo.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Registro.*;
>>>>>>> Stashed changes

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public abstract class MallaCursos {
    protected static final long serialVersionUID = -491840464239633611L;
    protected Pensum pensum;
    protected Periodo periodo;
<<<<<<< Updated upstream
<<<<<<< HEAD
    protected   int creditos = 0;
=======
    protected int creditos = 0;

>>>>>>> Stashed changes
    protected Map<String, ArrayList<Periodo>> periodosMap; //es una mapa que agrupa los periodos en periodos simples
    protected Map<String, CursoRegistrado> cursosRegistrados;
    protected Map<String, RequerimientoRegistrado> reqsRegistrados;
    protected Map<String, Curso> cursosValidados;
    protected Map<Periodo, Map<String, CursoRegistrado>> infoPeriodos; //informacion por periodos


    public void agregarPeriodo(Periodo periodo) {
        String periodoS = PeriodoS.periodoS(periodo.getAnio(), periodo.getSemestre());
        ArrayList<Periodo> periodosL = periodosMap.computeIfAbsent(periodoS, k -> new ArrayList<>());
        if (periodosL.contains(periodo)) {
            periodosL.add(periodo);
        }
<<<<<<< Updated upstream
        if(periodo.compare(this.periodo) == 1){
=======
    protected int creditos = 0;

    protected Map<String, ArrayList<Periodo>> periodosMap; //es una mapa que agrupa los periodos en periodos simples
    protected Map<String, CursoRegistrado> cursosRegistrados;
    protected Map<String, RequerimientoRegistrado> reqsRegistrados;
    protected Map<String, Curso> cursosValidados;
    protected Map<Periodo, Map<String, CursoRegistrado>> infoPeriodos; //informacion por periodos


    public void agregarPeriodo(Periodo periodo) {
        String periodoS = PeriodoS.periodoS(periodo.getAnio(), periodo.getSemestre());
        ArrayList<Periodo> periodosL = periodosMap.computeIfAbsent(periodoS, k -> new ArrayList<>());
        if (periodosL.contains(periodo)) {
            periodosL.add(periodo);
        }
        if (periodo.compare(this.periodo) == 1) {
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
        if (periodo.compare(this.periodo) == 1) {
>>>>>>> Stashed changes
            this.periodo = periodo;
        }
    }

<<<<<<< Updated upstream
<<<<<<< HEAD
    public void modificarHistoria(Curso curso, Periodo periodo){
=======
    public int agregarCursoxPeriodo(Curso curso, EstadoCurso estadoC, ArrayList<Curso> cursosP, Periodo periodo) {
>>>>>>> Stashed changes
        String codigo = curso.getCodigo();
        CursoRegistrado cr = getCurReg(codigo);
        if (cr != null) {
            int comp = periodo.compare(cr.getPeriodo());
            if (comp == 0) {
                return 2;
            }
            if (comp == -1) {
                if (cr.getEstado() == EstadoCurso.Planeado) { // le dejamos planear dos veces el mismo curso
                    return auxCursoXPeriodo(curso, cursosP, estadoC, periodo);
                }
                return -3; // no puedes planear o inscribirlo antes
            }
            if (cr.getEstado() == EstadoCurso.Planeado || !cr.getNota().aprobo()) {
                return auxCursoXPeriodo(curso, cursosP, estadoC, periodo);
            }
            return 2;
        }
        return auxCursoXPeriodo(curso, cursosP, estadoC, periodo);
    }

    public abstract CursoRegistrado getCurReg(String codigo);

    public void modificarHistoria(CursoRegistrado cursoR, Periodo periodo) {
        Curso curso = cursoR.getCurso();
        String codigo = cursoR.getCurso().getCodigo();

        String reqAsociado = pensum.getCursosValidacionAuto().get(codigo);
        if (reqAsociado != null) {
            Requerimiento req = pensum.getRequerimientos().get(reqAsociado);

            validarRequerimiento(codigo, reqAsociado);
        }
        Map<String, CursoRegistrado> infoPerido = infoPeriodos.computeIfAbsent(periodo, k -> new Hashtable<>());
        infoPerido.putIfAbsent(codigo, cursoR);
        creditos += curso.getCreditos();
    }


    public int auxCursoXPeriodo(Curso curso, ArrayList<Curso> cursosP, EstadoCurso estadoC, Periodo periodo) {
        int rest = revisarRestriciones(curso, cursosP, periodo);
        if (rest != 1) {
            return rest;
        }
        CursoRegistrado regist = new CursoRegistrado(curso, periodo, estadoC);
        cursosRegistrados.put(curso.getCodigo(), regist);
        modificarHistoria(regist, periodo);
        return 1;
    }


<<<<<<< Updated upstream
    public int validarRequerimientoAux(Curso curso, Requerimiento req){
        String codigo = curso.getCodigo();

=======
    public int agregarCursoxPeriodo(Curso curso, EstadoCurso estadoC, ArrayList<Curso> cursosP, Periodo periodo) {
        String codigo = curso.getCodigo();
        CursoRegistrado cr = getCurReg(codigo);
        if (cr != null) {
            int comp = periodo.compare(cr.getPeriodo());
            if (comp == 0) {
                return 2;
            }
            if (comp == -1) {
                if (cr.getEstado() == EstadoCurso.Planeado) { // le dejamos planear dos veces el mismo curso
                    return auxCursoXPeriodo(curso, cursosP, estadoC, periodo);
                }
                return -3; // no puedes planear o inscribirlo antes
            }
            if (cr.getEstado() == EstadoCurso.Planeado || !cr.getNota().aprobo()) {
                return auxCursoXPeriodo(curso, cursosP, estadoC, periodo);
            }
            return 2;
        }
        return auxCursoXPeriodo(curso, cursosP, estadoC, periodo);
    }

    public abstract CursoRegistrado getCurReg(String codigo);

    public void modificarHistoria(CursoRegistrado cursoR, Periodo periodo) {
        Curso curso = cursoR.getCurso();
        String codigo = cursoR.getCurso().getCodigo();

        String reqAsociado = pensum.getCursosValidacionAuto().get(codigo);
        if (reqAsociado != null) {
            Requerimiento req = pensum.getRequerimientos().get(reqAsociado);

            validarRequerimiento(codigo, reqAsociado);
        }
        Map<String, CursoRegistrado> infoPerido = infoPeriodos.computeIfAbsent(periodo, k -> new Hashtable<>());
        infoPerido.putIfAbsent(codigo, cursoR);
        creditos += curso.getCreditos();
    }


    public int auxCursoXPeriodo(Curso curso, ArrayList<Curso> cursosP, EstadoCurso estadoC, Periodo periodo) {
        int rest = revisarRestriciones(curso, cursosP, periodo);
        if (rest != 1) {
            return rest;
        }
        CursoRegistrado regist = new CursoRegistrado(curso, periodo, estadoC);
        cursosRegistrados.put(curso.getCodigo(), regist);
        modificarHistoria(regist, periodo);
        return 1;
    }


=======
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
>>>>>>> Stashed changes
        if(cursosValidados.containsKey(codigo)){
            return 2;
        }
        RequerimientoRegistrado reqR = reqsRegistrados.computeIfAbsent(req.getNombre(), k ->new RequerimientoRegistrado(req));
<<<<<<< Updated upstream
<<<<<<< HEAD
        int valid = reqR.agregarCurso(curso);
=======
        int valid = reqR.agregarCurso(cursoR, periodo);
>>>>>>> Stashed changes
        if(valid == 1){
            cursosValidados.put(codigo,cursoR.getCurso());
        }
        return valid;

    }

<<<<<<< Updated upstream
    public EstadoAgregar formatoAgregar(ArrayList<Curso> cursos, ArrayList<Periodo> periodos,
                                        Map<Periodo, ArrayList<Curso>> cursosPeriodos,
                                        Map<Curso, Integer> infoCursos, ArrayList<Periodo> Lperiodos){
=======
        int valid = reqR.agregarCurso(cursoR, periodo);
        if(valid == 1){
            cursosValidados.put(codigo,cursoR.getCurso());
        }
        return valid;

    }

=======
>>>>>>> Stashed changes

    public void formatoAgregar(ArrayList<Curso> cursos, ArrayList<Periodo> periodos,
                                        Map<Periodo, Map<String, intCurso>> cursosPeriodos, ArrayList<Periodo> Lperiodos,
                                        ArrayList<EstadoAgregar> estado){
<<<<<<< Updated upstream
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
>>>>>>> Stashed changes

        Periodo acperiodo = null;
        Curso accurso = null;

        for (int i = 0; i < cursos.size(); i++) {
            acperiodo = periodos.get(i);
            accurso =  cursos.get(i);
<<<<<<< Updated upstream
<<<<<<< HEAD
            if(infoCursos.containsKey(accurso)){
                return new EstadoAgregar(3,acperiodo,accurso);
=======
            String codigo = accurso.getCodigo();
            if(cursosPeriodos.get(acperiodo).containsKey(codigo)){
                estado.add(new EstadoAgregar(3,acperiodo,accurso));
            }else{
               Map<String, intCurso> cursosP = cursosPeriodos.get(acperiodo);
                if(cursosP == null){
                    cursosP = new Hashtable<>();
                    cursosPeriodos.put(acperiodo,cursosP);
                    Lperiodos.add(acperiodo);
                }
                cursosP.put(codigo, new intCurso(accurso, i));
>>>>>>> Stashed changes
            }
        }
        Lperiodos.sort(Periodo::compare);
    }

    protected class intCurso {
        public int indice;
        public Curso curso;
        public intCurso(Curso curso, int indice){
            this.curso = curso;
            this.indice = indice;
        }
    }



    public abstract int revisarRestriciones(Curso curso, ArrayList<Curso> cursosP, Periodo periodo);

<<<<<<< Updated upstream
=======
            String codigo = accurso.getCodigo();
            if(cursosPeriodos.get(acperiodo).containsKey(codigo)){
                estado.add(new EstadoAgregar(3,acperiodo,accurso));
            }else{
               Map<String, intCurso> cursosP = cursosPeriodos.get(acperiodo);
                if(cursosP == null){
                    cursosP = new Hashtable<>();
                    cursosPeriodos.put(acperiodo,cursosP);
                    Lperiodos.add(acperiodo);
                }
                cursosP.put(codigo, new intCurso(accurso, i));
            }
        }
        Lperiodos.sort(Periodo::compare);
    }

    protected class intCurso {
        public int indice;
        public Curso curso;
        public intCurso(Curso curso, int indice){
            this.curso = curso;
            this.indice = indice;
        }
    }



    public abstract int revisarRestriciones(Curso curso, ArrayList<Curso> cursosP, Periodo periodo);

/*
    public abstract int revisarRestriciones(Curso curso, ArrayList<Curso> cursosP);
*/
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
/*
    public abstract int revisarRestriciones(Curso curso, ArrayList<Curso> cursosP);
*/
>>>>>>> Stashed changes
    public Pensum getPensum() {
        return pensum;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public Map<String, ArrayList<Periodo>> getPeriodosMap() {
        return periodosMap;
    }

<<<<<<< Updated upstream
<<<<<<< HEAD
    public Map<String, Curso> getCursos() {
        return cursos;
=======
    public Map<String, CursoRegistrado> getCursosRegistrados() {
        return cursosRegistrados;
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
    public Map<String, CursoRegistrado> getCursosRegistrados() {
        return cursosRegistrados;
>>>>>>> Stashed changes
    }

    public Map<String, RequerimientoRegistrado> getReqsRegistrados() {
        return reqsRegistrados;
    }

    public Map<String, Curso> getCursosValidados() {
        return cursosValidados;
    }

<<<<<<< Updated upstream
<<<<<<< HEAD
    public Map<Periodo, Map<String, Curso>> getInfoPeriodos() {
        return infoPeriodos;
    }
}
=======
    public Map<Periodo, Map<String, CursoRegistrado>> getInfoPeriodos() {
        return infoPeriodos;
    }
}

>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
    public Map<Periodo, Map<String, CursoRegistrado>> getInfoPeriodos() {
        return infoPeriodos;
    }
}

>>>>>>> Stashed changes
