package uniandes.dpoo.proyecto1.modelo;
import java.util.ArrayList;

public abstract class Requerimiento {

	private String nombre;
	private int semestresugerido;
	private ArrayList<Curso> cursos;
	private int creditos;

	public Requerimiento(String nombre, int semestresug,ArrayList<Curso> cursos) {
		super();
		this.nombre = nombre;
		this.cursos = cursos;
		this.semestresugerido = semestresug;
	}

	public String getNombre() {
		return nombre;
	}
	public void setCursos(ArrayList<Curso> cursos){
		this.cursos = cursos;
	}

	
	public ArrayList<Curso> getCursos() {
		return cursos;
	}

	public abstract boolean validar(Curso cursoOp);
	
}
