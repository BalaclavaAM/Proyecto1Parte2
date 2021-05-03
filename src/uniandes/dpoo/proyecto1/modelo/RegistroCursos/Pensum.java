 package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;

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

	private Map<String, String> cursosValidacionAuto;
	private Map<String, Requerimiento> requerimientos;
	private Map<Integer, ArrayList<Requerimiento>> requerimientosXsemestre;
	private Map<Nivel, Map<String,ArrayList<Requerimiento>>>  reqsXNivelTipo;




	public Pensum(String nombre) {
		this.creditos = 0;
		this.nombre = nombre;
		this.requerimientos = new Hashtable<>();
		this.requerimientosXsemestre = new Hashtable<>();
		this.cursosValidacionAuto = new Hashtable<>();
		this.reqsXNivelTipo = new Hashtable<>();
	}

	public void agregarRequerimiento(Requerimiento req) {
		String nombre = req.getNombre();
		if(requerimientos.containsKey(nombre)){
			String tipo = req.getTipo();
			Nivel nivel = req.getNivel();
			int semestre = req.getSemestresugerido();
			requerimientos.put(nombre, req);
			requerimientosXsemestre.putIfAbsent(semestre, new ArrayList<>());
			requerimientosXsemestre.get(semestre).add(req);
			reqsXNivelTipo.putIfAbsent(nivel, new Hashtable<>());
			Map<String, ArrayList<Requerimiento>> reqsxTipoN = reqsXNivelTipo.get(nivel);
			reqsxTipoN.putIfAbsent(tipo, new ArrayList<>());
			reqsxTipoN.get(tipo).add(req);
			this.creditos += req.getCreditos();
		}
	}

	public boolean agregarCursoValidacion(String curs, String reqN) {
		if(requerimientos.containsKey(reqN)) {
			cursosValidacionAuto.put(curs, reqN);
			return true;
		}
		return false;
	}

	public String getNombre() {
		return nombre;
	}

	public int getCreditos() {
		return creditos;
	}

	public Map<String,Requerimiento> getRequerimientos() {
		return requerimientos;
	}

	public Map<Integer, ArrayList<Requerimiento>> getRequerimientosXsemestre() {
		return requerimientosXsemestre;
	}

	public Map<Nivel, Map<String, ArrayList<Requerimiento>>> getReqsXNivelTipo() {
		return reqsXNivelTipo;
	}

	public Map<String, String> getCursosValidacionAuto() {
		return cursosValidacionAuto;
	}
}
