package uniandes.dpoo.proyecto1.modelo;

import jdk.jfr.Period;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


public class HistoriaAcademica implements Serializable {
	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = -491840464239633611L;
	private Pensum pensum;
	private float creditos = 0;
	private int semestre = 0;
	private Periodo periodo;

	private Map<String, CursoVisto> cursosvistos;
	private Map<Periodo, ArrayList<CursoVisto>> cursosvistosXperiodo;
	private Map<String, Integer> cursosVreq;
	private Map<String, RegistroRequerimiento> requerimientosRegistro;
	private Map<Nivel, Map<String, RegistroRequerimiento>> reqOblXnivel;


	public HistoriaAcademica(Pensum pensum, Periodo periodo) {
		this.pensum = pensum;
		this.periodo = periodo;
		this.cursosvistos = Collections.emptyMap();
		this.requerimientosRegistro = Collections.emptyMap();
		this.cursosvistosXperiodo = Collections.emptyMap();
		this.cursosVreq = Collections.emptyMap();
		this.reqOblXnivel = Collections.emptyMap();

		for (Requerimiento req : pensum.getRequerimientos()) {
			RegistroRequerimiento rReq = new RegistroRequerimiento(req);
			requerimientosRegistro.put(req.getNombre(), rReq);
			reqOblXnivel.putIfAbsent(req.getNivel(), Collections.emptyMap());
			reqOblXnivel.get(req.getNivel()).put(req.getNombre(), rReq);
		}

	}

	public float getCreditos() {
		return creditos;
	}
	public int getSemestre() {
		return semestre;
	}
	public Pensum getPensum() {
		return pensum;
	}

	public Map<Nivel, Map<String, RegistroRequerimiento>> getReqOblXnivel() {
		return reqOblXnivel;
	}
	public Map<String, Integer> getCursosVreq() {
		return cursosVreq;
	}
	public Map<String, RegistroRequerimiento> getRequerimientosRegistro() {
		return requerimientosRegistro;
	}
	public Map<String, CursoVisto> getCursosvistos() {
		return cursosvistos;
	}

	public Map<Periodo, ArrayList<CursoVisto>> getCursosvistosXperiodo(){
		return cursosvistosXperiodo;
	}

	public void agregarCurso(Curso curso, Periodo periodo, float nota){
		CursoVisto registro = new CursoVisto(curso, periodo, nota);
		cursosvistosXperiodo.computeIfAbsent(periodo, k -> new ArrayList<>());
		cursosvistosXperiodo.get(periodo).add(registro);
		cursosvistos.put(curso.getCodigo(),registro);
		creditos += curso.getCreditos();
	}

	public int validarRequerimiento(String codigoCurso, String reqS){
		CursoVisto cursoV = cursosvistos.get(codigoCurso);
		RegistroRequerimiento reqRegistro = requerimientosRegistro.get(reqS);
		if (cursoV == null || reqRegistro == null){
			return -1;
		}
		if(reqRegistro.agregarCurso(cursoV, cursosVreq)){
			return 1;
		}
		return 0;

	}


	public boolean cambiarReq(String codigoCurso, String reqInicial, String reqCambio){
		CursoVisto cursoV = cursosvistos.get(codigoCurso);
		if (validarRequerimiento(codigoCurso, reqCambio) == 1){
			RegistroRequerimiento reqRegistro = requerimientosRegistro.get(reqInicial);
			reqRegistro.quitarCurso(cursoV);
			return true;
		}
		return false;
	}

	public double calcularPromedioPeriodo(Periodo periodo){
		int suma = 0;
		int cnt = 0;
		for(CursoVisto cv: cursosvistosXperiodo.get(periodo)){
			suma += cv.getNota()*cv.getCurso().getCreditos();
			cnt += cv.getCurso().getCreditos();
		}
		return (double) suma/cnt;
	}

	public double calcularPromedioAcademico(){
		int suma = 0;
		int cnt = 0;
		for(CursoVisto cv: cursosvistos.values()) {
			suma += cv.getNota() * cv.getCurso().getCreditos();
			cnt += cv.getCurso().getCreditos();
		}
		return (double) suma/cnt;
	}
}
