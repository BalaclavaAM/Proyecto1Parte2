package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Restricciones.Restriccion;
import uniandes.dpoo.proyecto1.modelo.Registro.*;
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.calCual;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


public class HistoriaAcademica extends MallaCursos implements Serializable {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -491840464239633611L;
    private Map<String, Curso> cursosInscritos;
    public Nota notaP = new NotaCual(calCual.pendiente);



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

    public Map<String, CursoRegistrado> getCursosRegistrados() {
        return cursosRegistrados;
    }

    public boolean setPeriodo(Periodo periodo) {
		return false;
	}

    public Map<String, Curso> getCursosInscritos() {
        return cursosInscritos;
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


    public int agregarCursoxPeriodo(Curso curso, Nota nota, Periodo periodo, boolean epsilon, ArrayList<Curso> cursosP){
        //auxiliar para agregar un conjunto de cursos
        CursoRegistrado cr = cursosRegistrados.get(curso.getCodigo());
        if(cr != null){
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


    public ArrayList<EstadoAgregar> agregarCursos(ArrayList<Curso> cursos, ArrayList<Nota> notas, ArrayList<Boolean> epsilon,
                                       ArrayList<Periodo> periodos) {
        ArrayList<EstadoAgregar> estado = new ArrayList<>();
        Map<Periodo, ArrayList<Curso>> cursosPeriodos = new Hashtable<>();
        Map<Curso, Integer> infoCursos = new Hashtable<>();
        ArrayList<Periodo> Lperiodos = new ArrayList<>();
        formatoAgregar(cursos,periodos, cursosPeriodos,infoCursos,Lperiodos, estado);
        for(Periodo p: Lperiodos){
            ArrayList<Curso> cursosP = cursosPeriodos.get(p);
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


    @Override
    public CursoRegistrado getCurReg(String codigo) {
         return cursosRegistrados.get(codigo);
    }

    public int validarRequerimiento(String codigo, String reqN) {
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


    public double calcularPromedioSemestre(String periodoS) {
		ArrayList<Periodo> periodos = periodosMap.get(periodoS);
		int creditosSemestre = 0;
		double puntosSemestre = 0;
		for (Periodo periodo:
			 periodos) {
			Map<String, CursoRegistrado> cursRegP = infoPeriodos.get(periodo);
			for (CursoRegistrado cr: cursRegP.values()) {
                if (cr.isNumerica()) {
					int creditosCurso = cr.getCurso().getCreditos();
					creditosSemestre += creditosCurso;
					puntosSemestre += creditosCurso * cr.getNota().notaNum();
				}
			}
		}
		if(creditosSemestre >0){
			return puntosSemestre/creditosSemestre;
		}
		return 0;
	}

	public double calcularPromedioAcademico() {
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
		if(creditosSemestre >0){
			return puntosSemestre/creditosSemestre;
		}

		return 0;
	}

    public int cambiarRequerimiento(String codigoCurso, String req1N, String req2N){
        RequerimientoRegistrado reqR1 =  reqsRegistrados.get(req1N);
        Curso curso = cursosRegistrados.get(codigoCurso).getCurso();
        if(req1N == null  || curso == null){
            return -1;
        }
        int val = validarRequerimiento(codigoCurso, req2N);
        if(val != 1){
            return val;
        }
        if(reqR1.quitarCurso(codigoCurso)){
            return 1;
        }
        return -2;
    }
}


