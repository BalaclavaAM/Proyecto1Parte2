package uniandes.dpoo.proyecto1.modelo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public abstract class Requerimiento implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7762431642880730439L;
	private String nombre;
	private Nivel nivel;
	private int semestresugerido;
	private String tipologia;
	private ArrayList<Curso> cursos;
	private int creditos;

	public Requerimiento(String nombre, Nivel nivel, int semestresug, String tipologia, ArrayList<Curso> cursos, int creditos) {
		this.nombre = nombre;
		this.nivel = nivel;
		this.tipologia = tipologia;
		this.cursos = cursos;
		this.semestresugerido = semestresug;
		this.creditos = creditos;
	}

	public String getNombre() {
		return nombre;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public ArrayList<Curso> getCursos() {
		return cursos;
	}

	public abstract  boolean validar(Curso cursoOp, Map<String, Integer> cursosVreq);
	public abstract  boolean cumplio(Curso cursoOp, RegistroRequerimiento regisReq);

	public String getTipologia() {
		return tipologia;
	}

	public int getCreditos() {
		return creditos;
	}
	
	public int getSemestreSugerido() {
		return semestresugerido;
	}
}
