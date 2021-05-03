package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Restricciones.Restriccion;
import uniandes.dpoo.proyecto1.modelo.Registro.*;
import uniandes.dpoo.proyecto1.modelo.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Nota.calCual;

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

    public boolean setPeriodo(Periodo periodo) {
		return false;
	}

    public Map<String, Curso> getCursosInscritos() {
        return cursosInscritos;
    }


    public boolean actualizarNota(Curso curso, Periodo periodo, Nota nota, boolean epsilon) {
        Map<String,CursoRegistrado> Mcr = infoPeriodos.get(periodo);
        if(Mcr!=null){
            CursoRegistrado cr = Mcr.get(curso.getCodigo());
            if(cr !=null){
                cr.setNota(nota);
                cr.setEpsilon(epsilon);
                String codigo = cr.getCurso().getCodigo();
                String reqAsociado = pensum.getCursosValidacionAuto().get(codigo);
                if (reqAsociado != null) {
                    validarRequerimiento(codigo, reqAsociado);
                    }
                }
                return true;
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
            cursos.remove(0);
            int val = agregarCursoxPeriodo(c,notaP,EstadoCurso.Inscrito,false,cursos, periodo);
            if(val != 1){
                estado.add(new EstadoAgregar(val,periodo,c));
            }
            cursosInscritos.put(c.getCodigo(),c);
        }
        return estado;
    }

    @Override
    public CursoRegistrado getCurReg(String codigo) {
         return cursosRegistrados.get(codigo);
    }
    
    public int validarRequerimiento(String codigo, String reqN) {
        int val = super.validarRequerimiento(codigo,reqN);
        if (val != 1) {
            return val;
        }
        if(cursosRegistrados.get(codigo).getNota().aprobo()) {
            return 1;
        }
        return -2;
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

    public float getCreditos() {
        return creditos;
    }

    @Override
    public boolean dentroPeriodo(Periodo p) {
        return periodo.compare(p) == 1;
    }

    @Override
    public Periodo getPHis() {
        return periodo;
    }

    @Override
    public Nota getauxNota(int i, ArrayList<Nota> notas) {
        return notas.get(i);
    }

    @Override
    public boolean getauxEps(int i, ArrayList<Boolean> epsilons) {
        return epsilons.get(i);
    }

    @Override
    public EstadoCurso getauxEsC(Nota nota) {
        if(nota.notaCual() == calCual.pendiente){
            return EstadoCurso.Pendiente;
        }
        return EstadoCurso.Finalizado;
    }

}

