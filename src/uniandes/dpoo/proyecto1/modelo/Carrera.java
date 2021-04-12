package uniandes.dpoo.proyecto1.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Carrera implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7300727299950071526L;
	private String nombre;
	private String diminutivo;
	private Map<String, Estudiante> estudiantes;

	public Carrera(String nombre, String diminutivo, Map<String, Estudiante> estudiantes){
		super();
		this.nombre = nombre;
		this.diminutivo = diminutivo;
		this.estudiantes = estudiantes;
	}
	
	public String getDiminutivo() {
		return diminutivo;
	}
	public String getNombre() {
		return nombre;
	}
}
