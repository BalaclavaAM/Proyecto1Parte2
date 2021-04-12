package uniandes.dpoo.proyecto1.modelo;

import java.io.Serializable;

public class Curso implements Serializable {
    /**
	 * Suave
	 */
	private static final long serialVersionUID = -1708691240589592764L;
	private final boolean notanumerica;
	private String nombre;
	private String codigo;
	private String programa;
	private String duracion;
	private String tipologia;
	private int creditos;
	private Restriccion[] restricciones;
			




	public Curso(String nombre, String codigo, String programa, int creditos, String duracion, String tipologia, boolean notanumerica) {
		this.nombre = nombre;
		this.codigo = codigo;
		this.programa = programa;
		this.duracion = duracion;
		this.tipologia = tipologia;
		this.creditos = creditos;
		this.notanumerica = notanumerica;

	}

	public String getNombre() {
		return nombre;
	}
	public String getCodigo() {
		return codigo;
	}
	public String getPrograma() {
		return programa;
	}
	public String getDuracion() {
		return duracion;
	}
	public String getTipologia() {
		return tipologia;
	}

	public void addRestriccion(String tipo, String[] opciones){
		this.codigo = codigo;
		
	}

	public boolean isNotanumerica() {
		return notanumerica;
	}

	public Restriccion[] getRestricciones() {
		return restricciones;
	}
	public int getCreditos() {
		return creditos;
	}

	public void setRestricciones(Restriccion[] restricciones) {
		this.restricciones = restricciones;
	}
}
