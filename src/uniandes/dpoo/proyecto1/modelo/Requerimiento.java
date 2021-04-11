package uniandes.dpoo.proyecto1.modelo;
import java.io.Serializable;
import java.util.ArrayList;

public class Requerimiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7762431642880730439L;
	private String nombre;
	private int semestresugerido;
	private ArrayList<Curso> cursos;

	public Requerimiento(String nombre, int semestresug,ArrayList<Curso> cursos) {
		super();
		this.nombre = nombre;
		this.cursos = cursos;
		this.setSemestresugerido(semestresug);
	}

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

	public int getSemestresugerido() {
		return semestresugerido;
	}

	public void setSemestresugerido(int semestresugerido) {
		this.semestresugerido = semestresugerido;
	}
}
