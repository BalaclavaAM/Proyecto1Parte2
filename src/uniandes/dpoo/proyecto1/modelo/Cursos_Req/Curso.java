package uniandes.dpoo.proyecto1.modelo.Cursos_Req;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Restricciones.Restriccion;

import java.io.Serializable;
import java.util.ArrayList;

public class Curso implements Serializable {
    /**
	 * Suave
	 */
	private static final long serialVersionUID = -1708691240589592764L;
	private final boolean notanumerica; //meramente informativa o para validar;
	private final String nombre;
	private String codigo;
	private final String descripcion;
	//private final String programa; //puede ser redundante con materia exepto para los cbus
	private String materia; // es lo mismo que aparece en la oferta de curso, se podria utilizar un enum(muy grande)
	//private int semanas;
	private final int creditos;
	private ArrayList<Restriccion> restricciones;


	public Curso(String nombre, String codigo, String programa, int creditos, int semanas,
				 boolean notanumerica, String descripcion) {
		this.nombre = nombre;
		this.codigo = codigo;
		//this.programa = programa;
		this.creditos = creditos;
		this.notanumerica = notanumerica;
		//this.semanas = semanas; //no usado, serviria para corregir reqisitos cursos de 8 semanas con perrequistos cursos de 16 (poco comun)
		this.descripcion = descripcion;
	}



	public String getNombre() {
		return nombre;
	}
	public String getCodigo() {
		return codigo;
	}
	public String getMateria(){ return materia; }

	public String getDescripcion() {
		return descripcion;
	}

	public ArrayList<Restriccion> getRestricciones() {
		return restricciones;
	}

	public void addRestriccion(String tipo, String[] opciones){
		this.codigo = codigo;
		
	}

	public boolean isNotanumerica() {
		return notanumerica;
	}



	public int getCreditos() {
		return creditos;
	}

}
