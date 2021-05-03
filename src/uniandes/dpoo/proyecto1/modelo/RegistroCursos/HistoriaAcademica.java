package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

<<<<<<< Updated upstream
<<<<<<< HEAD
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Restricciones.Restriccion;
import uniandes.dpoo.proyecto1.modelo.Registro.*;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.calCual;

=======
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
=======
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
>>>>>>> Stashed changes
import uniandes.dpoo.proyecto1.modelo.Restricciones.Restriccion;
import uniandes.dpoo.proyecto1.modelo.Registro.*;
import uniandes.dpoo.proyecto1.modelo.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Nota.calCual;
<<<<<<< Updated upstream
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
>>>>>>> Stashed changes

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


public class HistoriaAcademica extends MallaCursos implements Serializable {
<<<<<<< Updated upstream
<<<<<<< HEAD
	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = -491840464239633611L;
	private float creditos = 0;
    private Map<String, Curso> cursosInscritos;
    private Map<String, CursoRegistrado> cursosRegistrados;

=======
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -491840464239633611L;
    private Map<String, Curso> cursosInscritos;
    public Nota notaP = new NotaCual(calCual.pendiente);
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -491840464239633611L;
    private Map<String, Curso> cursosInscritos;
    public Nota notaP = new NotaCual(calCual.pendiente);
>>>>>>> Stashed changes



    public HistoriaAcademica(Pensum pensum, Periodo periodo) {
		this.pensum = pensum;
		this.periodo = periodo;
		this.periodosMap = new Hashtable<>();
		this.cursosRegistrados = new Hashtable<>();
		this.reqsRegistrados = new Hashtable<>();
		this.cursosValidados = new Hashtable<>();
		this.cursosInscritos = new Hashtable<>();
		this.infoPeriodos = new Hashtable<>();
	}

	public float getCreditos() {
		return creditos;
	}

<<<<<<< Updated upstream
<<<<<<< HEAD

=======
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
>>>>>>> Stashed changes
    public Map<String, CursoRegistrado> getCursosRegistrados() {
        return cursosRegistrados;
    }

    public boolean setPeriodo(Periodo periodo) {
		return false;
	}

