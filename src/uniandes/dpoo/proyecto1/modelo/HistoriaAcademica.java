package uniandes.dpoo.proyecto1.modelo;

import java.util.ArrayList;

public class HistoriaAcademica {
	private float creditos;
	private int semestre;
	private float promedio;
	private ArrayList<CursoVisto> cursosvistos;
	
	public HistoriaAcademica(float creditos, int semestre, float promedio, ArrayList<CursoVisto> cursosvistos) {
		super();
		this.creditos = creditos;
		this.semestre = semestre;
		this.promedio = promedio;
		this.cursosvistos = cursosvistos;
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
	public ArrayList<CursoVisto> getCursosvistos() {
		return cursosvistos;
	}
	public void setCursosvistos(ArrayList<CursoVisto> cursosvistos) {
		this.cursosvistos = cursosvistos;
	}
	
}
