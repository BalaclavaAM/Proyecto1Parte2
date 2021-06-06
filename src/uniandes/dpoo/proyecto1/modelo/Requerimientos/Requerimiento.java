package uniandes.dpoo.proyecto1.modelo.Requerimientos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.ReqTipologia;

import java.io.Serializable;

public abstract class Requerimiento implements Serializable {
	/**
	 *
	 */
	protected static final long serialVersionUID = -7762431642880730439L;
	protected String nombre;
	protected Nivel nivel;
	protected String tipo;
	protected String [] mains;
	protected int semestresugerido;
	protected ReqTipologia tipologia;
	protected int creditos;
	protected int items;

	public String getNombre() {
		return nombre;
	}

	public Requerimiento(String nombre, Nivel nivel, int semestresugerido, ReqTipologia tipologia, int creditos, String[] mains){
		this.nombre = nombre;
		this.nivel = nivel;
		this.semestresugerido = semestresugerido;
		this.tipologia = tipologia;
		this.creditos = creditos;
		this.mains = mains;
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

	public ReqTipologia getTipologia() {
		return tipologia;
	}

	public int getCreditos() {
		return creditos;
	}

	public int compareS(Requerimiento req){
		return Integer.compare(semestresugerido,req.semestresugerido);
	}

	public abstract double  validar(Curso curso); //devuelve la cantidad de items que cumple el curso

	public String[] getMains() {
		return mains;
	}
}
