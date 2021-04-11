package uniandes.dpoo.proyecto1.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Carrera implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7300727299950071526L;
	private String nombre;
	private String diminutivo;
	private ArrayList<Pensum> Pensum;
	
	public Carrera(String nombre, String diminutivo, ArrayList<uniandes.dpoo.proyecto1.modelo.Pensum> pensum) {
		super();
		this.nombre = nombre;
		this.diminutivo = diminutivo;
		Pensum = pensum;
	}
	
	public ArrayList<Pensum> getPensum() {
		return Pensum;
	}
	public void setPensum(ArrayList<Pensum> pensum) {
		Pensum = pensum;
	}
	
	
	public String getDiminutivo() {
		return diminutivo;
	}
	public void setDiminutivo(String diminutivo) {
		this.diminutivo = diminutivo;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
