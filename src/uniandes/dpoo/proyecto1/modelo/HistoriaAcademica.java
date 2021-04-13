package uniandes.dpoo.proyecto1.modelo;

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
	private float creditos;
	private int semestre;
	private float promedio;
	private Map<Integer, ArrayList<CursoVisto>> cursosvistosXsemestre;
	private Map<String, CursoVisto> cursosvistos;
	private Map<Requerimiento, RequerimientoHistoria> requerimientosRegistro;
	private Map<String, Integer> cursosVreq;



	public HistoriaAcademica(Pensum pensum) {
		super();
		this.creditos = 0;
		this.semestre = 0;
		this.promedio = 0;
		this.cursosvistos = Collections.emptyMap();;
		this.requerimientosRegistro = Collections.emptyMap();
		this.cursosvistosXsemestre = Collections.emptyMap();
		this.cursosVreq = Collections.emptyMap();
		for( Requerimiento req: pensum.getRequerimientos()){
			requerimientosRegistro.put(req, new RequerimientoHistoria(req));
		}
	}
	public float getCreditos() {
		return creditos;
	}
	public void setCreditos(float creditos) {
		this.creditos = creditos;
	}
	public int getSemestre() {
		return semestre;
	}
	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}
	public float getPromedio() {
		return promedio;
	}
	public void setPromedio(float promedio) {
		this.promedio = promedio;
	}
	public Pensum getPensum() {
		return pensum;
	}

	public Map<Integer, ArrayList<CursoVisto>> getCursosvistos() {
		return cursosvistosXsemestre;
	}



	public void agregarCurso(Curso curso, Periodo periodo, float nota, int semestre){
		CursoVisto registro = new CursoVisto(curso, periodo, nota);
		cursosvistosXsemestre.computeIfAbsent(semestre, k -> new ArrayList<>());
		cursosvistosXsemestre.get(semestre).add(registro);
		cursosvistos.put(curso.getCodigo(),registro);
	}

	public int validarRequerimiento(String codigoCurso, Requerimiento req){
		CursoVisto cursoV = cursosvistos.get(codigoCurso);
		RequerimientoHistoria reqRegistro = requerimientosRegistro.get(req);
		if (cursoV == null || reqRegistro == null){
			return -1;
		}
		if(cursosVreq.containsKey(codigoCurso)){
			return -2;
		}
		if(reqRegistro.agregarCurso(cursoV)){
			cursosVreq.put(cursoV.getCurso().getCodigo(),1);
			return 1;
		}
		return 0;

	}


	public boolean cambiarReq(String codigoCurso, Requerimiento reqInicial, Requerimiento reqCambio){
		CursoVisto cursoV = cursosvistos.get(codigoCurso);
		if (validarRequerimiento(codigoCurso, reqCambio) == 1){
			RequerimientoHistoria reqRegistro = requerimientosRegistro.get(reqInicial);
			reqRegistro.quitarCurso(cursoV);
			return true;
		}
		return false;
	}

	public double calcularPromedioSemestre(int semestre){
		int suma = 0;
		int cnt = 0;
		for(CursoVisto cv: cursosvistosXsemestre.get(semestre)){
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

	public Map<Integer, ArrayList<CursoVisto>> getCursosvistosXsemestre(){
		return cursosvistosXsemestre;
	}
}
