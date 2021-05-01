package uniandes.dpoo.proyecto1.modelo.Cursos_Req.Requerimientos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;

import java.io.Serializable;

public abstract class Requerimiento implements Serializable {
	/**
	 *
	 */
	protected static final long serialVersionUID = -7762431642880730439L;
	protected String nombre;
	protected Nivel nivel;
	protected String tipo;
	protected int semestresugerido;
	protected String tipologia;
	protected int creditos;
	protected int items;
	protected String materia;

	public String getNombre() {
		return nombre;
	}
	public Requerimiento(String nombre, Nivel nivel, int semestresugerido, String tipologia, int creditos, String materia){
		this.nombre = nombre;

		this.nivel = nivel;
		this.semestresugerido = semestresugerido;
		this.tipologia = tipologia;
		this.creditos = creditos;
	}


	public Nivel getNivel() {
		return nivel;
	}

	public String getTipo() {
		return tipo;
	}

	public int getItems(){return items;}

	public int getSemestresugerido() {
		return semestresugerido;
	}

	public String getTipologia() {
		return tipologia;
	}

	public int getCreditos() {
		return creditos;
	}

	public abstract int  validar(Curso curso); //devuelve la cantidad de items que cumple el curso
}
