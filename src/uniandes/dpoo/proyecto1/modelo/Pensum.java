 package uniandes.dpoo.proyecto1.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Pensum implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5299770356142552116L;
	private int creditos;
	private final String nombre;
	private ArrayList<Requerimiento> requerimientos;
	private Map<String, ArrayList<Requerimiento>> requerimientosXtipo;
	private Map<Integer, ArrayList<Requerimiento>> requerimientosXsemestre;
	private Map<Nivel, ArrayList<Requerimiento>> requerimientosXnivel;


	public Pensum(String nombre) {
		this.creditos = 0;
		this.nombre = nombre;
		this.requerimientos = new ArrayList<>();
		this.requerimientosXtipo = new Hashtable<>();
		this.requerimientosXnivel = new Hashtable<>();
		this.requerimientosXsemestre = new Hashtable<>();
	}

	public void agregarRequerimiento(Requerimiento req) {
		requerimientos.add(req);
		requerimientosXtipo.putIfAbsent(req.getTipologia(), new ArrayList<>());
		requerimientosXtipo.get(req.getTipologia()).add(req);
		requerimientosXsemestre.putIfAbsent(req.getSemestreSugerido(), new ArrayList<>());
		requerimientosXsemestre.get(req.getSemestreSugerido()).add(req);
		requerimientosXnivel.putIfAbsent(req.getNivel(), new ArrayList<>());
		requerimientosXtipo.get(req.getTipologia()).add(req);
		this.creditos += req.getCreditos();
	}

	public String getNombre() {
		return nombre;
	}

	public int getCreditos() {
		return creditos;
	}

	public ArrayList<Requerimiento> getRequerimientos() {
		return requerimientos;
	}

	public Map<String, ArrayList<Requerimiento>> getRequerimientosXtipo() {
		return requerimientosXtipo;
	}

	public Map<Integer, ArrayList<Requerimiento>> getRequerimientosXsemestre() {
		return requerimientosXsemestre;
	}

	public Map<Nivel, ArrayList<Requerimiento>> getRequerimientosXnivel() {
		return requerimientosXnivel;
	}
}