    public Map<String, Curso> getCursosInscritos() {
        return cursosInscritos;
    }

<<<<<<< Updated upstream
<<<<<<< HEAD
    public int inscribirCursoxPeriodo(Curso curso, Periodo periodo, ArrayList<Curso> cursosP){
        //auxiliar para agregar un conjunto de cursos
        CursoRegistrado cursoR = cursosRegistrados.get(curso.getCodigo());
        if(cursoR != null && cursoR.getNota().aprobo()){
            return 2;
        }
        int rest = revisarRestriciones(curso, cursosP, periodo);
        if( rest != 1){
            return rest;
=======

    public boolean actualizarNota(Curso curso, Periodo periodo, Nota nota, boolean epsilon) {
        Map<String,CursoRegistrado> Mcr = infoPeriodos.get(periodo);
        if(Mcr!=null){
            CursoRegistrado cr = Mcr.get(curso.getCodigo());
            if(cr !=null){
                cr.setNota(nota);
                cr.setEpsilon(epsilon);
                return true;
            }
>>>>>>> Stashed changes
        }
        return false;
    }


	public ArrayList<EstadoAgregar> inscripcionCursos(ArrayList<Curso> cursos, Periodo periodo) {
        ArrayList<EstadoAgregar> estado = new ArrayList<>();
        int cmp = periodo.compare(this.periodo);
        if( cmp == -1) { // falta revisar otras cosas
            estado.add(new EstadoAgregar(5,periodo));
            return estado;
        }
        if( cmp> 1) {
            if(!(periodo.getAnio() == this.periodo.getAnio()) || !(periodo.getSemestre() == this.periodo.getSemestre())) {
                cursosInscritos = new Hashtable<>();
            }
        }
        agregarPeriodo(periodo);
        for (Curso c: cursos) {
            int val = agregarCursoxPeriodo(c, EstadoCurso.Inscrito,cursos, periodo);
            if(val != 1){
                estado.add(new EstadoAgregar(val,periodo,c));
            }
            cursosInscritos.put(c.getCodigo(),c);
        }
        return estado;

    }


<<<<<<< Updated upstream
=======

    public boolean actualizarNota(Curso curso, Periodo periodo, Nota nota, boolean epsilon) {
        Map<String,CursoRegistrado> Mcr = infoPeriodos.get(periodo);
        if(Mcr!=null){
            CursoRegistrado cr = Mcr.get(curso.getCodigo());
            if(cr !=null){
                cr.setNota(nota);
                cr.setEpsilon(epsilon);
                return true;
            }
        }
        return false;
    }


	public ArrayList<EstadoAgregar> inscripcionCursos(ArrayList<Curso> cursos, Periodo periodo) {
        ArrayList<EstadoAgregar> estado = new ArrayList<>();
        int cmp = periodo.compare(this.periodo);
        if( cmp == -1) { // falta revisar otras cosas
            estado.add(new EstadoAgregar(5,periodo));
            return estado;
        }
        if( cmp> 1) {
            if(!(periodo.getAnio() == this.periodo.getAnio()) || !(periodo.getSemestre() == this.periodo.getSemestre())) {
                cursosInscritos = new Hashtable<>();
            }
        }
        agregarPeriodo(periodo);
        for (Curso c: cursos) {
            int val = agregarCursoxPeriodo(c, EstadoCurso.Inscrito,cursos, periodo);
            if(val != 1){
                estado.add(new EstadoAgregar(val,periodo,c));
            }
            cursosInscritos.put(c.getCodigo(),c);
        }
        return estado;

    }


=======
>>>>>>> Stashed changes
    public ArrayList<EstadoAgregar> agregarCursos(ArrayList<Curso> cursos, ArrayList<Nota> notas, ArrayList<Boolean> epsilon,
                                                  ArrayList<Periodo> periodos) {
        ArrayList<EstadoAgregar> estado = new ArrayList<>();
        Map<Periodo, Map<String, intCurso>> cursosPeriodos = new Hashtable<>();
        Map<Curso, Integer> infoCursos = new Hashtable<>();
        ArrayList<Periodo> Lperiodos = new ArrayList<>();
        formatoAgregar(cursos,periodos, cursosPeriodos,Lperiodos, estado);
        for(Periodo p: Lperiodos){
            ArrayList<Curso> cursosP = cursosPeriodos.get(p).values();
            if(periodo.compare(p) == 1) {
                agregarPeriodo(p);
                for (Curso c : cursosP) {
                    int indice = infoCursos.get(c);
                    int val = agregarCursoxPeriodo(c, notas.get(indice), p, epsilon.get(indice), cursosP);
                    if (val != 1) {
                        estado.add(new EstadoAgregar(val, p, c));
                    }
                }
            }else{
                estado.add(new EstadoAgregar(5,p));
                return estado;
            }
        }
        return estado;
    }
<<<<<<< Updated upstream
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
>>>>>>> Stashed changes


    public int agregarCursoxPeriodo(Curso curso, Nota nota, Periodo periodo, boolean epsilon, ArrayList<Curso> cursosP){
        //auxiliar para agregar un conjunto de cursos
        CursoRegistrado cr = cursosRegistrados.get(curso.getCodigo());
        if(cr != null){
<<<<<<< Updated upstream
<<<<<<< HEAD
            if(cr.getNota().notaCual() == calCual.pendiente){
                cr.setNota(nota);
                cr.setEpsilon(epsilon);
                return 1;
=======
            Periodo pCr = cr.getPeriodo();
            if(periodo.compare(pCr)== 0){
                if(cr.getNota().notaCual() == calCual.pendiente){
                    cr.setNota(nota);
                    cr.setEpsilon(epsilon);
                    return 1;
                }
                return 2;
            }
            if(periodo.compare(pCr) == -1){
                if(!nota.aprobo()) {
                    int val = revisarRestriciones(curso,cursosP,periodo);
                    if(val == 1){
                        cr = new CursoRegistrado(curso, periodo, nota, epsilon);
                        modificarHistoria(cr, periodo);
                        return 1;
                    }
                    return val;
                }
                return -3;     //incosistencia
            }
            if(!cr.getNota().aprobo()){
                return auxCursoXPeriodo(curso,nota,periodo,epsilon,cursosP);
>>>>>>> Stashed changes
            }
            return 2;
        }
        return auxCursoXPeriodo(curso,nota,periodo,epsilon,cursosP);
    }

    public int auxCursoXPeriodo(Curso curso, Nota nota, Periodo periodo, boolean epsilon, ArrayList<Curso> cursosP){
        int val = revisarRestriciones(curso,cursosP,periodo);
        if(val == 1){
            CursoRegistrado cr = new CursoRegistrado(curso, periodo, nota, epsilon);
            cursosRegistrados.put(curso.getCodigo(),cr);
            modificarHistoria(cr, periodo);
            return 1;
        }
        return val;
    }




    @Override
    public CursoRegistrado getCurReg(String codigo) {
         return cursosRegistrados.get(codigo);
    }

<<<<<<< Updated upstream
	public int validarRequerimiento(String codigo, String reqN) {
=======
            Periodo pCr = cr.getPeriodo();
            if(periodo.compare(pCr)== 0){
                if(cr.getNota().notaCual() == calCual.pendiente){
                    cr.setNota(nota);
                    cr.setEpsilon(epsilon);
                    return 1;
                }
                return 2;
            }
            if(periodo.compare(pCr) == -1){
                if(!nota.aprobo()) {
                    int val = revisarRestriciones(curso,cursosP,periodo);
                    if(val == 1){
                        cr = new CursoRegistrado(curso, periodo, nota, epsilon);
                        modificarHistoria(cr, periodo);
                        return 1;
                    }
                    return val;
                }
                return -3;     //incosistencia
            }
            if(!cr.getNota().aprobo()){
                return auxCursoXPeriodo(curso,nota,periodo,epsilon,cursosP);
            }
            return 2;
        }
        return auxCursoXPeriodo(curso,nota,periodo,epsilon,cursosP);
    }

    public int auxCursoXPeriodo(Curso curso, Nota nota, Periodo periodo, boolean epsilon, ArrayList<Curso> cursosP){
        int val = revisarRestriciones(curso,cursosP,periodo);
        if(val == 1){
            CursoRegistrado cr = new CursoRegistrado(curso, periodo, nota, epsilon);
            cursosRegistrados.put(curso.getCodigo(),cr);
            modificarHistoria(cr, periodo);
            return 1;
        }
        return val;
    }




    @Override
    public CursoRegistrado getCurReg(String codigo) {
         return cursosRegistrados.get(codigo);
    }

    public int validarRequerimiento(String codigo, String reqN) {
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
    public int validarRequerimiento(String codigo, String reqN) {
>>>>>>> Stashed changes
        if (!cursosRegistrados.get(codigo).getNota().aprobo()) {
            return -2;
        }
        return  super.validarRequerimiento(codigo,reqN);
    }

    @Override
    public int revisarRestriciones(Curso curso, ArrayList<Curso> cursosP, Periodo periodo) {
        ArrayList<Restriccion> restriccions = curso.getRestricciones();
        for(Restriccion rst: restriccions){
            if(!rst.cumple(this, cursosP, periodo)){
                return 0;
            }
        }
        return 1;
    }

<<<<<<< Updated upstream
<<<<<<< HEAD
=======
=======
>>>>>>> Stashed changes
/*
    @Override
    public int revisarRestriciones(Curso curso, ArrayList<Curso> cursosP) {
        ArrayList<Restriccion> restriccions = curso.getRestricciones();
        for(Restriccion rst: restriccions){
            if(!rst.cumple(this, cursosP)){
                return 0;
            }
        }
        return 1;
    }
*/

<<<<<<< Updated upstream
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
>>>>>>> Stashed changes

    public double calcularPromedioSemestre(String periodoS) {
		ArrayList<Periodo> periodos = periodosMap.get(periodoS);
		int creditosSemestre = 0;
		double puntosSemestre = 0;
		for (Periodo periodo:
			 periodos) {
<<<<<<< Updated upstream
<<<<<<< HEAD
			Map<String,Curso> cursRegP = infoPeriodos.get(periodo);
			for (Curso c: cursRegP.values()) {
                CursoRegistrado cr = cursosRegistrados.get(c.getCodigo());
                if (cursosRegistrados.get(c.getCodigo()).isNumerica()) {
					int creditosCurso = c.getCreditos();
=======
=======
>>>>>>> Stashed changes
			Map<String, CursoRegistrado> cursRegP = infoPeriodos.get(periodo);
			for (CursoRegistrado cr: cursRegP.values()) {
                if (cr.isNumerica()) {
					int creditosCurso = cr.getCurso().getCreditos();
<<<<<<< Updated upstream
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
>>>>>>> Stashed changes
					creditosSemestre += creditosCurso;
					puntosSemestre += creditosCurso * cr.getNota().notaNum();
				}
			}
		}
		if(creditosSemestre >0){
			return puntosSemestre/creditosSemestre;
		}
<<<<<<< Updated upstream
<<<<<<< HEAD

=======
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
>>>>>>> Stashed changes
		return 0;
	}

	public double calcularPromedioAcademico() {
<<<<<<< Updated upstream
<<<<<<< HEAD
		int creditosSemestre = 0;
		double puntosSemestre = 0;
		for (CursoRegistrado cr: cursosRegistrados.values()) {
			if (cr.isNumerica()) {
				int creditosCurso = cr.getCurso().getCreditos();
				creditosSemestre += creditosCurso;
				puntosSemestre += creditosCurso * cr.getNota().notaNum();
			}
		}
=======
=======
>>>>>>> Stashed changes
        double puntosSemestre = 0;
        double creditosSemestre = 0;
        for(Map<String, CursoRegistrado> mp: infoPeriodos.values()) {
            for (CursoRegistrado cr : mp.values()) {
                if (cr.isNumerica()) {
                    int creditosCurso = cr.getCurso().getCreditos();
                    creditosSemestre += creditosCurso;
                    puntosSemestre += creditosCurso * cr.getNota().notaNum();
                }
            }
        }
<<<<<<< Updated upstream
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
>>>>>>> Stashed changes
		if(creditosSemestre >0){
			return puntosSemestre/creditosSemestre;
		}

		return 0;
	}

    public int cambiarRequerimiento(String codigoCurso, String req1N, String req2N){
        RequerimientoRegistrado reqR1 =  reqsRegistrados.get(req1N);
<<<<<<< Updated upstream
<<<<<<< HEAD
        Requerimiento req2 = pensum.getRequerimientos().get(req2N);
=======
>>>>>>> Stashed changes
        Curso curso = cursosRegistrados.get(codigoCurso).getCurso();
        if(req1N == null  || curso == null){
            return -1;
        }
        int val = validarRequerimiento(codigoCurso, req2N);
        if(val != 1){
            return val;
        }
<<<<<<< Updated upstream
        return val;
=======
        Curso curso = cursosRegistrados.get(codigoCurso).getCurso();
        if(req1N == null  || curso == null){
            return -1;
        }
        int val = validarRequerimiento(codigoCurso, req2N);
        if(val != 1){
            return val;
        }
=======
>>>>>>> Stashed changes
        if(reqR1.quitarCurso(codigoCurso)){
            return 1;
        }
        return -2;
<<<<<<< Updated upstream
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
>>>>>>> Stashed changes
    }
}


