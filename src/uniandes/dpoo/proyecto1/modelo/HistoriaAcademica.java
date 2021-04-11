package uniandes.dpoo.proyecto1.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class HistoriaAcademica {
	private Pensum pensum;
	private float creditos;
	private int semestre;
	private float promedio;
	private Map<String, ArrayList<CursoVisto>> cursosvistos;

	public HistoriaAcademica(ArrayList<CursoVisto> cursosvistos) {
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
	public Map<String, ArrayList<CursoVisto>> getCursosvistos() {
		return cursosvistos;
	}
	public void setCursosvistos(Map<String, ArrayList<CursoVisto>> cursosvistos) {
		this.cursosvistos = cursosvistos;
	}
}
