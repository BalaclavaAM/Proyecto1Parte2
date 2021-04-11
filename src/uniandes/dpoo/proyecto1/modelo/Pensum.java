package uniandes.dpoo.proyecto1.modelo;

import java.util.ArrayList;

public class Pensum {
	int creditos;
	private String Nombre;
	private ArrayList<Requerimiento> Requerimientos;


	public ArrayList<Requerimiento> getRequerimientos() {
		return Requerimientos;
	}


	public String getNombre() {
		return Nombre;
	}

}
