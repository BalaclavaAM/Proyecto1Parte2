package uniandes.dpoo.proyecto1.modelo;

import java.util.*;


public class HistoriaAcademica {
	private Pensum pensum;
	private float creditos;
	private int semestre;
	private float promedio;
	private Map<Integer, ArrayList<CursoVisto>> cursosvistosXsemestre;
	private Map<String, CursoVisto> cursosvistos;
	private Map<Object, Curso> requerimientosCumplidos;



	public HistoriaAcademica() {
		super();
		this.creditos = 0;
		this.semestre = 0;
		this.promedio = 0;
		this.cursosvistos = new Hashtable<>();
		this.cursosvistosXsemestre =  new Hashtable<>();
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

	public void agregarCurso(Curso curso,Periodo periodo, float nota, int semestre){
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
		if (!req.validar(cursoV.getCurso())){
			return 0;
		}
		requerimientosCumplidos.put(req,cursoV.getCurso());
		return 1;
	}

	public int actualizarRequerimiento(){

	}


	public boolean validarRequerimiento(Requerimiento req){

	}


}
