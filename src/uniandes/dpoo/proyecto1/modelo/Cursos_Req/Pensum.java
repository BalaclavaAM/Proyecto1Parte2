 package uniandes.dpoo.proyecto1.modelo.Cursos_Req;

import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Pensum implements Serializable {
	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = 5299770356142552116L;
	private int creditos;
	private final String nombre;

	private final Map<String, Requerimiento> cursosValidacionAuto;
	private final Map<String, Requerimiento> requerimientos;
	private final Map<Integer, ArrayList<Requerimiento>> requerimientosXsemestre;
	private final Map<Nivel, Map<ReqTipologia, ArrayList<Requerimiento>>> reqsXNivelXTipologia;


	public Pensum(String nombre) {
		this.creditos = 0;
		this.nombre = nombre;
		this.requerimientos = new Hashtable<>();
		this.requerimientosXsemestre = new Hashtable<>();
		this.cursosValidacionAuto = new Hashtable<>();
		this.reqsXNivelXTipologia = new Hashtable<>();
	}


	public void agregarRequerimiento(Requerimiento req) {
		String nombre = req.getNombre();
		if(!requerimientos.containsKey(nombre)){
			ReqTipologia tipologia = req.getTipologia();
			Nivel nivel = req.getNivel();
			int semestre = req.getSemestresugerido();
			requerimientos.put(nombre, req);
			requerimientosXsemestre.putIfAbsent(semestre, new ArrayList<>());
			requerimientosXsemestre.get(semestre).add(req);
			reqsXNivelXTipologia.putIfAbsent(nivel, new Hashtable<>());
			Map<ReqTipologia, ArrayList<Requerimiento>> reqsxTipoN = reqsXNivelXTipologia.get(nivel);
			reqsxTipoN.putIfAbsent(tipologia, new ArrayList<>());
			reqsxTipoN.get(tipologia).add(req);
			for(String codM: req.getMains()){
				if(!cursosValidacionAuto.containsKey(codM)){
					cursosValidacionAuto.put(codM, req);
				}
				else{
					System.out.println("dos requerimientos con el mismo main");
				}
			}
			this.creditos += req.getCreditos();
		}
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

	public Map<Nivel, Map<ReqTipologia, ArrayList<Requerimiento>>> getReqsXNivelTipo() {
		return reqsXNivelXTipologia;
	}

	public Map<String, Requerimiento> getCursosValidacionAuto() {
		return cursosValidacionAuto;
	}
}
