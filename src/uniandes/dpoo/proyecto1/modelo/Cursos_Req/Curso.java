package uniandes.dpoo.proyecto1.modelo.Cursos_Req;

import uniandes.dpoo.proyecto1.modelo.Restricciones.Correquisito;
import uniandes.dpoo.proyecto1.modelo.Restricciones.PreRestriccion;

import java.io.Serializable;
import java.util.ArrayList;

public class Curso implements Serializable {
	private static final long serialVersionUID = -1708691240589592764L;
	private final String nombre;
	private final String codigo;
	private final boolean NotaNumerica;
	private final String materia; // es lo mismo que aparece en la oferta de curso, se podria utilizar un enum(muy grande)
	private final int creditos;
	private final ArrayList<PreRestriccion> restricciones;
	private final ArrayList<Correquisito> correquisitos;


	public Curso(String nombre, String codigo, String programa, int creditos,
				 boolean notaNumerica, ArrayList<PreRestriccion> restricciones, ArrayList<Correquisito> correquisitos)
	{
		this.nombre = nombre;
		this.codigo = codigo;
		this.creditos = creditos;
		//meramente informativa o para validar;
		//this.semanas = semanas; //no usado, serviria para corregir reqisitos cursos de 8 semanas con perrequistos cursos de 16 (poco comun)
		NotaNumerica = notaNumerica;
		this.restricciones = restricciones;
		this.correquisitos = correquisitos;
		this.materia = programa;
	}



	public String getNombre() {
		return nombre;
	}
	public String getCodigo() {
		return codigo;
	}
	public String getMateria(){ return materia; }
	public ArrayList<PreRestriccion> getRestricciones() {
		return restricciones;
	}


	public ArrayList<Correquisito> getCorrequisitos() {
		return correquisitos;
	}

	public int getCreditos() {
		return creditos;
	}

}
