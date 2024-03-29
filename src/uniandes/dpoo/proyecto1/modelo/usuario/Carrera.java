package uniandes.dpoo.proyecto1.modelo.usuario;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Pensum;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public class Carrera implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7300727299950071526L;
	private String nombre;
	private String diminutivo;
	private Map<String, Estudiante> estudiantes;
	private Pensum pensumActual;

	public Carrera(String nombre, String diminutivo) {
		this.nombre = nombre;
		this.diminutivo = diminutivo;
		this.setEstudiantes(Collections.emptyMap());
	}

	public void setPensumActual(Pensum pensumActual) {
		this.pensumActual = pensumActual;
	}

	public Pensum getPensumActual() {
		return pensumActual;
	}

	public String getDiminutivo() {
		return diminutivo;
	}
	public void setDiminutivo(String diminutivo) {
		this.diminutivo = diminutivo;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public Map<String,Estudiante> getEstudiantes() {
		return estudiantes;
	}



	public void setEstudiantes(Map<String,Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}
}
