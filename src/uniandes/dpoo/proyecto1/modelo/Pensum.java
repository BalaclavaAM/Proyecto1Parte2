package uniandes.dpoo.proyecto1.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Pensum implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5299770356142552116L;
	int creditos;
	private String Nombre;
	private ArrayList<Requerimiento> requerimientos;
	private Map<String, Requerimiento> requerimientosXtipo;

	public Pensum(int creditos, String nombre, ArrayList<Requerimiento> requerimientos) {
		this.creditos = creditos;
		Nombre = nombre;
		this.requerimientos = requerimientos;
	}

	public ArrayList<Requerimiento> getRequerimientos() {
		return requerimientos;
	}


	public String getNombre() {
		return Nombre;
	}

}
