package uniandes.dpoo.proyecto1.modelo;

import java.util.ArrayList;

public class Pensum {
	
	private String Nombre;
	private ArrayList<Requerimiento> Requerimientos;
	
	
	public ArrayList<Requerimiento> getRequerimientos() {
		return Requerimientos;
	}
	
	
	public void setRequerimientos(ArrayList<Requerimiento> requerimientos) {
		Requerimientos = requerimientos;
	}
	
	
	public String getNombre() {
		return Nombre;
	}
	
	
	public void setNombre(String nombre) {
		Nombre = nombre;
	}

}
