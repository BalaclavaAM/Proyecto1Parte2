package uniandes.dpoo.proyecto1.modelo.Cursos_Req;

import uniandes.dpoo.proyecto1.modelo.Restricciones.Correquisito;
import uniandes.dpoo.proyecto1.modelo.Restricciones.Prerrequisito;
import uniandes.dpoo.proyecto1.modelo.Restricciones.RestriccionNivel;
import uniandes.dpoo.proyecto1.modelo.Restricciones.RestriccionReq;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Curso implements Serializable {
	@Serial
	private static final long serialVersionUID = -1708691240589592764L;
	private final String nombre;
	private final String codigo;
	private final boolean completo;
	private final String materia; // es lo mismo que aparece en la oferta de curso, se podria utilizar un enum(muy grande)
	private final double creditos;
	private final ArrayList<Correquisito> correquisitos;
	private final ArrayList<Prerrequisito> prerrequisitos;
	private final ArrayList<RestriccionReq> restriccionesReqs;
	private final RestriccionNivel RestriccionNivel;
	private final String descripcion;




	public Curso(String nombre, String codigo, String programa, double creditos, boolean notaNumerica,
				 ArrayList<Prerrequisito> prerrequisitos, ArrayList<Correquisito> correquisitos,
				 ArrayList<RestriccionReq> restriccionesReqs, RestriccionNivel restriccionNivel, String descripcion)
	{
		this.nombre = nombre;
		this.codigo = codigo;
		this.creditos = creditos;
		//meramente informativa o para validar;
		//this.semanas = semanas; //no usado, serviria para corregir reqisitos cursos de 8 semanas con perrequistos cursos de 16 (poco comun)
		completo = notaNumerica;
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

	public Double getCreditos() {
		return creditos;
	}

	public boolean isCompleto() {
		return completo;
	}
}
