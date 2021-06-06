package uniandes.dpoo.proyecto1.modelo.Cursos_Req;

import uniandes.dpoo.proyecto1.modelo.Restricciones.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Curso implements Serializable {
	private static final long serialVersionUID = -1708691240589592764L;
	private final String nombre;
	private final String codigo;
	private final boolean NotaNumerica;
	private final String materia; // es lo mismo que aparece en la oferta de curso, se podria utilizar un enum(muy grande)
	private final int creditos;
	private final ArrayList<Correquisito> correquisitos;
	private final ArrayList<Prerrequisito> prerrequisitos;
	private final ArrayList<RestriccionReq> restriccionesReqs;
	private final RestriccionNivel RestriccionNivel;
	private final String descripcion;




	public Curso(String nombre, String codigo, String programa, int creditos, boolean notaNumerica,
				 ArrayList<Prerrequisito> prerrequisitos, ArrayList<Correquisito> correquisitos,
				 ArrayList<RestriccionReq> restriccionesReqs, RestriccionNivel restriccionNivel, String descripcion)
	{
		this.nombre = nombre;
		this.codigo = codigo;
		this.creditos = creditos;
		//meramente informativa o para validar;
		//this.semanas = semanas; //no usado, serviria para corregir reqisitos cursos de 8 semanas con perrequistos cursos de 16 (poco comun)
		NotaNumerica = notaNumerica;
		this.correquisitos = correquisitos;
		this.prerrequisitos = prerrequisitos;
		this.restriccionesReqs = restriccionesReqs;
		RestriccionNivel = restriccionNivel;
		this.materia = programa;
		this.descripcion = descripcion;
	}




	public String getNombre() {
		return nombre;
	}
	public String getCodigo() {
		return codigo;
	}
	public String getMateria(){ return materia; }
	public String getDescripcion(){
		return descripcion;
	}

	public ArrayList<Correquisito> getCorrequisitos() {
		return correquisitos;
	}

	public ArrayList<Prerrequisito> getPrerrequisitos() {
		return prerrequisitos;
	}

	public ArrayList<RestriccionReq> getRestriccionesReqs() {
		return restriccionesReqs;
	}

	public RestriccionNivel getRestriccionNivel() {
		return RestriccionNivel;
	}

	public int getCreditos() {
		return creditos;
	}

}
