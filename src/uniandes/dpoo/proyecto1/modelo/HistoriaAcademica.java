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
	private Map<Object, Curso> requerimientosCumplidos;
	private Map<String, Integer> cursosVreq;



	public HistoriaAcademica() {
		super();
		this.creditos = 0;
		this.semestre = 0;
		this.promedio = 0;
		this.cursosvistos = Collections.emptyMap();;
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
	public void setPensum(Pensum pensum) {
		this.pensum = pensum;
	}
	public Map<Integer, ArrayList<CursoVisto>> getCursosvistos() {
		return cursosvistosXsemestre;
	}

	public void agregarCurso(Curso curso,Periodo periodo, float nota, int semestre, Requerimiento req){
		CursoVisto registro = new CursoVisto(curso, periodo, nota);
		cursosvistosXsemestre.computeIfAbsent(semestre, k -> new ArrayList<>());
		cursosvistosXsemestre.get(semestre).add(registro);
		cursosvistos.put(curso.getCodigo(),registro);
		
	}
	
	public int agregarRequerimiento(String nombreCurso, RequerimientoCurso req){
		CursoVisto cursoV = cursosvistos.get(nombreCurso);
		if (cursoV == null){
			return -1;
		}
		if (cursoV.getNota()> 3 & !req.validar(cursoV.getCurso())){
			return 0;
		}
		if (requerimientosCumplidos.get(req) != null){
			return 2;
		}
		requerimientosCumplidos.put(req,cursoV.getCurso());
		cursosVreq.put(cursoV.getCurso().getCodigo(), 1);
		return 1;
	}
	public int validarRequerimientoBloque(Curso[] cursosOp, RequerimientoBloque req) {
		for (Curso curso : cursosOp) {
			if (cursosVreq.containsKey(curso.getCodigo())) {
				return -2;
			}
			if (!cursosvistos.containsKey(curso.getCodigo())) {
				return -1;
			}
		}
		if (!req.validar(cursosOp)) {
			return 0;
		}
		return 1;
	}
	
	
}
