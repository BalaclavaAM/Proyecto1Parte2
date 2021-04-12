 package uniandes.dpoo.proyecto1.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Pensum implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5299770356142552116L;
	int creditos;
	private String Nombre;
	private ArrayList<Requerimiento> Requerimientos;


	public Pensum(int creditos, String nombre, ArrayList<Requerimiento> requerimientos) {
		this.creditos = creditos;
		Nombre = nombre;
		Requerimientos = requerimientos;
	}

	public ArrayList<Requerimiento> getRequerimientos() {
		return Requerimientos;
	}


	public String getNombre() {
		return Nombre;
	}

}
