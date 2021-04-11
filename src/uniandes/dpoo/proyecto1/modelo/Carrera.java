package uniandes.dpoo.proyecto1.modelo;

import java.util.ArrayList;

public class Carrera {
	private String nombre;
	private String diminutivo;
	private ArrayList<Pensum> Pensum;
	
	
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
