package uniandes.dpoo.proyecto1.modelo;

import java.util.ArrayList;

public class Requerimiento {

	private String nombre;
	private ArrayList<Curso> cursos;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	public ArrayList<Curso> getCursos() {
		return cursos;
	}
	public void setCursos(ArrayList<Curso> cursos) {
		this.cursos = cursos;
	}
}
