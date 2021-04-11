package uniandes.dpoo.proyecto1.modelo;

import java.io.Serializable;

public class Curso implements Serializable {
    /**
	 * Suave
	 */
	private static final long serialVersionUID = -1708691240589592764L;
	private String nombre;
	private String codigo;
	private String programa;
	private String duracion;
	private String tipologia;



	public Curso(String nombre, String codigo, String programa, String duracion, String tipologia) {
		this.nombre = nombre;
		this.codigo = codigo;
		this.programa = programa;
		this.duracion = duracion;
		this.tipologia = tipologia;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getPrograma() {
		return programa;
	}
	public void setPrograma(String programa) {
		this.programa = programa;
	}


	public String getDuracion() {
		return duracion;
	}
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}


	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
}
